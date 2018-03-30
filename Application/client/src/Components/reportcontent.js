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
// import Parser from 'html-react-parser';

/**
 * ReportContent Component is displayed when URL is /plagiarism
 * and view have selected to view a Report
 */
class ReportContent extends Component {

  /** Constructor f(n) which recieves the props from parent
   * @param {Props}
   * @return {}
   */
    constructor(props){

        super(props);
        // URl for save Request
        this.reportUrl="http://localhost:8080/api/report/upload";

        this.state=  {showModal: false,
                      report:{}}
        this.close=this.close.bind(this)
        this.saveReport=this.saveReport.bind(this)
    }

    /** f(n) to change state of showModal, so that the modal is closed
     * @param {}
     * @return {}
     */
    close() {
       this.setState({ showModal: false });
     }

     /** f(n) to save the Report we are currently viewing;
      * @param {}
      * @return {Status}
      */
     saveReport(){
       // upload request body in the right format
       var dataBody={
            name: this.state.report.name,
            resultsummary: this.state.report.resultSummary,
            project1id: this.state.report.project1id,
            project2id: this.state.report.project2id,
            similarityscore: this.state.report.similarityScore
          }
//  Upload Method to save report to server
       axios({
           method: 'post',
           url: this.reportUrl,
           data: dataBody
         }
       ).then(res => {
           // saves the report
           alert("Upload Success!")
           this.setState({ showModal: false });
       })
           .catch(err => {
                alert("Upload  Not Success!")
               console.error(err);
           });

    }

    /** f(n) to change state of showModal to true when Modal is opened
     * @param {}
     * @return {}
     */
    open() {
        this.setState({ showModal: true });
    }

    /** f(n) which keeps on checking the state change of the props;
     * @param {{showModal: Boolean,report:report}}
     * @return {}
     */
    componentWillReceiveProps(newProps) {
        this.setState({showModal: newProps.activeModal,report:newProps.report});
    }

    /** f(n) to Render the JSX Component
     */
    render(){
      // Saves summary in new lines
      var summary="";
      if(this.state.report.resultSummary!=undefined)
      summary=this.state.report.resultSummary
                  .split("\n")
                  .map((line, index) => {return <p key={index}>{line}</p>; })

// Main Div : NOTE: B is for React-BootStrap
        return(
          // Modal is visible only when 'show' is true
          <B.Modal show={this.state.showModal} onHide={this.close}>
            {/* Header Showing the Report Name  */}
            <B.Modal.Header closeButton>
            <B.Modal.Title>{this.state.report.name}</B.Modal.Title>
          </B.Modal.Header>
          {/* Content of Report shown in body */}
         <B.Modal.Body>
           {summary}
         </B.Modal.Body>
             {/* Buttons to save the report or close */}
         <B.Modal.Footer>

                    <B.FormGroup>
                        <B.Button onClick={this.close}
                                  bsStyle="danger" >close
                        </B.Button>
                        <B.Button onClick={this.saveReport}
                                  bsStyle="success" >save
                        </B.Button>
                    </B.FormGroup>
                </B.Modal.Footer>
            </B.Modal>
        );
    }
}

// Exports the Component
export default ReportContent;
