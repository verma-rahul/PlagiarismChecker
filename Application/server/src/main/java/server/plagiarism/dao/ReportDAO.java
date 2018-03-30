package server.plagiarism.dao;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.exceptions.ReportNotFoundException;
import server.plagiarism.util.db.DBUtil;
import server.plagiarism.util.db.ReportQueryUtil;
import server.plagiarism.util.fs.FSUtil;

import javax.persistence.NoResultException;
import java.io.IOException;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 @version 1.0
 Class
 ReportDAO: data access object class to be used to interact with MySQL and AWS S3 for Reports
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ReportDAO implements FSReport, DBReport {

    private static ReportDAO instanceReportDAO;

    private ReportDAO() {
    }

    /**
     * Method
     * getInstanceProjectDAO: used to get singleton instance of the class
     * @return ReportDAO singleton object
     */
    static ReportDAO getInstanceReportDAO() {
        if(instanceReportDAO == null)
            instanceReportDAO = new ReportDAO();
        return instanceReportDAO;
    }

    /**
     * Method: readDB
     * Purpose: to read Report objects from database based on name
     * @param name
     * @return Report
     * @throws ReportNotFoundException
     */
    @Override
    public Report readDB(String name) throws ReportNotFoundException {
        return ReportQueryUtil.getReport(name);
    }

    /**
     * Method: readFS
     * Purpose: to read/download Report object from AWS S3
     * @param reportName
     * @return Report
     */
    @Override
    public Report readFS(String reportName) {
        FSUtil.downloadReportFromS3(reportName);
        return FSUtil.loadReportContentFromLocalFile(reportName);
    }

    /**
     * Method: readDB
     * Purpose: to read Report objects from database based on id
     * @param id
     * @return Report
     * @throws ReportNotFoundException
     */
    @Override
    public Report readDB(int id) throws ReportNotFoundException {
        Report report = null;
        try {
             report = ReportQueryUtil.getReport(id);
        } catch (NoResultException e) {
            return null;
        }
        return report;
    }

    /**
     * Method: createFS
     * Purpose: to create Report objects in AWS S3
     * @param report
     * @return boolean
     * @throws IOException
     * @throws EntityParameterException
     */
    @Override
    public boolean createFS(Report report) throws IOException, EntityParameterException {
        FSUtil.uploadReportToS3(report);
        return true;
    }

    /**
     * Method: createDB
     * Purpose: to write Report objects to database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    @Override
    public boolean createDB(Object obj) throws EntityParameterException {
        if(obj instanceof Report) {
            DBUtil.saveOrUpdateEntity(obj);
        } else {
            throw new EntityParameterException("Required object of class Report, found:" + obj.getClass());
        }
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
        if(obj instanceof Report) {
            DBUtil.deleteEntity(obj);
        } else {
            throw new EntityParameterException("Required object of class Report, found:" + obj.getClass());
        }
        return true;
    }

    /**
     * Method: deleteDB
     * Purpose: to delete Report objects from database based on id
     * @param id
     * @return boolean
     * @throws ReportNotFoundException
     */
    @Override
    public boolean deleteDB(int id) throws ReportNotFoundException {
        Report report = readDB(id);
        if(report == null)
            throw new ReportNotFoundException("Report with id: " + id + " not found in database");
        DBUtil.deleteEntity(report);
        return true;
    }

    /**
     * Method: deleteFS
     * Purpose: to delete Report object from AWS S3
     * @param reportName
     * @return boolean
     */
    @Override
    public boolean deleteFS(String reportName) {
        FSUtil.deleteFileFromS3(reportName);
        return true;
    }

    /**
     * Method: updateDB
     * Purpose: to update Project objects to database
     * @param obj
     * @return boolean
     * @throws EntityParameterException
     */
    @Override
    public boolean updateDB(Object obj) throws EntityParameterException{
        if(obj instanceof Report) {
            DBUtil.saveOrUpdateEntity(obj);
        } else {
            throw new EntityParameterException("Required object of class Report, found:" + obj.getClass());
        }
        return true;
    }

}
