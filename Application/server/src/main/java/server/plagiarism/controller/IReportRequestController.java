package server.plagiarism.controller;

import org.springframework.http.HttpStatus;
import server.plagiarism.entity.Project;
import server.plagiarism.entity.Report;
import server.plagiarism.requestbody.ReportRequestBody;

import java.util.List;

/**
 * @author Rahul Verma [verma.rah@husky.neu.edu]
 * @version 1.0
 *
 * Interface
 * IReportRequestController: declaring rest service methods related to report
 */
interface IReportRequestController {
    // abstract method declaration for saving report
    HttpStatus saveReport(ReportRequestBody reportRequestBody);

    // abstract method declaration for retrieving a list of reports
    List<Report> getReportList();

    // abstract method declaration for deleting a report
    List<Report> deleteReport(ReportRequestBody reportRequestBody, int id);

    // abstract method declaration for retrieving a report based on given id
    Report getReport(Integer id);

    // abstract method declaration for generating a report from 2 given projects
    Report generateReport(Project[] projects);
}
