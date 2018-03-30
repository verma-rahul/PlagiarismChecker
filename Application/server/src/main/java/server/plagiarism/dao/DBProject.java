package server.plagiarism.dao;

import server.plagiarism.entity.Project;
import server.plagiarism.exceptions.ProjectNotFoundException;

import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * DBProject: database interface with methods to be used for doing Project operations with MySQL
 */
public interface DBProject extends Database {

    /**
     * Method: readDB
     * Purpose: to read all Project objects from database
     * @return boolean
     * @throws ProjectNotFoundException
     */
    List<Project> readDB() throws ProjectNotFoundException;

    /**
     * Method: readDB
     * Purpose: to read Project objects from database
     * @param id
     * @return boolean
     * @throws ProjectNotFoundException
     */
    Project readDB(int id) throws ProjectNotFoundException;

    /**
     * Method: readDB
     * Purpose: to read Project objects from database
     * @param name
     * @return Project
     * @throws ProjectNotFoundException
     */
    Project readDB(String name) throws ProjectNotFoundException;

    /**
     * Method: deleteDB
     * Purpose: to delete Project objects from database
     * @param id
     * @return boolean
     * @throws ProjectNotFoundException
     */
    boolean deleteDB(int id) throws ProjectNotFoundException;
}
