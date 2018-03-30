/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
*/
import { render } from 'react-dom';
import  React, { Component }from 'react';
import { JumboHome } from '../Styles/style';
import * as B from 'react-bootstrap';

/**
 * Home Component which it displayed when URl is '/' and '/home'
*/
class Home extends Component {
  /** Constructor of the class Plagiarism
   */
  constructor(props){
      super(props);
      this.helpUrl="https://drive.google.com/a/husky.neu.edu/file/d/1XebjG1WWIN6-YQUrYy_dkjMXazzxlOUv/view?usp=sharing";
  }
  render(){
  return(
    /** Renders A Jumbotron to Display the content of home page,Style
    *  is imported from STyle file
    */
    <B.Jumbotron style={JumboHome}>
       <h1>Welcome to Plagiarism Detection</h1>
       <p>Here Plagiarism can be checked across against 2 Multi File Java
         Projects...</p>
       <B.FormGroup>
           <B.Col smOffset={0} sm={12}>
               {/*  Calls Help */}
               <B.Button  bsStyle="info"  bsSize="large"
                 href={this.helpUrl}
                  target="_blank">
                   Help
               </B.Button>
           </B.Col>
       </B.FormGroup>
      </B.Jumbotron>
  );
  }
}

/**
 * Exports the Component Home
*/
export default Home;
