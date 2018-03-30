package server.plagiarism.entity;

/**
   * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * EntityFactory: singleton factory class to instantiate various entities
 */
public class EntityFactory implements IEntityFactory {

    // singleton instance to be created once during first call to getInstance
    private static IEntityFactory factoryInstance = null;

    // making constructor private to force the use of getInstance
    private EntityFactory() {
    }

    /**
     * Method
     * getInstance: returns an instance of EntityFactory class
     * @return EntityFactory object
     */
    public static IEntityFactory getInstance() {
        if(factoryInstance == null) {
            factoryInstance = new EntityFactory();
        }
        return factoryInstance;
    }

    /**
     * Method (overridden)
     * makeProject: to instantiate Project class
     * @return Project object instance
     */
    @Override
    public Project makeProject() {
        return new Project();
    }

    /**
     * Method (overridden)
     * makeFile: to instantiate File class
     * @return File object instance
     */
    @Override
    public File makeFile() {
        return new File();
    }

    /**
     * Method (overridden)
     * makeReport: to instantiate Report class
     * @return Report object instance
     */
    @Override
    public Report makeReport() {
        return new Report();
    }
}
