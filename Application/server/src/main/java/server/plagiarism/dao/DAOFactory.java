package server.plagiarism.dao;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * DAOFactory: singleton factory class to instantiate DAO entities
 */
public class DAOFactory implements IDAOFactory {

    // singleton instance to be created once during first call to getInstance
    private static IDAOFactory factoryInstance = null;

    // making constructor private to force the use of getInstance
    private DAOFactory() {
    }

    /**
     * Method
     * getInstance: returns an instance of DAOFactory class
     * @return DAOFactory object
     */
    public static IDAOFactory getInstance() {
        if(factoryInstance == null) {
            factoryInstance = new DAOFactory();
        }
        return factoryInstance;
    }

    /**
     * Method (overridden)
     * makeFileDAO: to instantiate FileDAO class
     * @return FileDAO object instance
     */
    @Override
    public FileDAO makeFileDAO() {
        return FileDAO.getInstanceFileDAO();
    }

    /**
     * Method (overridden)
     * makeProjectDAO: to instantiate ProjectDAO class
     * @return ProjectDAO object instance
     */
    @Override
    public ProjectDAO makeProjectDAO() {
        return ProjectDAO.getInstanceProjectDAO();
    }

    /**
     * Method (overridden)
     * makeReportDAO: to instantiate ReportDAO class
     * @return ReportDAO object instance
     */
    @Override
    public ReportDAO makeReportDAO() {
        return ReportDAO.getInstanceReportDAO();
    }
}
