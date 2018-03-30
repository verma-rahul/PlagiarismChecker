package server.plagiarism.util.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plagiarism.entity.Project;

import server.plagiarism.entity.Project_;
import server.plagiarism.exceptions.ProjectNotFoundException;
import server.plagiarism.util.hibernate.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * ProjectQueryUtil: utility class to query project table
 */
public class ProjectQueryUtil {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(ProjectQueryUtil.class);

    /**
     * Function (static class method)
     * getProject: retrieve project object using project id
     * Input: project id
     * Output: Project object if found in database, else ProjectNotFoundException thrown
     * @param projectId
     * @return Project object
     * @throws ProjectNotFoundException
     */
    public static Project getProject(int projectId) throws ProjectNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction for getting project");
        if(!transaction.isActive()) transaction.begin();

        // Using criteria query to fetch results from database instead of using HQL
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaProjects = builder.createQuery(Project.class);
        Root<Project> root = criteriaProjects.from(Project.class);

        // Defining a Parameter to be used TypedQuery later to pass the integer id
        ParameterExpression<Integer> id = builder.parameter(Integer.class);
        criteriaProjects.select(root);
        criteriaProjects.where(
                builder.equal(root.get(Project_.projectId), id)
        );

        // Creating a TypedQuery with an integer parameter id
        TypedQuery<Project> query = session.createQuery(criteriaProjects) ;
        query.setParameter( id, projectId );
        Project project = null;
        try {
            project = query.getSingleResult();
        } catch(NoResultException e) {
            throw new ProjectNotFoundException("Project with id: " + projectId + " not found in database");
        } finally {
            log.info("Committing transaction for getting project");
            if(transaction.isActive()) transaction.commit();
        }

        log.info("returning project object");
        return project;
    }

    /**
     * Function (static class method)
     * getProject: retrieve project object using project name
     * Input: project name
     * Output: Project object if found in database, else ProjectNotFoundException thrown
     * @param projectName
     * @return Project object
     * @throws ProjectNotFoundException
     */
    public static Project getProject(String projectName) throws ProjectNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction for getting project");
        if(!transaction.isActive()) transaction.begin();

        // Using criteria query to fetch results from database instead of using HQL
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaProjects = builder.createQuery(Project.class);
        Root<Project> root = criteriaProjects.from(Project.class);

        // Defining a Parameter to be used TypedQuery later to pass the integer id
        ParameterExpression<String> name = builder.parameter(String.class);
        criteriaProjects.select(root);
        criteriaProjects.where(
                builder.equal(root.get(Project_.name), name)
        );

        // Creating a TypedQuery with an integer parameter id
        TypedQuery<Project> query = session.createQuery(criteriaProjects) ;
        query.setParameter(name, projectName);
        Project project = null;
        try {
            project = query.getSingleResult();
        } catch(NoResultException e) {
            throw new ProjectNotFoundException("Project with name: " + projectName + " not found in database");
        } finally {
            log.info("Committing transaction for getting project");
            if(transaction.isActive()) transaction.commit();
        }

        log.info("returning project object");
        return project;
    }

    /**
     * Function (static class method)
     * getProjectALL: retrieve all project objects from database
     * Output: Project object if found in database, else ProjectNotFoundException thrown
     * @return Project list
     */
    public static List<Project> getProjectAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction for getting project list");
        if(!transaction.isActive()) transaction.begin();

        // Creating criteria to fetch object from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaProjects = builder.createQuery(Project.class);
        Root<Project> root = criteriaProjects.from(Project.class);
        criteriaProjects.select(root);

        // Creating a TypedQuery using CriteriaQuery
        TypedQuery<Project> query = session.createQuery(criteriaProjects) ;
        List<Project> projects = query.getResultList();

        log.info("Committing transaction for getting project list");
        if(transaction.isActive()) transaction.commit();

        log.info("returning project object list");
        return projects;
    }
}
