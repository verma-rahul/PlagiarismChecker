package server.plagiarism.requestbody;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0

 * Class
 * UploadProjectRequest: class mimics the json object sent from client
 */
public class ProjectRequestBody {

    // directory is the directory path in uploadRequest
    private String directory;

    /**
     * Constructor (default)
     */
    public ProjectRequestBody() {
    }

    /**
     * Constructor (parameterized)
     * @param directory: Directory path of the project
     */
    public ProjectRequestBody(String directory) {
        this.directory=directory;
    }

    /**
     * Method
     * getDirectory: getter method to retrieve directory value
     * @return String directory path
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Method
     * setDirectory: setter method to set directory value
     * @param directory: String path of the folder
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }


}
