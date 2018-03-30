package server.plagiarism.exceptions;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * EntityParameterException: custom exception class to describe entity parameter mismatch
 */
public class EntityParameterException extends Exception {
    // Variable to store the description of the exception thrown
    private String description;

    /**
     * Constructor (parameterized)
     * @param str: String description of the exception
     */
    public EntityParameterException(String str) {
        description = str;
    }

    /**
     * Method (overridden)
     * getMessage: returns the description of the exception
     * @return String description of the exception
     */
    @Override
    public String getMessage() {
        return description;
    }
}
