package server.plagiarism.util.fs;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import org.apache.commons.io.FileUtils;
import server.plagiarism.entity.EntityFactory;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.exceptions.EntityParameterException;
import server.plagiarism.util.common.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Class
 * FSUtil: utility class for file system (S3 and local) related operations
 */
public class FSUtil {

    // Variable for storing the name of properties file
    private static final String FILE_PROJECT_PROPERTIES = "project.properties";

    // Variable for storing temp directory name for projects
    private static final String TEMP_LOCAL_PROJECT = "tempLocation";

    // Variable for storing temp directory name for projects
    private static final String TEMP_LOCAL_REPORT = "tempLocationReports";

    // Variable for storing system home drive property
    private static final String SYSTEM_HOME_DRIVE = System.getenv("HOMEDRIVE");

    // Variable for storing system home drive property
    private static final String SYSTEM_HOME_PATH = System.getenv("HOMEPATH");

    // Variable to store project properties
    private static final Properties projectProperties = CommonUtil.loadPropertiesFile(FILE_PROJECT_PROPERTIES);

    /**
     * Method (static class method)
     * deleteFileFromS3: used to delete a single file from AWS S3
     * @param docKey: String name of the file
     * @return boolean
     */
    public static boolean deleteFileFromS3(String docKey) {
        AmazonS3 amazonS3Client = S3Util.getAmazonS3Client();
        amazonS3Client.deleteObject(new DeleteObjectRequest(
                S3Util.getS3BucketName(),
                docKey
        ));
        amazonS3Client.shutdown();
        return true;
    }

    /**
     * Method (static class method)
     * downloadReportFromS3: download a single report from AWS S3 to local temp location
     * @param reportName: String name of the report
     * @return boolean
     */
    public static boolean downloadReportFromS3(String reportName) {
        File reportTempLocal = new File(getTempLocationLocalForReports() + "/" + reportName);
        TransferManager transferManager = S3Util.getTransferManager();
        Download download =
                transferManager.download(S3Util.getS3BucketName(), reportName, reportTempLocal);
        S3Util.pollTransfer(download);
        transferManager.shutdownNow();
        return true;
    }

    /**
     * Method (static class method)
     * uploadReportToS3: uploads a single report to AWS S3
     * @param report: Report
     * @return boolean
     * @throws IOException
     * @throws EntityParameterException
     */
    public static boolean uploadReportToS3(Report report) throws IOException, EntityParameterException{
        if(report == null) {
            throw new EntityParameterException("Expected parameter Report, found: null");
        }
        createReportLocalAndUploadToS3(report);
        return true;
    }

    /**
     * Method (static class method)
     * createReportLocalAndUploadToS3: creates report file in temp location locally and uploads to AWS S3
     * @param report: Report object
     * @throws IOException
     */
    private static void createReportLocalAndUploadToS3(Report report) throws IOException {
        uploadFileToS3(
                report.getName(),
                new File(getTempLocationLocalForReports() + "/" + report.getName()),
                report);
    }

    /**
     * Method (static class method)
     * uploadFileToS3: uploads a single file to AWS S3
     * @param fileKey: String name of the file
     * @param file: File object
     * @param report Report Object
     * @throws IOException
     */
    private static void uploadFileToS3(String fileKey, File file, Report report)
            throws IOException {
        FileUtils.writeStringToFile(file, report.getContent(), "UTF-8");
        TransferManager transferManager = S3Util.getTransferManager();
        Upload upload = transferManager.upload(S3Util.getS3BucketName(), fileKey, file);
        S3Util.pollTransfer(upload);
        transferManager.shutdownNow();
    }

    /**
     * Method (static class method)
     * uploadFolderToS3: uploads a single folder to AWS S3
     * @param rootKey: String name of the folder
     * @param rootDir: String full directory path of the folder
     * @return boolean
     */
    public static boolean uploadFolderToS3(String rootKey, String rootDir) {
        File folder = new File(rootDir);
        TransferManager transferManager = S3Util.getTransferManager();
        MultipleFileUpload upload =
                transferManager.uploadDirectory(S3Util.getS3BucketName(), rootKey, folder, true);
        S3Util.pollTransfer(upload);
        transferManager.shutdownNow();
        return true;
    }

    /**
     * Method (static class method)
     * deleteFolderFromS3: deletes a single folder from AWS S3
     * @param rootKey: String name of the folder
     * @return boolean
     */
    public static boolean deleteFolderFromS3(String rootKey) {
        AmazonS3 amazonS3Client = S3Util.getAmazonS3Client();
        // Multi-object delete by specifying only keys (no version ID).
        DeleteObjectsRequest multiObjectDeleteRequest =
                new DeleteObjectsRequest(S3Util.getS3BucketName()).withQuiet(false);
        // Create request that include only object key names.
        List<DeleteObjectsRequest.KeyVersion> keys = S3Util.getObjectKeyList(amazonS3Client, rootKey);
        multiObjectDeleteRequest.setKeys(keys);
        S3Util.deleteNoVersionMultipleObjects(amazonS3Client, multiObjectDeleteRequest);
        amazonS3Client.shutdown();
        return true;
    }

    /**
     * Method (static class method)
     * downloadFolderFromS3: downloads a single folder from AWS S3
     * @param projectName: String name of the project
     */
    public static void downloadFolderFromS3(String projectName) {
        TransferManager transferManager = S3Util.getTransferManager();
        for(String str : FSUtil.getFileStructureAndSizeFromS3(projectName)) {
            Download download = transferManager.download(
                    new GetObjectRequest(S3Util.getS3BucketName(), str.split(",")[0])
                    , new java.io.File(getTempLocationLocalForProjects() + "/" + str.split(",")[0]));
            S3Util.pollTransfer(download);
        }
        transferManager.shutdownNow();
    }

    /**
     * Method (static class method)
     * getFileStructureAndSizeFromS3: retrieves list of S3 keys (full file names) and their sizes from AWS S3
     * @param projectName: String name of the project
     * @return List of Strings
     */
    public static List<String> getFileStructureAndSizeFromS3(String projectName) {
        AmazonS3 amazonS3Client = S3Util.getAmazonS3Client();
        List<S3ObjectSummary> objectSummaries = S3Util.getAllKeysFromProject(amazonS3Client, projectName);
        List<String> fileAndSize = new ArrayList<>();
        for(S3ObjectSummary objectSummary : objectSummaries) {
            fileAndSize.add(objectSummary.getKey()+","+objectSummary.getSize());
        }
        amazonS3Client.shutdown();
        return fileAndSize;
    }

    /**
     * Method (static class method)
     * initProjectFromLocal: read a project from local temp path and return a project object
     * @param projectPath: String directory of the project
     * @return Project object
     */
    public static Project initProjectFromLocal(String projectPath) {
        String rootKey = projectPath.substring(CommonUtil.getProjectNameIndexFromPath(projectPath));
        Project project = EntityFactory.getInstance().makeProject();
        List<server.plagiarism.entity.File> files = new ArrayList<>();
        initFileList(files, new File(projectPath), rootKey, project);
        project.setFiles(files);
        project.setName(rootKey);
        return project;
    }

    /**
     * Method (static class method)
     * initProjectFromLocalUsingName: read a project from local temp path and return a project object
     * @param projectName: String name of the project
     * @return Project object
     */
    public static Project initProjectFromLocalUsingName(String projectName) {
        return initProjectFromLocal(getTempLocationLocalForProjects() + "/" + projectName);
    }

    /**
     * Method (static class method)
     * initFileList: initializes the list of files by the files present on the directory path
     * @param files: List of entity File
     * @param tempProjectPath: File path of the project
     * @param relPath: String relative path starting with the name of the project
     * @param project: Project object
     */
    private static void initFileList(List<server.plagiarism.entity.File> files,
                                    File tempProjectPath, String relPath, Project project) {
        File[] listFiles = tempProjectPath.listFiles();
        for(int index = 0; index < listFiles.length; index++) {
            if(listFiles[index].isFile()) {
                try {
                    addFile(files, listFiles[index], relPath, project);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(listFiles[index].isDirectory()) {
                initFileList(files, listFiles[index], relPath + "/" + listFiles[index].getName(), project);
            }
        }

    }

    /**
     * Method (static class method)
     * addFile: adds the file to list after reading from local filesystem
     * @param files: List of files
     * @param fileParam: File object from local filesystem
     * @param directory: String directory of file
     * @param project: Project object
     * @throws IOException
     */
    private static void addFile(List<server.plagiarism.entity.File> files,
                                File fileParam, String directory, Project project) throws IOException{
        server.plagiarism.entity.File file = EntityFactory.getInstance().makeFile();
        String name = fileParam.getName();
        String content = FileUtils.readFileToString(fileParam,"UTF-8");
        file.setContent(content); file.setDirectory(directory); file.setName(name); file.setProject(project);
        files.add(file);
    }

    /**
     * Function (static class method)
     * loadReportContentFromLocalFile: read a report from local file system and initialize the report object
     * Input: name of the report
     * Output: report object loaded with the contents of the local file
     * @param reportName
     */
    public static Report loadReportContentFromLocalFile(String reportName)  {
        File reportLocal = new File(
                new File(FSUtil.getTempLocationLocalForReports()).getAbsolutePath() +
                        "/" + reportName);
        Report report = EntityFactory.getInstance().makeReport();
        try {
            String reportSummary = FileUtils.readFileToString(reportLocal, "UTF-8");
            report.setContent(reportSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return report;
    }

    /**
     * Method (static class method)
     * deleteFolderFromLocalTemp: deletes a folder from local filesystem
     * @param projectName: name of the project
     */
    public static void deleteFolderFromLocalTemp(String projectName) {
        try {
            FileUtils.deleteDirectory(new File(getTempLocationLocalForProjects() + "/" + projectName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method (static class method)
     * cleanReportFolderInLocalTemp: deletes all reports in the local temp report folder
     */
    public static void cleanReportFolderInLocalTemp() {
        try {
            FileUtils.cleanDirectory(new File(getTempLocationLocalForReports()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method (static class method)
     * getTempLocationLocalForProjects: get the directory of projects stored on local filesystem
     * @return String directory of projects
     */
    private static String getTempLocationLocalForProjects() {
        return SYSTEM_HOME_DRIVE + SYSTEM_HOME_PATH + "/" + projectProperties.getProperty(TEMP_LOCAL_PROJECT);
    }

    /**
     * Method (static class method)
     * getTempLocationLocalForReports: get the directory of reports stored on local filesystem
     * @return String directory of reports
     */
    public static String getTempLocationLocalForReports() {
        return SYSTEM_HOME_DRIVE + SYSTEM_HOME_PATH + "/" + projectProperties.getProperty(TEMP_LOCAL_REPORT);
    }

    /**
     * Method (static class method)
     * getTempLocationLocalForReports: get the directory of reports stored on local filesystem
     * @return String directory of reports
     */
    public static String getTempRelativeLocationLocalForReports() {
        return projectProperties.getProperty(TEMP_LOCAL_REPORT);
    }
}
