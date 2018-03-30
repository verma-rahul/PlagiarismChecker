package server.plagiarism.service;

import org.junit.Assert;
import org.junit.Test;
import server.plagiarism.requestbody.ProjectRequestBody;
/*
  * @author Rahul Verma [verma.rah@husky.neu.edu]
  *
**/ 
public class TestUploadRequestService {

    @Test
    public void test1ProjectRequestValidation() {
        Assert.assertTrue(new UploadRequestServiceImpl().isValid(createProjectRequestBodyValid()));
    }

    @Test
    public void test2ProjectRequestValidation() {
        Assert.assertFalse(new UploadRequestServiceImpl().isValid(createProjectRequestBodyInvalid()));
    }

    private ProjectRequestBody createProjectRequestBodyValid() {
        return new ProjectRequestBody(System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH"));
    }

    private ProjectRequestBody createProjectRequestBodyInvalid() {
        return new ProjectRequestBody(System.getenv("HOMEDRIVE") + System.getenv("HOMEPATH")
                +"/fakepath");
    }
}
