package server.plagiarism.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plagiarism.entity.File;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.interceptor.hibernate.ProjectIdNullOnDeleteInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static server.plagiarism.util.common.CommonUtil.loadPropertiesFile;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * HibernateUtil: used to load hibernate properties and provide session objects
 */
public class HibernateUtil {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);

    // Variable to store hibernate session factory after building required metadata
    private static SessionFactory sessionFactory;

    // Variable to store hibernate registry, used in building metadata
    private static StandardServiceRegistry registry;

    // Variable to store the name of the hibernate.properties file name
    private static final String FILE_HIBERNATE_PROPERTIES = "hibernate.properties";

    // Variable to store the key name in properties file for hibernate dialect to be used
    private static final String KEY_HIBERNATE_DIALECT = "hibernate.dialect";

    // Variable to store the key name in properties file for hibernate to ddl trigger to be used
    private static final String KEY_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    // Variable to store the key name in properties file for hibernate current session context
    private static final String KEY_HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";

    /**
     * Method (static class method)
     * getSessionFactory: used to get a singleton instance of hibernate session factory
     * @return SessionFactory singleton instance
     */
    private static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            registry = buildAndGetRegistry(new StandardServiceRegistryBuilder());
            sessionFactory =
                    buildAndGetMetatdata()
                    .getSessionFactoryBuilder()
                    .applyInterceptor(new ProjectIdNullOnDeleteInterceptor())
                    .build();
        }
        return sessionFactory;
    }

    /**
     * Method (static class method)
     * buildAndGetMetadata: uses registry and list of hibernate entity classes to build metadata
     * @return Metadata object
     */
    private static Metadata buildAndGetMetatdata() {
        MetadataSources metadataSources =
                new MetadataSources(registry)
                        .addAnnotatedClass(File.class)
                        .addAnnotatedClass(Project.class)
                        .addAnnotatedClass(Report.class);

        return metadataSources.getMetadataBuilder().build();
    }

    /**
     * Method (static class method)
     * buildAndGetRegistry: uses the hibernate properties to build registry for session factory
     * @param registryBuilder: StandardServiceRegistryBuilder
     * @return StandardServiceRegistry object
     */
    private static StandardServiceRegistry buildAndGetRegistry(StandardServiceRegistryBuilder registryBuilder) {
        Map<String, Object> settings = getHibernateRegistrySettings();
        registryBuilder.applySettings(settings);
        return registryBuilder.build();

    }

    /**
     * Method (static class method)
     * shutdown: destroys the registry which in turn closes the underlying hibernate session
     */
    public static void shutdown() {
        log.info("starting shutdown of current session");
        if(registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        log.info("shutdown of current session finished");
    }

    /**
     * Method (static class method)
     * getSession: returns a context-aware session object
     * @return Session object
     */
    public static Session getSession() {
        log.info("retrieving hibernate current session");
        return getSessionFactory().getCurrentSession();
    }

    /**
     * Method (static class method)
     * getHibernateRegistrySettings: returns a map of hibernate settings loaded from properties file
     *                               and system environment variables
     * @return Map of String keys and Object values of Hibernate Environment type
     */
    private static Map<String, Object> getHibernateRegistrySettings() {
        Map<String, Object> settings = new HashMap<>();
        setHibernateProperties(settings, loadPropertiesFile(FILE_HIBERNATE_PROPERTIES));
        return settings;
    }

    /**
     * Method (static class method)
     * setHibernateProperties: used to load hibernate properties in a provided map
     * @param settings: Map of String keys and Object values of Hibernate Environment type
     * @param hibernateProperties: Properties loaded from hibernate.properties file
     */
    private static void setHibernateProperties(Map<String, Object> settings, Properties hibernateProperties) {
        settings.put(Environment.DATASOURCE, DataSourceUtil.getDataSource());
        settings.put(Environment.DIALECT, hibernateProperties.getProperty(KEY_HIBERNATE_DIALECT));
        settings.put(Environment.HBM2DDL_AUTO, hibernateProperties.getProperty(KEY_HIBERNATE_HBM2DDL_AUTO));
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, hibernateProperties.getProperty(KEY_HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS));
    }
}
