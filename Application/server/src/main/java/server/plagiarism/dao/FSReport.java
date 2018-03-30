package server.plagiarism.dao;

import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.EntityParameterException;

import java.io.IOException;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * FSReport: file system interface with methods to be used for doing Report operations with AWS S3
 */
public interface FSReport extends FileSystem {

    /**
     * Method: readFS
     * Purpose: to read/download Report objects from AWS S3
     * @param reportName
     * @return Report
     */
    Report readFS(String reportName);

    /**
     * Method: createFS
     * Purpose: to create Report objects in AWS S3
     * @param doc
     * @return boolean
     * @throws IOException
     * @throws EntityParameterException
     */
    boolean createFS(Report doc) throws IOException, EntityParameterException;
}
