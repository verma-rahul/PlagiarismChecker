package server.plagiarism.entity;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * IEntityFactory: declaring methods for instantiating entities
 */
public interface IEntityFactory {
    // abstract method declaration for instantiating Project
    Project makeProject();

    // abstract method declaration for instantiating File
    File makeFile();

    // abstract method declaration for instantiating Report
    Report makeReport();
}
