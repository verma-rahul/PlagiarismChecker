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
/**
 * UploadProject Component is displayed when URL is /uploadproject
 * It Displays 2 text Input fileds
 *  1. Project Name   2. Project URL (as String)
 *  and send the Inputs to Server
 */
class UploadProject extends Component {
    /** Constructor of the class UploadProject
     */
    constructor(props){
        super(props);
        // URl for Get All Project Request
        this.getAllProjectsUrl="http://localhost:8080/api/project/all";
        // URl for POST Project Request
        this.projectUrl="http://localhost:8080/api/project/upload";
        /**
         * The Initial State of the Class Where
         *  1. name: Name of project
         *  2. directory: directory string of project
         */
        this.state=  {directory:"",data: []}
        this.checkProject=this.checkProject.bind(this)
    }

    /**
     * f(n) is called when Upload button is clicked
     * Sends the Project name and directory in a POST Request
     * @param {Event}
     * @return {Status}
     */
    handleUpload(e) {
        // Variables to store the directorya and name strings
        var directory=this.state.directory;
        // Post request with Project name and path string;NOTE: Server is run local
        // so takes the files locally
        if(this.checkProject()){
        axios({
            method: 'post',
            url: this.projectUrl,
            data: {
                directory: directory
            }
        }).then(res => {

          if(res.data=="FORBIDDEN")
            alert("Upload Not Successful , Maybe directory exists or directory invalid")
          else
            alert("Upload Successful")
            // TO  Reset the Input field
            this.setState(  {directory:""})
        })
            .catch(err => {
            alert("Upload Not Successful , Maybe directory exists or directory invalid")
            this.setState(  {directory:""})
            console.error(err);
            });

          }
    }

    /**
     * f(n) to Boolean Telling whether the UploadProject API call
     *      should be made or not
     * @param {}
     * @return {Boolean} Boolean Telling whether the UploadProject API call
     *                  should be made or not
     */

    checkProject(){
      var names = this.state.data.map(project => project.name);
      var name=this.state.directory.split(/[\\\/]/).pop();
      if(name.length<1){
        alert("Invalid Directory, cannot be empty, should be a directory")
        this.setState(  {directory:""})
        return false
      }
      if(names.includes(name))
      {
        alert("There exists a Project with same name, Please change the name"+
              " of the folder you are trying to upload and try Again")
      this.setState(  {directory:""})
      return false
      }
      return true
    }

    /**
     * f(n) is called when values in input field for name and directory changes
     * updates the name and directory of the Class's state depending upon
     * event's target.id
     * @param {Event}
     * @return {}
     */
    handleChange(e) {
        this.setState({ directory: e.target.value });
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
                    console.error(err);
                });
        }

    /**
     * Renders the Component
     */
    render(){
// Main Div : NOTE: B is for React-BootStrap
        return(
            <div style={Styles.DivHeader} >
                {/*  Form Component having 2 inputs and thier labels*/}
                <B.Form horizontal>

                    {/* Form Group : 1. Label for Source name
                         2. Input Component for name*/}

                    {/* Form Group : 1. Label for Source directory
                      2. Input Component for directory*/}
                      <B.FormGroup controlId="instructions">
                          <B.Col componentClass={B.ControlLabel} sm={2}>
                          </B.Col>
                          <B.Col sm={10}>
                            <B.FormControl.Static>
                              Please Paste the Folder Directory.
                                (Note : folder should have only .java files)
                                </B.FormControl.Static>
                          </B.Col>
                      </B.FormGroup>

                    <B.FormGroup controlId="directory">

                        <B.Col componentClass={B.ControlLabel} sm={2}>
                            Directory
                        </B.Col>
                        <B.Col sm={10}>

                            <B.FormControl type="text" placeholder="Directory 1"
                                           onChange={ this.handleChange.bind(this) }
                                           value={this.state.directory}/>
                        </B.Col>
                    </B.FormGroup>
                    <B.FormGroup>

                        {/* Button to upload Project  */}
                        <B.Col smOffset={2} sm={10}>
                            {/*  Calls handleUpload Mehtod of the class*/}
                            <B.Button  bsStyle="primary" onClick={this.handleUpload.bind(this)}>
                                Upload
                            </B.Button>
                        </B.Col>

                    </B.FormGroup>
                </B.Form>
            </div>
        );
    }
}

// Exports the Component
export default UploadProject;
