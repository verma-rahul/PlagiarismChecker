package server.plagiarism.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * Report: to represent report documents
 */
@Entity
@Table(name = "REPORT")
public class Report extends Document {

    // Variable to store unique report id
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    // Variable to store similarity score
    @Column(name = "SIMILARITY_SCORE", nullable = false)
    private float similarityScore;

    // Variable to store project1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT1_ID")
    @JsonIgnore
    private Project project1;

    // Variable to store project2
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT2_ID")
    @JsonIgnore
    private Project project2;

    // Variable to store result summary
    @Transient
    private String resultSummary;

    // getter for report id property
    public int getReportId() {
        return reportId;
    }

    // setter for report id property
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    // getter for similarity score property
    public float getSimilarityScore() {
        return similarityScore;
    }

    // setter for similarity score property
    public void setSimilarityScore(float similarityScore) {
        this.similarityScore = similarityScore;
    }

    // getter for project 1 property
    public Project getProject1() {
        return project1;
    }

    // setter for project 1 property
    public void setProject1(Project project1) {
        this.project1 = project1;
    }

    // getter for project 2 property
    public Project getProject2() {
        return project2;
    }

    // setter for project 2 property
    public void setProject2(Project project2) {
        this.project2 = project2;
    }

    // getter for result summary property
    public String getResultSummary() {
        return resultSummary;
    }

    // setter for result summary property
    public void setResultSummary(String resultSummary) {
        this.resultSummary = resultSummary;
    }

}
