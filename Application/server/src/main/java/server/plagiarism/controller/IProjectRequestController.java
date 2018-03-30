package server.plagiarism.controller;

import org.springframework.http.HttpStatus;
import server.plagiarism.entity.Project;
import server.plagiarism.requestbody.ProjectRequestBody;

import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * IProjectRequestController: declaring rest service methods related to project
 */
public interface IProjectRequestController {
    // abstract method declaration for uploading project
    HttpStatus uploadProject(ProjectRequestBody request);

    // abstract method declaration for retrieving list of projects
    List<Project> getProjectList();

    // abstract method declaration for deleting a project
    List<Project> deleteProject(Project project);
}
