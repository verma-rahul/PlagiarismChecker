package server.plagiarism.dao;

import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.ReportNotFoundException;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * DBReport: database interface with methods to be used for doing Report operations with MySQL
 */
public interface DBReport extends Database {

    /**
     * Method: readDB
     * Purpose: to read Report objects from database based on id
     * @param id
     * @return Report
     * @throws ReportNotFoundException
     */
    Report readDB(int id) throws ReportNotFoundException;

    /**
     * Method: readDB
     * Purpose: to read Report objects from database based on name
     * @param name
     * @return Report
     * @throws ReportNotFoundException
     */
    Report readDB(String name) throws ReportNotFoundException;

    /**
     * Method: deleteDB
     * Purpose: to delete Report objects from database based on id
     * @param id
     * @return boolean
     * @throws ReportNotFoundException
     */
    boolean deleteDB(int id) throws ReportNotFoundException;
}
