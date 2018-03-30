package server.plagiarism.util.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plagiarism.entity.File;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.util.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * DBUtil: providing database related utilities like saving, updating and deleting
 */
public class DBUtil {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(DBUtil.class);

    /**
     * Function (static class method)
     * saveOrUpdateEntity: used to save or update an entity to database
     * Input: an object, could be an instance of either Project or Report
     * Output: object written to the database
     * @param obj: Object {Project or Report}
     */
    public static void saveOrUpdateEntity(Object obj) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction to write to database");
        if(!transaction.isActive()) transaction.begin();
        if(obj instanceof Report) {
            Report report = (Report) obj;
            session.saveOrUpdate(report);

            log.info("Committing transaction to write report object to database");
            if(transaction.isActive()) transaction.commit();
        } else if(obj instanceof Project) {
            Project project = (Project) obj;
            try {
                saveOrUpdateProject(project, session);
            } catch (ConstraintViolationException e) {
                throw e;
            } finally {
                log.info("Committing transaction to write project object to database");
                if(transaction.isActive()) transaction.commit();
            }
        }
    }

    /**
     * Function (static class method)
     * deleteEntity: used to delete an entity from database
     * Input: an object, could be an instance of either Project or Report
     * Output: object deleted from the database
     * @param obj: Object {Project or Report}
     */
    public static void deleteEntity(Object obj) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction to delete object from database");
        if(!transaction.isActive()) transaction.begin();
        if(obj instanceof Report) {
            Report report = (Report) obj;
            session.delete(report);
        } else if(obj instanceof Project) {
            Project project = (Project) obj;
            session.delete(project);
        }

        log.info("Committing transaction to delete object from database");
        if(transaction.isActive()) transaction.commit();
    }

    /**
     * Function (static class method)
     * saveOrUpdateProject: helper function to write or update project object to database
     * Input: project object and session object
     * Output: project object written to database
     * @param project: Project object
     * @param session: Session object
     */
    private static void saveOrUpdateProject(Project project, Session session) {
        List<File> files = new ArrayList<>();
        for(File file : project.getFiles()) {
            file.setProject(project);
            files.add(file);
        }
        project.setFiles(files);
        try {
            session.saveOrUpdate(project);
        } catch (ConstraintViolationException e) {
            throw e;
        }
    }
}
