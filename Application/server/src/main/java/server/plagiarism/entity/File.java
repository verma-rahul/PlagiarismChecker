package server.plagiarism.entity;

import javax.persistence.*;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * File: to represent file documents
 */
@Entity
@Table(name = "FILE")
public class File extends Document {

    // Variable to store unique file id
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileId;

    // Variable to store project for bi directional mapping with Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    private Project project;

    // getter for project property
    public Project getProject() {
        return project;
    }

    // setter for project property
    public void setProject(Project project) {
        this.project = project;
    }

    // getter for file id property
    public int getFileId() {
        return fileId;
    }

    // setter for file id property
    public void setFileId(int docId) {
        this.fileId = docId;
    }

}
