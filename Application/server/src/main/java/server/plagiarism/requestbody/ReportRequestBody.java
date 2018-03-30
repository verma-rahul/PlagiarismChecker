package server.plagiarism.requestbody;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * SaveReportRequest: class mimics the json object sent from client to save report
 */
public class ReportRequestBody {

    // Variable to store name of report
    private String name;

    // Variable to store summary of report
    private String resultsummary;

    // Variable to store project 1 id of report
    private int project1id;

    // Variable to store project 2 id of report
    private int project2id;

    // Variable to store similarity score of report
    private float similarityscore;

    /**
     * Method
     * getName: getter method for name property
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Method
     * setName: setter method for name property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method
     * getResultsummary: getter method for summary property
     * @return String resultSummary
     */
    public String getResultsummary() {
        return resultsummary;
    }

    /**
     * Method
     * setResultsummary: setter method for summary property
     */
    public void setResultsummary(String resultsummary) {
        this.resultsummary = resultsummary;
    }

    /**
     * Method
     * getProject1id: getter method for project1 id property
     * @return integer project 1 id
     */
    public int getProject1id() {
        return project1id;
    }

    /**
     * Method
     * setProject1id: setter method for project1id property
     */
    public void setProject1id(int project1id) {
        this.project1id = project1id;
    }

    /**
     * Method
     * getProject2id: getter method for project 2 id property
     * @return integer project 2 id
     */
    public int getProject2id() {
        return project2id;
    }

    /**
     * Method
     * setProject2id: setter method for project2id property
     */
    public void setProject2id(int project2id) {
        this.project2id = project2id;
    }

    /**
     * Method
     * getSimilarityScore: setter method for similarityScore property
     * @return similarity score
     */
    public float getSimilarityscore() {
        return similarityscore;
    }

    /**
     * Method
     * setSimilarityScore: setter method for similarityScore property
     */
    public void setSimilarityscore(float similarityScore) {
        this.similarityscore = similarityScore;
    }
}
