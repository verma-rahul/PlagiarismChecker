package server.plagiarism.dao;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * FileSystem: filesystem interface with methods to be used for interaction with AWS S3
 */
public interface FileSystem extends DAO {

    /**
     * Method: deleteFS
     * Purpose: to delete Project and Report objects from AWS S3
     * @param dir
     * @return boolean
     */
    boolean deleteFS(String dir);
}
