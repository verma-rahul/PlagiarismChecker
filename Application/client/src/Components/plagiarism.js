
/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 */
// Importing React's Components and React's and Node Libraries
import { render } from 'react-dom';
import  React, { Component }from 'react';
import * as B from 'react-bootstrap';
import Styles from '../Styles/style';
import axios from 'axios';
import { Modal } from 'react-bootstrap'
import ReportContent from './reportcontent'
/**
 * Plagiarism Component is displayed when URL is /plagiarism
 * It Displays a dropdown menu which are name of projects
 * It also send the selected projects to Server for running plagiarism
 *  and returns the similarity score
 */
class Plagiarism extends Component {
    /** Constructor of the class Plagiarism
     */
    constructor(props){
        super(props);
        // URl for Get All Project Request
        this.projectsUrl="http://localhost:8080/api/project/all";
        // URL for Run Plagiarism
        this.plagiarismUrl="http://localhost:8080/api/report/plagiarism";
        /**
         * The Initial State of the Class Where
         *  1. data: projectList
         *  2. project1: Name of project 1 selected which is empty initialy
         *  3. project2: Name of project 2 selected which is empty initialy
         */

        this.state={data: [],
                    project1:"",
                    project2:"",
                    activeModal:false,
                    report:{}}

        this.handleUpload=this.handleUpload.bind(this)
    }
    /** f(n) to Load All Projects from Server and Saves the result in Class's
     * state.data ;
     * @param {}
     * @return {Status}
     */
    loadProjectsFromServer() {
        axios({
            method: 'get',
            url: this.projectsUrl}
        ).then(res => {
            // sets the returned data to this.state.data

            this.setState({ data: res.data });
        })
            .catch(err => {
                console.error(err);
            });
    }
    /**
     * f(n) is called when Complonent is loaded , calls loadProjectsFromServer()
     * to load projectsList
     * @param {}
     * @return {Status}
     */
    componentDidMount() {
        this.loadProjectsFromServer();
    }
    /**
     * f(n) is called when Run Plagiarism button is clicked
     * Sends the Project Objects in a POST Request
     * to load projectsList
     * @param {Event}
     * @return {Status}
     */
    handleUpload(e) {
        // Variables to store the current selected projects
        let id1=this.state.project1,id2=this.state.project2;
        /**
         * Checks and makes sure none of the id is empty
         */

        if (!(id1 == "") && !(id2 == "")){
            if(id1 != id2) {
                // Loads Project1 corresponding to seleceted projectid1
                var p2=this.state.data.find(function( project ) {
                    return project.projectId == id2;
                })
                // Loads Project2 corresponding to seleceted projectid2
                var p1=this.state.data.find(function( project ) {
                    return project.projectId == id1;
                })
                // Post request with Project Objects to Run Plagiarism
                axios({
                    method: 'post',
                    url: this.plagiarismUrl,
                    data: [p1,p2]
                }).then(res => {


                  // Makes the Name of the report with random value
                  res.data.name="Report-"+Math.floor(Math.random()*10)
                                  +"-"+ p1.name+"-"+p2.name+"-"+
                                  Math.floor(Math.random()*10)+21
                  //Sets the Project Ids in Report Object
                    res.data.project1id=p1.projectId
                    res.data.project2id=p2.projectId

                  // Opens the Modal with saving the value
                  this.setState({ activeModal: true ,
                                  report:res.data});
                }).catch(err => {
                    console.error(err);
                });
            } else {
                alert("Source and target projects are same, please select different projects for analysis");
            }
        }
        else{
            // executed when either of the project selected is empty
            alert("Please Select Both Projects")
        }
    }

    /**
     * f(n) is called when any of the project is selected from drowpdown
     * Sets the State of Class's project1 or project2 dependgin upon the id
     * of the event e
     * @param {Event}
     * @return {}
     */
    handleChange(e) {
        ((e.target.id=="project1") ?
            (this.setState({ project1: e.target.value,
                            activeModal:false,
                            report:{} })):
            (this.setState({ project2: e.target.value,
                            activeModal:false,
                            report:{} })));

    }

    /**
     * Renders the Component
     */
    render(){
        /**
         * Variables which Stores the JSX component for Project
         * the Id and value of the component is the projectID and diplayed value is
         * name
         */
        let project1Nodes = this.state.data.map(project => {
            return (
                <option value={project.projectId}
                        key={project.projectId}>{project.name}</option>
            )});

        /**
         * The reutrned JSX Component which is Form which have 2 dropdown to show
         * the list of projects to be selected
         */
        return(
            // Main Div : NOTE: B is for React-BootStrap
            <div style={Styles.DivHeader} >
                {/*  Form Component having 2 dropdowns and RunPlagiarim Button*/}
                <B.Form horizontal>
                    {/* Form Group : 1. Label for Source Project 1
                           2.Select Component for Project 1 */}
                    <B.FormGroup controlId="project1">
                        {/* Label Project 1 */}
                        <B.Col componentClass={B.ControlLabel} sm={3} >
                            Select Source Project
                        </B.Col>
                        {/* Select Component to display all project option for project 1*/}
                        <B.Col sm={9}>
                            <B.FormControl componentClass="select" placeholder="Source Project"
                                           onChange={ this.handleChange.bind(this) } >
                                {/* Option values rendered : NOTE : Defined above*/}
                                <option value={""}>Select Project</option>
                                {project1Nodes}
                            </B.FormControl>
                        </B.Col>
                    </B.FormGroup>

                    {/* Form Group : 1. Label for Source Project 2
                        2.Select Component for Project 2 */}
                    <B.FormGroup controlId="project2">
                        {/* Label Project 2 */}
                        <B.Col componentClass={B.ControlLabel} sm={3}>
                            Select Target Project
                        </B.Col>
                        {/* Select Component to display all project option for project 2*/}
                        <B.Col sm={9}>
                            <B.FormControl componentClass="select" placeholder="Second Project"
                                           onChange={ this.handleChange.bind(this) } >
                                {/* Option values rendered : NOTE : Defined above*/}
                                <option value={""}>Select Project</option>
                                {project1Nodes}
                            </B.FormControl>
                        </B.Col>
                    </B.FormGroup>

                    {/* Button to run PLagiarism */}
                    <B.FormGroup>
                        <B.Col smOffset={3} sm={9}>
                            {/*  Calls handleUpload Mehtod of the class*/}
                            <B.Button  bsStyle="success"  bsSize="large"
                                       onClick={this.handleUpload}>
                                Run Plagiarism
                            </B.Button>

                        </B.Col>
                    </B.FormGroup>
                </B.Form>
          {/*  Component to Show Display it is hidden till report not loaded*/}
                <ReportContent activeModal={this.state.activeModal}
                                report={this.state.report} >
                </ReportContent>
            </div>
        );
    }
}

// Exports the Component
export default Plagiarism;
