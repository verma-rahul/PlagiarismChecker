package server.plagiarism.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.plagiarism.dao.FileDAO;
import server.plagiarism.dao.ProjectDAO;
import server.plagiarism.dao.ReportDAO;
import server.plagiarism.engine.ConcretePlagiarismSummary;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.exceptions.ProjectNotFoundException;
import server.plagiarism.exceptions.ReportNotFoundException;
import server.plagiarism.requestbody.ReportRequestBody;
import server.plagiarism.util.common.CommonUtil;
import server.plagiarism.util.db.ReportQueryUtil;
import server.plagiarism.util.fs.FSUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *  @version 1.0
 *
 *  Class
 *  ReportRequestController: class which defines all the Report related RESTful web services
 */
@RestController
@RequestMapping("/report")
public class ReportRequestController implements IReportRequestController {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(ReportRequestController.class);

    // Variable to store file dao object
    @Autowired
    private FileDAO fileDAO;

    // Variable to store project dao object
    @Autowired
    private ProjectDAO projectDAO;

    // Variable to store report dao object
    @Autowired
    private ReportDAO reportDAO;

    /**
     *  Method
     *  saveReport: to save a report
     *  @param reportRequest: ReportRequestBody object to be saved
     *  @return HttpStatus indicating success or failure of operation
     */
    @Override
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public HttpStatus saveReport(@RequestBody ReportRequestBody reportRequest) {
        log.info("saving report");
        Report report = null;
        try {
            report = createReportFromRequest(reportRequest);
            reportDAO.createFS(report);
            reportDAO.createDB(report);
            log.info("report saved successfully");
            FSUtil.cleanReportFolderInLocalTemp();
            return HttpStatus.OK;
        } catch (EntityParameterException e) {
            log.error("EntityParameterException thrown, parameter passed is not report");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IOException thrown");
            e.printStackTrace();
        } catch (ProjectNotFoundException e) {
            log.error("ProjectNotFoundException thrown, project not found in database");
            e.printStackTrace();
        }
        log.error("saving report failed");
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Method
     * createReportFromRequest: takes a ReportRequestBody and creates a Report out of it
     * @param reportRequestBody ReportRequestBody object
     * @return Report object
     * @throws ProjectNotFoundException if project is missing from database
     */
    private Report createReportFromRequest(ReportRequestBody reportRequestBody) throws ProjectNotFoundException {
        Report report = EntityFactory.getInstance().makeReport();
        report.setName(reportRequestBody.getName());
        report.setContent(reportRequestBody.getResultsummary());
        report.setProject1(projectDAO.readDB(reportRequestBody.getProject1id()));
        report.setProject2(projectDAO.readDB(reportRequestBody.getProject2id()));
        report.setSimilarityScore(reportRequestBody.getSimilarityscore());
        report.setDirectory(FSUtil.getTempRelativeLocationLocalForReports());
        return report;
    }

    /**
     *  Method
     *  getReportList: to get a list of reports
     *  @return List of reports
     */
    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Report> getReportList() {
        log.info("fetching list of reports");
        List<Report> reports = ReportQueryUtil.getReportAll();
        for(Report report : reports) {
            report.setResultSummary(reportDAO.readFS(report.getName()).getContent());
        }
        FSUtil.cleanReportFolderInLocalTemp();
        return reports;
    }

    /**
     *  Method
     *  deleteReport: to delete a single report
     *  @param reportRequestBody: ReportRequestBody object
     *  @param id: integer report id
     *  @return List of remaining reports
     */
    @Override
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public List<Report> deleteReport(@RequestBody ReportRequestBody reportRequestBody, @PathVariable("id") int id) {
        log.info("deleting project");
        try {
            reportDAO.deleteDB(id);
            reportDAO.deleteFS(reportRequestBody.getName());
        } catch (ReportNotFoundException e) {
            log.error("delete failed, report not found");
            e.printStackTrace();
        }
        return ReportQueryUtil.getReportAll();
    }

    /**
     * Method
     * getReport: to fetch a report
     * @param id: integer report id
     * @return Report object
     */
    @Override
    public Report getReport(Integer id) {
        log.info("fetching report");
        Report report = null;
        try {
            report = reportDAO.readDB(id);
            report.setResultSummary(reportDAO.readFS(report.getName()).getContent());
        } catch (ReportNotFoundException e) {
            log.error("fetch report failed, report not found, will return null to caller");
            e.printStackTrace();
            return null;
        }
        FSUtil.cleanReportFolderInLocalTemp();
        log.info("report fetch successful");
        return report;
    }

    /**
     * Method
     * generateReport: to generate a plagiarism report between 2 projects
     * @param projects: Array of 2 projects
     * @return Report object
     */
    @Override
    @RequestMapping(value = "/plagiarism", method = RequestMethod.POST)
    public Report generateReport(@RequestBody Project[] projects) {
        log.info("starting plagiarism check");
        if(projects.length != 2) {
            log.info("incorrect number of project passed as parameter, returning null to caller");
            return null;
        }
        Project[] prjLocal = null;
        try {
            prjLocal = readProjectsProjectDAO(projects[0].getProjectId(), projects[1].getProjectId());
        } catch (ProjectNotFoundException e) {
            log.info("downloading projects from database failed, project not found");
            e.printStackTrace();
        }
        readProjectsFileDAO(prjLocal[0].getName(), prjLocal[1].getName());
        Project projectLocalFS[] = initProjectsFromLocalFilesystem(prjLocal[0].getName(), prjLocal[1].getName());
        updateProjectAndFileIds(prjLocal, projectLocalFS);
        cleanupLocalTempProjects(prjLocal[0].getName(), prjLocal[1].getName());
        log.info("starting plagiarism engine");
        ConcretePlagiarismSummary.instance().generateReport(projectLocalFS[0], projectLocalFS[1]);
        log.info("plagiarism check done");
        return ConcretePlagiarismSummary.instance().getReport();
    }

    /**
     * Method
     * readProjectsProjectDAO: read projects from database using project ids
     * @param project1Id: integer project id
     * @param project2Id: integer project id
     * @return Array of 2 projects
     * @throws ProjectNotFoundException
     */
    private Project[] readProjectsProjectDAO(int project1Id, int project2Id) throws ProjectNotFoundException {
        log.info("reading projects from database");
        Project[] projects = new Project[2];
        projects[0] = projectDAO.readDB(project1Id);
        projects[1] = projectDAO.readDB(project2Id);
        log.info("downloading projects from database successful");
        return projects;
    }

    /**
     * Method
     * readProjectsFileDAO: read projects from AWS S3 using project names
     * @param project1Name: String project name
     * @param project2Name: String project name
     */
    private void readProjectsFileDAO(String project1Name, String project2Name) {
        log.info("downloading projects to local filesystem");
        fileDAO.readFS(project1Name);
        fileDAO.readFS(project2Name);
        log.info("downloading projects to local filesystem successful");
    }

    /**
     * Method
     * initProjectsFromLocalFilesystem: read projects from local filesystem and return 2 project objects
     * @param project1Name: String project name
     * @param project2Name: String project name
     * @return Array of 2 projects
     */
    private Project[] initProjectsFromLocalFilesystem(String project1Name, String project2Name) {
        log.info("initializing project objects from projects in local filesystem");
        Project[] projects = new Project[2];
        projects[0] = FSUtil.initProjectFromLocalUsingName(project1Name);
        projects[1] = FSUtil.initProjectFromLocalUsingName(project2Name);
        log.info("initializing project objects from projects in local filesystem successful");
        return projects;
    }

    /**
     * Method
     * updateProjectAndFileIds: update target project id and file ids based on source project
     * @param sourceProjects: Array of 2 source projects
     * @param targetProjects: Array of 2 target projects
     */
    private void updateProjectAndFileIds(Project[] sourceProjects, Project[] targetProjects) {
        log.info("updating project id and file ids of project objects from local filesystem using database objects");
        CommonUtil.updateProjectAndFileId(sourceProjects[0], targetProjects[0]);
        CommonUtil.updateProjectAndFileId(sourceProjects[1], targetProjects[1]);
        log.info("updating project id and file ids of project objects from local filesystem using " +
                "database objects successful");
    }

    /**
     * Method
     * cleanupLocalTempProjects: clean up the temp local directory of projects
     * @param project1Name: String project 1 name
     * @param project2Name: String project 2 name
     */
    private void cleanupLocalTempProjects(String project1Name, String project2Name) {
        log.info("cleaning up local temp projects");
        FSUtil.deleteFolderFromLocalTemp(project1Name);
        FSUtil.deleteFolderFromLocalTemp(project2Name);
        log.info("cleaning up local temp projects successful");
    }

}
