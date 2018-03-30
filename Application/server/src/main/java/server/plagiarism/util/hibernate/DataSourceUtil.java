package server.plagiarism.util.hibernate;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

import static server.plagiarism.util.common.CommonUtil.loadPropertiesFile;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * DataSourceUtil: loads the basic data source configuration
 */
public class DataSourceUtil {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(DataSourceUtil.class);

    // Variable for storing MySQL Driver class name to be used
    private static final String KEY_MYSQL_DB_DRIVER_CLASS = "mysql.db.driver.class";

    // Variable for storing MySQL instance URL on AWS RDS
    private static final String KEY_MYSQL_INSTANCE_URL = "mysql.instance.url";

    // Variable for storing MySQL database name
    private static final String KEY_RDS_MYSQL_DB_NAME = "RDS_MYSQL_DB_NAME";

    // Variable for storing MySQL database username
    private static final String KEY_RDS_MYSQL_DB_USERNAME = "RDS_MYSQL_DB_USERNAME";

    // Variable for storing MySQL password
    private static final String KEY_RDS_MYSQL_DB_PASSWORD = "RDS_MYSQL_DB_PASSWORD";

    // Variable for storing the name of properties file
    private static final String FILE_DB_PROPERTIES = "db_public.properties";

    /**
     * Method (static class method)
     * getDataSource: creates, loads and returns an object of type DataSource
     * @return DataSource object with necessary configuration information
     */
    public static DataSource getDataSource() {
        log.info("retrieving data source object");
        return getDataSource(loadPropertiesFile(FILE_DB_PROPERTIES));
    }

    /**
     * Method (static class method)
     * getDataSource: returns a BasicDataSource object loaded with all configuration information
     * @param properties: Properties
     * @return BasicDataSource object with necessary configuration information
     */
    private static BasicDataSource getDataSource(Properties properties) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty(KEY_MYSQL_DB_DRIVER_CLASS));
        ds.setUrl(properties.getProperty(KEY_MYSQL_INSTANCE_URL)
                + System.getenv(KEY_RDS_MYSQL_DB_NAME));
        ds.setUsername(System.getenv(KEY_RDS_MYSQL_DB_USERNAME));
        ds.setPassword(System.getenv(KEY_RDS_MYSQL_DB_PASSWORD));
        return ds;
    }

}
