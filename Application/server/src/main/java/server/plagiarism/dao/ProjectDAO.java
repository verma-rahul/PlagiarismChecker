package server.plagiarism.dao;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.plagiarism.entity.Project;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.exceptions.ProjectNotFoundException;
import server.plagiarism.util.db.DBUtil;
import server.plagiarism.util.db.ProjectQueryUtil;

import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 @version 1.0
 Class
 ProjectDAO: data access object class to be used to interact with MySQL for Projects
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) // works when autowired (spring bean container manages that)
public class ProjectDAO implements DBProject {

    // singleton instance for the class
    private static ProjectDAO instanceProjectDAO;

    // private constructor to force use of getInstance
    private ProjectDAO() {
    }

    /**
     * Method
     * getInstanceProjectDAO: used to get singleton instance of the class
     * @return ProjectDAO singleton object
     */
    static ProjectDAO getInstanceProjectDAO() {
        if(instanceProjectDAO == null)
            instanceProjectDAO = new ProjectDAO();
        return instanceProjectDAO;
    }

    /**
     * Method: createDB
     * Purpose: to write Project objects to database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    @Override
    public boolean createDB(Object obj) throws EntityParameterException {
        if(obj instanceof Project) {
            DBUtil.saveOrUpdateEntity(obj);
            return true;
        } else {
            throw new EntityParameterException("Required object of class Project, found:" + obj.getClass());
        }

    }

    /**
     * Method: readDB
     * Purpose: to read Project objects from database
     * @param id
     * @return boolean
     * @throws ProjectNotFoundException
     */
    @Override
    public Project readDB(int id) throws ProjectNotFoundException {
        Project project = ProjectQueryUtil.getProject(id);
        return project;
    }

    /**
     * Method: updateDB
     * Purpose: to update Project objects to database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    @Override
    public boolean updateDB(Object obj) throws EntityParameterException {
        if(obj instanceof Project) {
            DBUtil.saveOrUpdateEntity(obj);
        } else {
            throw new EntityParameterException("Required object of class Project, found:" + obj.getClass());
        }
        return true;
    }

    /**
     * Method: deleteDB
     * Purpose: to delete Project objects from database
     * @param id
     * @return boolean
     * @throws ProjectNotFoundException
     */
    @Override
    public boolean deleteDB(int id) throws ProjectNotFoundException {
        Project project = readDB(id);
        DBUtil.deleteEntity(project);
        return true;
    }

    /**
     * Method: deleteDB
     * Purpose: to delete Project objects from database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    @Override
    public boolean deleteDB(Object obj) throws EntityParameterException {
        if(obj instanceof Project) {
            DBUtil.deleteEntity(obj);
        } else {
            throw new EntityParameterException("Required object of class Project, found:" + obj.getClass());
        }
        return true;
    }

    /**
     * Method: readDB
     * Purpose: to read Project objects from database
     * @param name
     * @return Project
     * @throws ProjectNotFoundException
     */
    @Override
    public Project readDB(String name) throws ProjectNotFoundException {
        Project project = ProjectQueryUtil.getProject(name);
        return project;
    }

    /**
     * Method: readDB
     * Purpose: to read all Project objects from database
     * @return Project List
     * @throws ProjectNotFoundException
     */
    @Override
    public List<Project> readDB() throws ProjectNotFoundException{
        List<Project> projects = ProjectQueryUtil.getProjectAll();
        if(projects.size() == 0) {
            throw new ProjectNotFoundException("No Projects not found in database");
        }
        return projects;
    }
}
