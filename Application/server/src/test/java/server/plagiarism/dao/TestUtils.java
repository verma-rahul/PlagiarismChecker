package server.plagiarism.dao;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.exceptions.ReportNotFoundException;
import server.plagiarism.util.common.CommonUtil;
import server.plagiarism.util.db.ReportQueryUtil;
import server.plagiarism.util.fs.FSUtil;
import server.plagiarism.util.hibernate.HibernateUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
  * @author Rahul Verma [verma.rah@husky.neu.edu]
  *
**/

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUtils {

    // object to provide AWS S3 and RDS operations
    private static ReportDAO reportDAO;

    // object to provide AWS RDS MySQL operations
    private static ProjectDAO projectDAO;

    // before class method to run before any tests are executed
    @BeforeClass
    public static void init() {
        createFolder();
        reportDAO = DAOFactory.getInstance().makeReportDAO();
        projectDAO = DAOFactory.getInstance().makeProjectDAO();
    }

    // test to try to upload null report
    @Test
    public void test01NullReport() {
        try {
            FSUtil.uploadReportToS3(null);
        } catch (IOException e) {
        } catch (EntityParameterException e) {
            Assert.assertEquals("Expected parameter Report, found: null", e.getMessage());
        }
    }

    // test to clean a local directory
    @Test
    public void test02CleanDirectory() {
        FSUtil.cleanReportFolderInLocalTemp();
        Assert.assertTrue(true);
    }

    // test to delete folder from local filesystem
    @Test
    public void test03DeleteDirectory() {
        FSUtil.deleteFolderFromLocalTemp("test");
        Assert.assertTrue(true);
    }

    // test to check the local reports directory
    @Test
    public void test04TempReportsDirectory() {
        Assert.assertEquals("tempAWSDownload/Reports", FSUtil.getTempRelativeLocationLocalForReports());
    }

    // test to check closing of hibernate session
    @Test
    public void test99HibernateShutdown() {
        HibernateUtil.getSession();
        HibernateUtil.shutdown();
        Assert.assertTrue(true);
    }

    // test for updating ids from source to target project
    @Test
    public void test06UpdateIds() {
        Project source = createProjectWithIds();
        Project target = createProjectNoIds();
        CommonUtil.updateProjectAndFileId(source, target);
        Assert.assertEquals(source.getProjectId(), target.getProjectId());
    }

    // test for reading all reports from db
    @Test
    public void test07GetAllReports() {
        Report report = TestReportDAO.createReport(null, null);
        report.setName("report_test");
        try {
            reportDAO.createDB(report);
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
        List<Report> reports = ReportQueryUtil.getReportAll();
        List<Report> reports1 = ReportQueryUtil.getReportAll();
        Assert.assertEquals(reports.size(), reports1.size());
        try {
            reportDAO.deleteDB(report);
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
    }

    // test for checking hibernate interceptor
    @Test
    public void test08TestInterceptor() {
        Project project = createProjectNoIds();
        project.setName("Test1Project");
        Report report1 = TestReportDAO.createReport(project, null);
        report1.setName("Test1Report");
        Report report2 = TestReportDAO.createReport(null, project);
        report2.setName("Test2Report");
        try {
            projectDAO.createDB(project);
            reportDAO.createDB(report1);
            reportDAO.createDB(report2);

            projectDAO.deleteDB(project);
            Report report3 = reportDAO.readDB("Test1Report");
            Report report4 = reportDAO.readDB("Test2Report");
            Assert.assertEquals(null, report3.getProject1());
            Assert.assertEquals(null, report4.getProject2());

            reportDAO.deleteDB(reportDAO.readDB("Test1Report").getReportId());
            reportDAO.deleteDB(reportDAO.readDB("Test2Report").getReportId());

        } catch (EntityParameterException e) {
            e.printStackTrace();
        } catch (ReportNotFoundException e) {
            e.printStackTrace();
        }
    }

    // test for checking parsing of directory
    @Test
    public void test09ParseAndCheck() {
        Assert.assertEquals(false, CommonUtil.parseAndCheckProjectName("test", "test2"));
    }

    // method for creating folder in local filesystem
    private static void createFolder() {
        try {
            FileUtils.forceMkdir(new File(System.getenv("HOMEDRIVE")
                    + "/"
                    + System.getenv("HOMEPATH")
                    + "/"
                    + "tempAWSDownload"
                    + "/"
                    + "test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method for creating project with no id or file ids
    private Project createProjectNoIds() {
        Project project = EntityFactory.getInstance().makeProject();
        project.setName("project 3");
        List<server.plagiarism.entity.File> files = new ArrayList<>();
        for(int i=1; i<=10 ; i++) {
            server.plagiarism.entity.File file = EntityFactory.getInstance().makeFile();
            file.setDirectory("dir3" + i);
            file.setName("file3" + i);
            file.setProject(project);
            files.add(file);
        }
        project.setFiles(files);
        return project;
    }

    // method for creating project with ids
    private Project createProjectWithIds() {
        Project project = EntityFactory.getInstance().makeProject();
        project.setName("project 3");
        project.setProjectId(1);
        List<server.plagiarism.entity.File> files = new ArrayList<>();
        for(int i=1; i<=10 ; i++) {
            server.plagiarism.entity.File file = EntityFactory.getInstance().makeFile();
            file.setDirectory("dir3" + i);
            file.setName("file3" + i);
            file.setProject(project);
            file.setFileId(i);
            files.add(file);
        }
        project.setFiles(files);
        return project;
    }


}
