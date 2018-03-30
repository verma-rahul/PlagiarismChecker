package server.plagiarism.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * Project: to represent Projects
 */
@Entity
@Table(name = "PROJECT")
public class Project {

    // Variable for storing unique project id
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    // Variable to store list of files and mapped bi directionally with File
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<File> files;

    // Variable to store name of the project
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    // getter method for project id property
    public int getProjectId() {
        return projectId;
    }

    // setter method for project id property
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    // getter method for files property
    public List<File> getFiles() {
        return files;
    }

    // setter method for files property
    public void setFiles(List<File> files) {
        this.files = files;
    }

    // getter method for name property
    public String getName() {
        return name;
    }

    // setter method for name property
    public void setName(String name) {
        this.name = name;
    }

}
