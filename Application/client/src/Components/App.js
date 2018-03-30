/**
 *
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 *
*/
// Importing React's Components and React's and Node Libraries
import { render } from 'react-dom';
import  React, { Component } from 'react';
import { Route, Router, NavLink} from 'react-router-dom';
import {createBrowserHistory} from 'history';
import Home from './home';
import UploadProject from './uploadproject';
import ViewProjects from './viewprojects';
import ViewReports from './viewreports';
import Plagiarism from './plagiarism';
import * as B from 'react-bootstrap';

// Constatnt Stores history of the Browser
const history = createBrowserHistory()

/**
 * App is the main Component which is displayed not matter what the URL is
 * It contains the Navbar and Routing
*/
class App extends React.Component{

//constructor to load the initial state of the navbar
constructor(props){
  super(props);

// activeKey points to Home or respective tab in inital load of webpage
  var activeKey=(history.location.pathname).replace('/', '')
  activeKey=(activeKey.length>0? activeKey: "home")
  this.state={activeKey: activeKey}

// binds the function handleSelect so that if user selects
// a tab, the activeKey is to right tab which is highlighted
  this.handleSelect=this.handleSelect.bind(this)
}

handleSelect(selectedKey) {
  // set the state of activeKey to the right tab, its highlighted in navbar
  this.setState({activeKey: selectedKey});
// pushes the right tabname in URl and which helps navigates to it
  history.push(selectedKey)
}
// This Methods renders React's JSX Code into Virtual DOM which renders into DOM
  render(){
  return(
    // Router Object helps in client side navigation all paths append to
    // the home URL, { history} saves the current history of URL
    <Router history={ history}>
      <div className="container ">
        {/*B.Nav is React-Bootsrap's navbar; activeKey stores which NavItem to
        make active */}
        <B.Nav bsStyle="tabs" justified
          // onSelect tells to call handleSelect which in turns changes
          // this.state.activeKey and navigates to right tab
          activeKey={this.state.activeKey} onSelect={this.handleSelect}>
              <B.NavItem eventKey={"home"} >Home</B.NavItem>
              <B.NavItem eventKey={"plagiarism"} >Run Plagiarism  </B.NavItem>
              <B.NavItem eventKey={"uploadproject"} >Upload Project</B.NavItem>
              <B.NavItem eventKey={"viewprojects"} >View Projects</B.NavItem>
              <B.NavItem eventKey={"viewreports"} >View Reports</B.NavItem>
        </B.Nav>
{/* Routes are child components which stores the component in regard to the
  URL's Path*/}
      <Route exact path="/" component={Home}/>
      <Route path="/uploadproject" component={UploadProject}/>
      <Route path="/viewprojects" component={ViewProjects}/>
      <Route path="/home" component={Home}/>
      <Route path="/viewreports" component={ViewReports}/>
      <Route path="/plagiarism" component={Plagiarism}/>

    </div>
  </Router>
  );
  }
}


export default App;
