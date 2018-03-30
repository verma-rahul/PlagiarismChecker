package server.plagiarism.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import server.plagiarism.requestbody.ProjectRequestBody;

import java.io.File;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * UploadRequestServiceImpl Class to validate incoming JSON Body in uploadProject Request
 */
@Service
public class UploadRequestServiceImpl implements UploadRequestService {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(UploadRequestServiceImpl.class);

    /**
     * Checks if the incoming request is valid or nor
     * @param request: the incoming request body
     * @return boolean
     */
    @Override
    public boolean isValid(ProjectRequestBody request) {
        log.info("validating request " + request.getDirectory());
        return ((request != null) &&
                (!request.getDirectory().isEmpty()) &&
                ((new File(request.getDirectory())).isDirectory()) &&
                ((new File(request.getDirectory())).listFiles().length != 0));
    }
}
