package server.plagiarism.interceptor.hibernate;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.entity.Report_;
import server.plagiarism.util.hibernate.HibernateUtil;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * ProjectIdNullOnDeleteInterceptor: used to intercept the database operations and perform manual cleanup
 * of project ids from report table
 */
public class ProjectIdNullOnDeleteInterceptor extends EmptyInterceptor {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(ProjectIdNullOnDeleteInterceptor.class);

    /**
     * Method (overridden)
     * onDelete: is called before a delete operation is performed on any database table
     * @param entity: Object being deleted
     * @param id
     * @param state
     * @param propertyNames
     * @param types
     */
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        log.info("inside interceptor's onDelete method");
        if(entity instanceof Project) {
            Project project = (Project) entity;
            List<Report> reports = getReportsForInterceptor(project);
            if(reports != null && reports.size() != 0) {
                updateReportsForNullProjects(reports, project.getProjectId());
            }
        }
    }

    /**
     * Method
     * updateReportsForNullProjects: updates reports project in database with null where a match is found
     * @param reports: A list of reports from database
     * @param projectId: integer project id
     */
    private void updateReportsForNullProjects(List<Report> reports, int projectId) {
        for(Report report : reports) {
            if(report.getProject1() != null && report.getProject1().getProjectId() == projectId) {
                report.setProject1(null);
            }
            if(report.getProject2() != null && report.getProject2().getProjectId() == projectId) {
                report.setProject2(null);
            }
            // get session for entity delete and update report schema
            HibernateUtil.getSession().saveOrUpdate(report);
        }
    }

    /**
     * Method (static class method)
     * getReportsForInterceptor: get a list of reports just for the interceptor which contains the given project
     * @param project: Project object
     * @return List of Reports
     */
    private static List<Report> getReportsForInterceptor(Project project) {
        Session session = HibernateUtil.getSession();
        // transaction is active here from entity delete

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaReports = builder.createQuery(Report.class);
        Root<Report> reportRoot = criteriaReports.from(Report.class);
        criteriaReports.select(reportRoot);

        ParameterExpression<Project> prj = builder.parameter(Project.class);
        Predicate projectRestriction = builder.or(
                builder.equal(reportRoot.get(Report_.project1), prj),
                builder.equal(reportRoot.get(Report_.project2), prj)
        );

        criteriaReports.where(projectRestriction);

        TypedQuery<Report> query = session.createQuery(criteriaReports);
        query.setParameter(prj, project);
        return query.getResultList();
    }
}
