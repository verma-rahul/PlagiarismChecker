
/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 */
// Importing React's Components and React's and Node Libraries
import  React, { Component } from 'react';
import * as B from 'react-bootstrap';
import axios from 'axios';

/**
 * ViewProjects Component is displayed when URL is /viewprojects
 *  It Displays Table of Projects name and Actions
 *  Action can be Run Plagiarism and Delete
 *  Delete send the Project to be deleted to Server
 */
class ViewProjects extends Component {
    /** Constructor of the class UploadProject
     */

    constructor(props){
        super(props);
        // URl for Get All Project Request
        this.getAllProjectsUrl="http://localhost:8080/api/project/all";
        // URl for Delete a Project Request
        this.deleteProjectUrl="http://localhost:8080/api/project/delete";
        /**
         * The Initial State of the Class Where
         *  1. data: projectList
         */
        this.state={data: []};
    }

    /** f(n) to Load All Projects from Server and Saves the result in Class's
     * state.data ;
     * @param {}
     * @return {Status}
     */
    loadProjectsFromServer() {
        axios({
            method: 'get',
            url: this.getAllProjectsUrl}
        ).then(res => {
            // sets the returned data to this.state.data
            this.setState({ data: res.data });
        })
            .catch(err => {
              alert("Projects Load Not Successful")
                console.error(err);
            });
    }

    /** f(n) to delete the given Project in Server and return the remaining list
     * and save that in  the result in Class's state.data ;
     * @param {Project}
     * @return {Status}
     */
    handleDelete(project) {
        // Post request with Projectid in with URL and Project as Body
        const confirmDelete = confirm("Delete Project: " + project.name + "?");
        if(confirmDelete)
            axios({
                    method: 'delete',
                    url: this.deleteProjectUrl,
                    data: project
                }
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
     * Renders the Component
     */
    render(){
        /**
         * Variables which Stores the JSX row component for Project name
         * and associated actions
         * the Id and value of the component is the projectID and diplayed value is
         * name
         */
        let rowNodes = this.state.data.map(project => {
            return (
                <tr key={project.projectId}>
                    <td>
                        {project.name}

                    </td>
                    <td className="text-nowrap">
                        <B.ButtonToolbar>
                            {/*  Button to remove the given project*/}
                            <B.Button  bsStyle="danger"
                                       onClick={ this.handleDelete.bind(this, project) }>
                           <span className="glyphicon glyphicon-remove">
                           </span></B.Button>
                        </B.ButtonToolbar>
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
                            <th><span className="caret"></span> Project Name</th>
                            <th><span className="caret"></span> Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {/* Defined Above */}
                        { rowNodes }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}
// Exports the Component
export default ViewProjects;
