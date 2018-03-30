package server.plagiarism.dao;

import org.hibernate.AssertionFailure;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.File;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.exceptions.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * TestProjectDAO: to test ProjectDAO class
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProjectDAO {

    // object to provide AWS RDS MySQL operations
    private static ProjectDAO projectDAO;

    // before class method to run before any test case runs
    @BeforeClass
    public static void init() {
        projectDAO = server.plagiarism.dao.DAOFactory.getInstance().makeProjectDAO();
    }

    // test for creating project in db
    @Test
    public void test1Create() {
        Project project = createProject();
        boolean isWrite = false;
        try {
            isWrite = projectDAO.createDB(project);
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, isWrite);
    }

    // test for creating project with same name
    @Test(expected = AssertionFailure.class)
    public void test1_1Create() {
        Project project = createProject();
        boolean isWrite = false;
        try {
            isWrite = projectDAO.createDB(project);
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
    }

    // test for reading project from db
    @Test
    public void test2Read() {
        Project project = null;
        Project project2 = null;
        try {
            project = projectDAO.readDB("project 3");
            project2 = projectDAO.readDB(project.getProjectId());
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(project2.getName(), project.getName());
    }

    // test for reading list of projects from db
    @Test
    public void test2_1Read() {
        List<Project> projects = null;
        List<Project> projects1 = null;
        try {
            projects = projectDAO.readDB();
            projects1 = projectDAO.readDB();
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(projects1.size(), projects.size());
    }

    // test for updating project in db
    @Test
    public void test2_2Update() {
        Project project = null;
        Project project2 = null;
        try {
            project = projectDAO.readDB("project 3");
            project.setName("Project 3 New");
            projectDAO.updateDB(project);
            project2 = projectDAO.readDB("Project 3 New");
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("Project 3 New", project2.getName());
    }

    // test for deleting project from db
    @Test
    public void test3Delete() {
        Project project = null;
        boolean isDelete = false;
        try {
            project = projectDAO.readDB("Project 3 New");
            isDelete = projectDAO.deleteDB(project);
        } catch (EntityParameterException e) {
            e.printStackTrace();
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, isDelete);
    }

    // test for deleting project from db using id
    @Test
    public void test3_1DeleteWithId() {
        Project project = createProject();
        boolean isDelete = false;
        try {
            projectDAO.createDB(project);
            project = projectDAO.readDB("project 3");
            isDelete = projectDAO.deleteDB(project.getProjectId());
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        } catch (EntityParameterException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, isDelete);
    }

    // test for reading 1 project that does not exist
    @Test
    public void test3_2DeletedRead() {
        Project project = null;
        boolean isDelete = false;
        try {
            project = projectDAO.readDB("project 3");
        } catch (ProjectNotFoundException e) {
            Assert.assertEquals("Project with name: project 3 not found in database", e.getMessage());
        }
    }

    // test for checking if all projects are retrieved
    @Test
    public void test3_2DeletedReadAll() {
        List<Project> projects = null;
        try {
            projects = projectDAO.readDB();
        } catch (ProjectNotFoundException e) {
            Assert.assertEquals("No Projects not found in database", e.getMessage());
        }
    }

    // test for creating report in db
    @Test
    public void test4CreateReport() {
        Report report = EntityFactory.getInstance().makeReport();
        try {
            projectDAO.createDB(report);
        } catch (EntityParameterException e) {
            Assert.assertEquals("Required object of class Project, found:" + report.getClass(), e.getMessage());
        }
    }

    // test for reading a project with invalid id
    @Test
    public void test5InvalidId() {
        try {
            projectDAO.readDB(-1);
        } catch (ProjectNotFoundException e) {
            Assert.assertEquals("Project with id: " + -1 + " not found in database", e.getMessage());
        }
    }

    // test for reading project from db with invalid name
    @Test
    public void test6InvalidName() {
        long time = System.currentTimeMillis();
        try {
            projectDAO.readDB(time + "");
        } catch (ProjectNotFoundException e) {
            Assert.assertEquals("Project with name: " + time + " not found in database", e.getMessage());
        }
    }

    // test for checking entity parameter exception by passing report to project dao object to save to db
    @Test
    public void test7DeleteReport() {
        Report report = EntityFactory.getInstance().makeReport();
        try {
            projectDAO.deleteDB(report);
        } catch (EntityParameterException e) {
            Assert.assertEquals("Required object of class Project, found:" + report.getClass(), e.getMessage());
        }
    }

    // test for checking entity parameter exception by passing report to project dao object to save to db
    @Test
    public void test8UpdateReport() {
        Report report = EntityFactory.getInstance().makeReport();
        try {
            projectDAO.updateDB(report);
        } catch (EntityParameterException e) {
            Assert.assertEquals("Required object of class Project, found:" + report.getClass(), e.getMessage());
        }
    }

    // local method to create project for testing purposes
    private Project createProject() {
        Project project = EntityFactory.getInstance().makeProject();
        project.setName("project 3");
        List<File> files = new ArrayList<>();
        for(int i=1; i<=10 ; i++) {
            File file = EntityFactory.getInstance().makeFile();
            file.setDirectory("dir3" + i);
            file.setName("file3" + i);
            file.setProject(project);
            files.add(file);
        }
        project.setFiles(files);
        return project;
    }
}
