package server.plagiarism.service;

import server.plagiarism.requestbody.*;
/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface to Intercept the UploadProject Request
 */
public interface UploadRequestService {

    // Validates the incoming request and returns a boolean
    boolean isValid(ProjectRequestBody request);

}
