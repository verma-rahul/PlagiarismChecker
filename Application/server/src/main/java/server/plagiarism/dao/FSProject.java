package server.plagiarism.dao;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * FSProject: file system interface with methods to be used for doing Project operations with AWS S3
 */
public interface FSProject extends FileSystem {

    /**
     * Method: readFS
     * Purpose: to read/download Project objects from AWS S3
     * @param projectName
     * @return Project
     */
    void readFS(String projectName);

    /**
     * Method: uploadFS
     * Purpose: to upload Project to AWS S3
     * @param dir
     * @return boolean
     */
    boolean uploadFS(String dir);
}
