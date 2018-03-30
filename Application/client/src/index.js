/**
 *
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 *
*/
// Importing React's Components and React's and Node Libraries
import { render } from 'react-dom';
import  React, { Component }from 'react';
import { Route, Router, NavLink} from 'react-router-dom';
import {createBrowserHistory} from 'history';
import * as B from 'react-bootstrap';
import App from './Components/App';

// Global Variables; mountNode saves the location in HTML to Mount to
var mountNode = document.getElementById('root')

// Main call which renders class App to MountNode
render(<App/>,mountNode);
