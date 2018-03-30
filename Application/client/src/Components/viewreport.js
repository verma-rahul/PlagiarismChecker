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
 * ViewReport Component is displayed when URL is /viewreports
 * and view have selected to view a Report
 */
class ViewReport extends Component {
  /** Constructor f(n) which recieves the props from parent
   * @param {Props}
   * @return {}
   */
    constructor(props){
        super(props);
        this.state=  {showModal: false,
                      report:{}}
        this.close=this.close.bind(this)
    }
    /** f(n) to change state of showModal, so that the modal is closed
     * @param {}
     * @return {}
     */
    close() {
       this.setState({ showModal: false ,
                     report:{}
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
        this.setState({showModal: newProps.activeModal,
                      report:newProps.report});
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
            {/*Buttons to close the view */}
         <B.Modal.Footer>
                    <B.FormGroup>
                        <B.Button onClick={this.close}>Close
                        </B.Button>
                    </B.FormGroup>
                </B.Modal.Footer>
            </B.Modal>
        );
    }
}

// Exports the Component
export default ViewReport;
