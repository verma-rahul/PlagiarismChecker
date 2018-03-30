package server.plagiarism.util.common;

import server.plagiarism.entity.Project;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
  * @author Rahul Verma [verma.rah@husky.neu.edu]
 * Version: 1.0
 *
 * Class
 * CommonUtil: utility class providing common functions for other classes
 */
public class CommonUtil {

    /**
     * Function (static class method)
     * loadPropertiesFile: used to load properties from a properties file
     * Input: Name of the properties file
     * Output: Properties object with all the properties loaded
     * @param propertiesFile: String
     * @return Properties
     */
    public static Properties loadPropertiesFile(String propertiesFile) {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propertiesFile);
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Function (static class method)
     * parseAndCheckProjectName: used to check whether a given aws key from S3 belongs to a current project
     * Input: aws key and name of the current project
     * Output: boolean value indicating whether the provided key is needed or not
     * @param awsKey: String aws object key
     * @param projectName: String name of the project
     * @return boolean
     */
    public static boolean parseAndCheckProjectName(String awsKey, String projectName) {
        int index = awsKey.indexOf("/");
        if(index == -1) {
            return awsKey.equals(projectName);
        } else {
            String name = awsKey.substring(0, index);
            return name.equals(projectName);
        }
    }

    /**
     * Method (static class method)
     * updateProjectAndFileId: updates the target project with project id and all the file ids from source project
     * @param sourceProject: Project source
     * @param targetProject: Project target
     */
    public static void updateProjectAndFileId(Project sourceProject, Project targetProject) {
        targetProject.setProjectId(sourceProject.getProjectId());
        for(server.plagiarism.entity.File file : sourceProject.getFiles()) {
            String fullName = file.getDirectory() + "/" + file.getName();
            for(server.plagiarism.entity.File filetarget : targetProject.getFiles()) {
                if(fullName.equals(filetarget.getDirectory() + "/" + filetarget.getName())) {
                    filetarget.setFileId(file.getFileId());
                    break;
                }
            }
        }
    }

    /**
     * Method (static class method)
     * getProjectNameIndexFromPath: returns the index of the project name on the directory string
     * @param projectPath: full name of the project including the path
     * @return index where the project name begins
     */
    public static int getProjectNameIndexFromPath(String projectPath) {
        int indexKeyName;
        if(projectPath.lastIndexOf('/') != -1) {
            indexKeyName = projectPath.lastIndexOf('/') + 1;
        } else {
            indexKeyName = projectPath.lastIndexOf('\\') + 1;
        }
        return indexKeyName;
    }
}
