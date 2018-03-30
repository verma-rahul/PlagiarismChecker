package server.plagiarism.util.fs;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.plagiarism.util.common.CommonUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * S3Util: used for connecting to and performing operations for AWS S3
 */
public class S3Util {

    // Logger (slf4j) variable for logging events
    private static final Logger log = LoggerFactory.getLogger(S3Util.class);

    // Variable for storing AWS S3 bucket name key for properties file
    private static final String KEY_BUCKET_NAME = "bucketname";

    // Variable for storing AWS S3 region key for properties file
    private static final String KEY_REGION = "region";

    // Variable for storing AWS S3 name of properties file
    private static final String FILE_S3_PROPERTIES = "awsS3.properties";

    // Variable for storing s3 properties
    private static final Properties s3Properties = CommonUtil.loadPropertiesFile(FILE_S3_PROPERTIES);

    // Variable for storing aws credentials
    private static final AWSCredentials awsCredentials = loadCredentials();

    /**
     * Constructor (default)
     * S3Util: made private to force usage of getters
     */
    private S3Util() {}

    /**
     * Method (static class method)
     * getTransferManager: builds and returns an AWS S3 transfer manager object
     * @return TransferManager object
     */
    static TransferManager getTransferManager() {
        return TransferManagerBuilder.standard()
                .withS3Client(getAmazonS3Client())
                .build();
    }

    /**
     * Method (static class method)
     * getAmazonS3Client: get an S3 client to interact with S3
     * @return AmazonS3 object
     */
    static AmazonS3 getAmazonS3Client() {
        return getS3ClientBuilder(
            awsCredentials,
            s3Properties.getProperty(KEY_REGION));
    }

    /**
     * Method (static class method)
     * getS3BucketName: returns the name of AWS S3 bucket
     * @return String name of the bucket
     */
    static String getS3BucketName() {
        return s3Properties.getProperty(KEY_BUCKET_NAME);
    }

    /**
     * Method (static class method)
     * loadCredentials: loads the AWS credentials
     * @return AWSCredentials object
     */
    private static AWSCredentials loadCredentials() {
        try {
            AWSCredentials awsCredentials = new ProfileCredentialsProvider().getCredentials();
            return awsCredentials;
        } catch(Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
    }

    /**
     * Method (static class method)
     * getS3ClientBuilder: builds and returns an S3 client builder
     * @param awsCredentials: AWSCredentials
     * @param region: String region name
     * @return AmazonS3
     */
    private static AmazonS3 getS3ClientBuilder(AWSCredentials awsCredentials, String region) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    /**
     * Method (static class method)
     * deleteNoVersionMultipleObjects: deletes multiple version less objects from AWS S3
     * @param amazonS3Client: AmazonS3 client
     * @param deleteObjectsRequest: DeleteObjectsRequest object
     */
    static void deleteNoVersionMultipleObjects(AmazonS3 amazonS3Client, DeleteObjectsRequest deleteObjectsRequest) {
        DeleteObjectsResult delObjRes = null;
        try {
            delObjRes = amazonS3Client.deleteObjects(deleteObjectsRequest);
            log.info("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
        } catch (MultiObjectDeleteException mode) {
            log.info("%s \n", mode.getMessage());
            log.info("No. of objects successfully deleted = %s\n", mode.getDeletedObjects().size());
            log.info("No. of objects failed to delete = %s\n", mode.getErrors().size());
            log.info("Printing error data...\n");
            for (MultiObjectDeleteException.DeleteError deleteError : mode.getErrors()){
                log.info("Object Key: %s\t%s\t%s\n",
                        deleteError.getKey(), deleteError.getCode(), deleteError.getMessage());
            }
        }

    }

    /**
     * Method (static class method)
     * getObjectKeyList: gets a list of object keys from AWS S3
     * @param amazonS3Client: AmazonS3 client
     * @param rootKey: String prefix of the keys
     * @return List of DeleteObjectsRequest.KeyVersion objects
     */
    static List<DeleteObjectsRequest.KeyVersion> getObjectKeyList(AmazonS3 amazonS3Client, String rootKey) {
        List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<>();
        for (S3ObjectSummary objectSummary : getAllObjectSummary(amazonS3Client, rootKey)) {
            if(CommonUtil.parseAndCheckProjectName(objectSummary.getKey(), rootKey))
                keys.add(new DeleteObjectsRequest.KeyVersion(objectSummary.getKey()));
        }
        return keys;
    }

    /**
     * Method (static class method)
     * getAllObjectSummary: builds and returns a list of S3ObjectSummary objects
     * @param amazonS3Client: AmazonS3 client
     * @param folderName: String name of the folder
     * @return List of S3ObjectSummary objects
     */
    static List<S3ObjectSummary> getAllObjectSummary(AmazonS3 amazonS3Client, String folderName) {
        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
        for(ListObjectsV2Result result : getObjectResults(amazonS3Client, folderName)) {
            objectSummaries.addAll(result.getObjectSummaries());
        }
        return objectSummaries;
    }

    /**
     * Method (static class method)
     * getAllKeysFromProject: builds and returns a list of S3ObjectSummary objects
     * @param amazonS3Client: AmazonS3 client
     * @param rootKey: String name of the folder
     * @return List of S3ObjectSummary objects
     */
    static List<S3ObjectSummary> getAllKeysFromProject(AmazonS3 amazonS3Client, String rootKey) {
        List<S3ObjectSummary> requiredKeys = new ArrayList<>();
        for(S3ObjectSummary objectSummary : getAllObjectSummary(amazonS3Client, rootKey)) {
            if(CommonUtil.parseAndCheckProjectName(objectSummary.getKey(), rootKey))
                requiredKeys.add(objectSummary);
        }
        return requiredKeys;
    }

    /**
     * Method (static class method)
     * getObjectResults: gets a list of object results
     * @param amazonS3Client: Amazon S3 client
     * @param folderName: String name of the folder
     * @return List of ListObjectsV2Result objects
     */
    private static List<ListObjectsV2Result> getObjectResults(AmazonS3 amazonS3Client, String folderName) {
        List<ListObjectsV2Result> listObjectResults = new ArrayList<>();
        ListObjectsV2Request listObjectsV2Request =
                new ListObjectsV2Request()
                        .withBucketName(s3Properties.getProperty(KEY_BUCKET_NAME))
                        .withPrefix(folderName);
        ListObjectsV2Result result;
        do {
            result = amazonS3Client.listObjectsV2(listObjectsV2Request);
            listObjectResults.add(result);
            listObjectsV2Request.setContinuationToken(result.getNextContinuationToken());
        } while(result.isTruncated());

        return listObjectResults;
    }

    /**
     * Method (static class method)
     * pollTransfer: poll the ongoing transfer activity
     * @param transfer: Transfer object
     */
    static void pollTransfer(Transfer transfer) {
        long bytesTransferred = 0;
        while (!transfer.isDone()) {
            if(bytesTransferred != transfer.getProgress().getBytesTransferred()) {
                bytesTransferred = transfer.getProgress().getBytesTransferred();
                log.info("Transfer: " + transfer.getDescription() +
                        "  - State: " + transfer.getState() +
                        "  - Progress: " + transfer.getProgress().getBytesTransferred());
            }

        }
    }
}
