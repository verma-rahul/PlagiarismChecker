package server.plagiarism.dao;

import server.plagiarism.exceptions.EntityParameterException;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * Database: database interface with methods to be used for interaction with MySQL
 */
public interface Database extends DAO {

    /**
     * Method: createDB
     * Purpose: to write Project and Report objects to database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    boolean createDB(Object obj) throws EntityParameterException;

    /**
     * Method: updateDB
     * Purpose: to update Project and Report objects to database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    boolean updateDB(Object obj) throws EntityParameterException;

    /**
     * Method: deleteDB
     * Purpose: to delete Project and Report objects from database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    boolean deleteDB(Object obj) throws EntityParameterException;
}
