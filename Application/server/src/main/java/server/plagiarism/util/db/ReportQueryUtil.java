package server.plagiarism.util.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plagiarism.entity.Report;
import server.plagiarism.util.hibernate.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * ReportQueryUtil: used to query database to retrieve report objects
 */
public class ReportQueryUtil {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(ReportQueryUtil.class);

    /**
     * Method (static class method)
     * getReport: retrieves a single report object using report id
     * @param reportId: integer report id
     * @return Report object
     */
    public static Report getReport(int reportId) throws NoResultException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction for getting report");
        if(!transaction.isActive()) transaction.begin();

        // Creating criteria to fetch object from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaReports = builder.createQuery(Report.class);
        Root<Report> reportRoot = criteriaReports.from(Report.class);
        criteriaReports.select(reportRoot);

        // Creating a parameter for the criteria
        ParameterExpression<Integer> rId = builder.parameter(Integer.class);

        // Creating a predicate for the criteria
        Predicate projectRestriction = builder.equal(reportRoot.get(Report_.reportId), rId);
        criteriaReports.where(projectRestriction);

        // Creating a TypedQuery using CriteriaQuery
        TypedQuery<Report> query = session.createQuery(criteriaReports);
        query.setParameter(rId, reportId);
        Report report = query.getSingleResult();

        log.info("Committing transaction for getting report");
        if(transaction.isActive()) transaction.commit();

        log.info("returning report object");
        return report;
    }

    /**
     * Method (static class method)
     * getReport: retrieves a single report object using report name
     * @param reportName: String report name
     * @return Report object
     */
    public static Report getReport(String reportName) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction for getting report");
        if(!transaction.isActive()) transaction.begin();

        // Creating criteria to fetch object from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaReports = builder.createQuery(Report.class);
        Root<Report> reportRoot = criteriaReports.from(Report.class);
        criteriaReports.select(reportRoot);

        // Creating a parameter for the criteria
        ParameterExpression<String> rName = builder.parameter(String.class);

        // Creating a predicate for the criteria
        Predicate projectRestriction = builder.equal(reportRoot.get(Report_.name), rName);
        criteriaReports.where(projectRestriction);

        // Creating a TypedQuery using CriteriaQuery
        TypedQuery<Report> query = session.createQuery(criteriaReports);
        query.setParameter(rName, reportName);
        Report report = query.getSingleResult();

        log.info("Committing transaction for getting report");
        if(transaction.isActive()) transaction.commit();

        log.info("returning report object");
        return report;
    }

    /**
     * Method (static class method)
     * getReport: retrieves a list of report objects
     * @return List of Report objects
     */
    public static List<Report> getReportAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        log.info("Starting transaction for getting multiple reports");
        if(!transaction.isActive()) transaction.begin();

        // Creating criteria to fetch object from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaProjects = builder.createQuery(Report.class);
        Root<Report> root = criteriaProjects.from(Report.class);
        criteriaProjects.select(root);

        // Creating a TypedQuery using CriteriaQuery
        TypedQuery<Report> query = session.createQuery(criteriaProjects) ;
        List<Report> reports = query.getResultList();

        log.info("Committing transaction for getting multiple reports");
        if(transaction.isActive()) transaction.commit();

        log.info("returning report objects");
        return reports;
    }

}
