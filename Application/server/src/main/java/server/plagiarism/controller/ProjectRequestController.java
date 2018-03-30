package server.plagiarism.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.plagiarism.dao.FileDAO;
import server.plagiarism.dao.ProjectDAO;
import server.plagiarism.entity.Project;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.exceptions.ProjectNotFoundException;
import server.plagiarism.requestbody.ProjectRequestBody;
import server.plagiarism.service.UploadRequestService;
import server.plagiarism.util.fs.FSUtil;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 *  @version 1.0
 *
 *  Class
 *  ProjectRequestController: class which defines all the Project related RESTful web services
 */
@RestController
@RequestMapping("/project")
public class ProjectRequestController implements IProjectRequestController {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(ProjectRequestController.class);

    // Variable to store project dao object
    @Autowired
    private ProjectDAO projectDAO;

    // Variable to store file dao object
    @Autowired
    private FileDAO fileDAO;

    // Variable to store upload request service object
    @Autowired
    private UploadRequestService uploadRequestService;

    /**
     * Method
     * getProjectList: to get a list of all projects
     * @return List of projects
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Project> getProjectList() {
        log.info("Retrieving list of all projects");
        List<Project> projects = null;
        try {
            projects = projectDAO.readDB();
        } catch (ProjectNotFoundException e) {
            log.error("Project not found exception thrown from getProjectList, will return empty array list to caller");
            e.printStackTrace();
            return new ArrayList<>();
        }
        log.info("projects retrieved, returning a list to caller");
        return projects;
    }

    /**
     * Method
     * deleteProject: to delete a project
     * @param project: Project Object to be deleted
     * @return List of all Project Entity Objects in the system
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public List<Project> deleteProject(@RequestBody Project project) {
        log.info("Deleting project");
        try {
            projectDAO.deleteDB(project);
        } catch (EntityParameterException e) {
            log.error("Entity parameter exception thrown, passed object is not a Project");
            e.printStackTrace();
        }
        fileDAO.deleteFS(project.getName());
        log.info("delete finished");
        return getProjectList();
    }

    /**
     * Method
     * uploadProject: uploads a project
     * @param request: UploadProjectRequest object
     * @return HttpStatus indicating success or failure of the operation
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public HttpStatus uploadProject(@RequestBody ProjectRequestBody request) {
        log.info("uploading project");
        if (uploadRequestService.isValid(request)) {
            fileDAO.uploadFS(request.getDirectory());
            Project project = FSUtil.initProjectFromLocal(request.getDirectory());
            try {
                projectDAO.createDB(project);
            } catch (EntityParameterException e) {
                log.error("EntityParameterException thrown by createDB in uploadProject, " +
                        "parameter passed in not project");
                e.printStackTrace();
                return HttpStatus.FORBIDDEN;
            }
            log.info("upload finished");
            return HttpStatus.OK;
        }
        //  returns FORBIDDEN if the request is not valid
        return HttpStatus.FORBIDDEN;
    }
}
