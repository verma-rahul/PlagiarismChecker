package server.plagiarism.dao;

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

import java.io.IOException;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * TestReportDAO: to test ReportDAO class
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestReportDAO {

    // object to provide AWS S3 and RDS operations
    private static ReportDAO reportDAO;

    // before class method to run before any method in class runs
    @BeforeClass
    public static void init() {
        reportDAO = server.plagiarism.dao.DAOFactory.getInstance().makeReportDAO();
    }

    // test for creating report in db
    @Test
    public void test01CreateReportInDB() {
        boolean isWrite = false;
        try {
            isWrite = reportDAO.createDB(createReport(null,null));
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(isWrite, true);
    }

    // test for creating report in aws s3
    @Test
    public void test02CreateReportInFS() {
        boolean isWrite = false;
        try {
            isWrite = reportDAO.createFS(createReport(null,null));
        } catch (EntityParameterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(isWrite, true);
    }

    // test for reading report from db
    @Test
    public void test03ReadReportFromDB() {
        try {
            Report report = reportDAO.readDB("ReportCustom");
            Assert.assertEquals("ReportCustom", report.getName());

            Report report1 = reportDAO.readDB(report.getReportId());
            Assert.assertEquals("ReportCustom", report.getName());

        } catch (ReportNotFoundException e) {
            e.printStackTrace();
        }
    }

    // test for updating report from db
    @Test
    public void test04UpdateReportInDB() {
        try {
            Report report = reportDAO.readDB("ReportCustom");
            updateReport(report);
            reportDAO.updateDB(report);
            report = reportDAO.readDB("ReportCustom");
            Assert.assertEquals(0.8f, report.getSimilarityScore(), 0.1f);
        } catch (ReportNotFoundException e) {
            e.printStackTrace();
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
    }

    // test for reading report from aws s3
    @Test
    public void test05ReadReportFromFS() {
        Report report = reportDAO.readFS("ReportCustom");
        Assert.assertEquals("SummaryHere", report.getContent());
    }

    // test for deleting report from aws s3
    @Test
    public void test06DeleteReportFromFS() {
        boolean isDeleted = reportDAO.deleteFS("ReportCustom");
        Assert.assertEquals(true, isDeleted);
    }

    // test for deleting report from db
    @Test
    public void test07DeleteReportFromDB() {
        try {
            Report report = reportDAO.readDB("ReportCustom");
            boolean isDeleted = reportDAO.deleteDB(report);
            Assert.assertEquals(true, isDeleted);

            reportDAO.createDB(createReport(null, null));
            Report report1 = reportDAO.readDB("ReportCustom");

            boolean isDeleted1 = reportDAO.deleteDB(report1.getReportId());
            Assert.assertEquals(true, isDeleted1);

        } catch (EntityParameterException e) {
            e.printStackTrace();
        } catch (ReportNotFoundException e) {
            e.printStackTrace();
        }
    }

    // test for checking exception if incorrect parameter is passed
    @Test
    public void test08CreateReportInDBException() {
        String str = "random text";
        try {
            reportDAO.createDB(str);
        } catch (EntityParameterException e) {
            Assert.assertEquals("Required object of class Report, found:" + str.getClass(), e.getMessage());
        }
    }

    // test for checking exception if incorrect parameter is passed
    @Test
    public void test09DeleteReportInDBException() {
        String str = "random text";
        try {
            reportDAO.deleteDB(str);
        } catch (EntityParameterException e) {
            Assert.assertEquals("Required object of class Report, found:" + str.getClass(), e.getMessage());
        }
    }

    // test for deleting project if project id is invalid
    @Test
    public void test10DeleteReportInDBException() {
        int id = Integer.MAX_VALUE;
        try {
            reportDAO.deleteDB(id);
        } catch (ReportNotFoundException e) {
            Assert.assertEquals("Report with id: " + id + " not found in database", e.getMessage());
        }
    }

    // test for checking exception when incorrect type is passed as parameter
    @Test
    public void test11UpdateReportInDBException() {
        String str = "random text";
        try {
            reportDAO.updateDB(str);
        } catch (EntityParameterException e) {
            Assert.assertEquals("Required object of class Report, found:" + str.getClass(), e.getMessage());
        }
    }

    // method for creating a project for testing purposes
    static Report createReport(Project project1, Project project2) {
        Report report = EntityFactory.getInstance().makeReport();
        report.setContent("SummaryHere");
        report.setDirectory("Reports/");
        report.setSimilarityScore(0.1f);
        report.setResultSummary("SummaryHere");
        report.setProject1(project1);
        report.setProject2(project2);
        report.setName("ReportCustom");
        return report;
    }

    // method for updating report for testing purposes
    private static void updateReport(Report report) {
        report.setSimilarityScore(0.8f);
    }
}
