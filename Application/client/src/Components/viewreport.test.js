/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
*/
import React from 'react';
import ReactDOM from 'react-dom';
import ViewReport from './viewreport';
import { shallow } from 'enzyme';
import * as B from 'react-bootstrap';
// Renders and checks it renders without crashing
it('renders without crashing', () => {
//Mounts on a dummy Node
  const mountNode = document.createElement('div');
  ReactDOM.render(<ViewReport/>,mountNode);
});

it('tr renders without crashing', () => {
// Checks if div is rendered or not
  const wrapper = shallow(<ViewReport />);
  expect(wrapper.find('p').length).toBe(1);
});
