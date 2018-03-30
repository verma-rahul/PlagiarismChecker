package server.plagiarism.dao;

import org.junit.*;
import org.junit.runners.MethodSorters;
import server.plagiarism.entity.Project;
import server.plagiarism.util.fs.FSUtil;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * TestFileDAO: to test FileDAO class
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFileDAO {

    // object to provide AWS S3 operations
    private static FileDAO fileDAO;

    // before class method to run before any test case runs
    @BeforeClass
    public static void init() {
        fileDAO = server.plagiarism.dao.DAOFactory.getInstance().makeFileDAO();
    }

    // test for uploading project to S3
    @Test
    public void test1uploadFS() {
        Assert.assertEquals(true, fileDAO.uploadFS("src\\test\\resources\\TestProjectsRoot\\TestDir"));
    }

    // test for downloading project from S3
    @Test
    public void test2readFS() {
        fileDAO.readFS("TestDir");
        Project project = FSUtil.initProjectFromLocalUsingName("TestDir");
        Assert.assertEquals("TestDir", project.getName());
    }

    // test for deleting project from S3
    @Test
    public void test3deleteFS() {
        Assert.assertEquals(true, fileDAO.deleteFS("TestDir"));
    }

    // after class method to clean local temp folder
    @AfterClass
    public static void cleanUp() {
        FSUtil.deleteFolderFromLocalTemp("TestDir");
    }

}
