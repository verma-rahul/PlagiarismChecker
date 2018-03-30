package server.plagiarism.dao;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.plagiarism.util.common.CommonUtil;
import server.plagiarism.util.fs.FSUtil;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 @version 1.0
 Class
 FileDAO: data access object class to be used to interact with AWS S3 for Projects
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileDAO implements FSProject {

    // singleton instance for the class
    private static FileDAO instanceFileDAO;

    // private constructor to force use of getInstance
    private FileDAO() {
    }

    /**
     * Method
     * getInstanceFileDAO: used to get singleton instance of the class
     * @return FileDAO singleton object
     */
    static FileDAO getInstanceFileDAO() {
        if(instanceFileDAO == null)
            instanceFileDAO = new FileDAO();
        return instanceFileDAO;
    }

   /**
     * Method: uploadFS
     * Purpose: to upload Project from local file system to AWS S3 and correspondingly update MySQL
     * @param rootDir
     * @return boolean
     */
    @Override
    public boolean uploadFS(String rootDir) {
        String rootKey = rootDir.substring(CommonUtil.getProjectNameIndexFromPath(rootDir));
        FSUtil.uploadFolderToS3(rootKey, rootDir);
        return true;
    }

    /**
     * Method: deleteFS
     * Purpose: to delete Project from AWS S3 and correspondingly update MySQL
     * @param projectName
     * @return boolean
     */
    @Override
    public boolean deleteFS(String projectName) {
        FSUtil.deleteFolderFromS3(projectName);
        return true;
    }

    /**
     * Method: readFS
     * Purpose: to read/download Project from AWS S3 and correspondingly use MySQL to update necessary fields
     * @param projectName
     * @return Project
     */
    @Override
    public void readFS(String projectName) {
        FSUtil.downloadFolderFromS3(projectName);
    }

}
