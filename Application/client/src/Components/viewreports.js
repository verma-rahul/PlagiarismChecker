/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 */
// Importing React's Components and React's and Node Libraries
import { render } from 'react-dom';
import  React, { Component }from 'react';
import * as B from 'react-bootstrap';
import { Route, Router, NavLink} from 'react-router-dom';
import axios from 'axios';
import ViewReport from './viewreport'
/**
 * ViewReports Component is displayed when URL is /viewreports
 *  It Displays Table of reports name , the asscociated projects
 * and the match perecent and Delete Button
 *  Delete send the Project to be deleted to Server
 */
class ViewReports extends Component {

    constructor(props){
        super(props);
        // URl for Get All reports Request
        this.reportsUrl="http://localhost:8080/api/report/all";
        // URl for Delete a Report Request
        this.reportUrl="http://localhost:8080/api/report/delete/";
        /**
         * The Initial State of the Class Where
         *  1. data: reportList
         */
        this.state={reports: [],
                    activeModal:false,
                    report:{}}
    }
    /** f(n) to Load All Reports from Server and Saves the result in Class's
     * state.data ;
     * @param {}
     * @return {Status}
     */
    loadReportsFromServer() {
        axios({
            method: 'get',
            url: this.reportsUrl}
        ).then(res => {
            // sets the returned data to this.state.data
            this.setState({ reports: res.data });
        }).catch(err => {
                console.error(err);
            });
    }

    /** f(n) to delete the given Report in Server and return the remaining list
     * and save that in  the result in Class's state.data ;
     * @param {Report}
     * @return {Status}
     */
    handleDelete(report) {
        // Post request with Report.reportId in with URL and Report as Body
        const confirmDelete = confirm("Delete Report: " + report.name + "?");
        if(confirmDelete)
        axios({
                method: 'delete',
                url: this.reportUrl+report.reportId,
                data: report
            }
        ).then(res => {
            // sets the returned data to this.state.data
            this.setState({ reports: res.data });
        })
            .catch(err => {
                console.error(err);
            });

    }
    /**
     * f(n) is called when Complonent is loaded , calls loadReportsFromServer()
     * to load Reportlist
     * @param {}
     * @return {Status}
     */
    componentDidMount() {
        this.loadReportsFromServer();
    }
    /** f(n) to view the given Report  ;
     * @param {Report}
     * @return {Status}
     */
    handleView(report) {
      this.setState({
                  activeModal:true,
                  report:report});
    }




    /**
     * Renders the Component
     */
    render(){
        /**
         * Variables which Stores the JSX row component for Report name
         * , associated projects Name and Similarity Score
         * the Id and value of the component is the projectID and diplayed value is
         * name
         */
        let rowNodes = this.state.reports.map(report => {
            return (
                <tr key={ report.reportId }>
                    {/*  Name of report*/}
                    <td>
                        {report.name}
                    </td>
                    {/*  Button to remove the given project*/}
                    <td className="text-nowrap">
                        <B.ButtonToolbar>
                            <B.Button  bsStyle="danger"
                                       onClick={this.handleDelete.bind(this, report)}>
                           <span className="glyphicon glyphicon-remove">
                           </span></B.Button>
                           <B.Button  bsStyle="success"
                                      onClick={this.handleView.bind(this, report)}>
                                      View
                           </B.Button>
                        </B.ButtonToolbar>
                    </td>
                    {/*  Similarity Score*/}
                    <td>
                        {(report.similarityScore*100).toFixed(2)+" %"}
                    </td>

                </tr>
            )
        })
        /**
         * The reutrned JSX Component which is Form which have table of project names
         */
        return(
            // Main Div : NOTE: B is for React-BootStrap
            <div className="container">
                {/* table having displaying the rowNodes Component define above*/}
                <div className="table-responsive">
                    <table className="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th><span className="caret"></span> Report Name</th>
                            <th><span className="caret"></span> Actions</th>
                            <th><span className="caret"></span> Score </th>
                        </tr>
                        </thead>
                        <tbody>
                        {/* Defined Above */}
                        { rowNodes }
                        </tbody>
                    </table>
                </div>
                {/*  Component to Show Display it is hidden till report not loaded*/}
                      <ViewReport activeModal={this.state.activeModal}
                                      report={this.state.report} >
                      </ViewReport>
            </div>
        );
    }
}

// Exports the Component
export default ViewReports;
