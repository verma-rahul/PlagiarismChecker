package server.plagiarism.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class (abstract)
 * Document: base class for files and reports
 */
@MappedSuperclass
abstract public class Document {

    // Variable to store name of the document
    @Column(name = "NAME", nullable = false)
    private String name;

    // Variable to store content of the document
    @Transient
    private String content;

    // Variable to store directory where the document lies
    @Column(name = "DIRECTORY", nullable = false)
    private String directory;

    // getter method for name property
    public String getName() {
        return name;
    }

    // setter method for name property
    public void setName(String name) {
        this.name = name;
    }

    // getter method for content property
    public String getContent() {
        return content;
    }

    // setter method for content property
    public void setContent(String content) {
        this.content = content;
    }

    // getter method for directory property
    public String getDirectory() {
        return directory;
    }

    // setter method for directory property
    public void setDirectory(String directory) {
        this.directory = directory;
    }

}
