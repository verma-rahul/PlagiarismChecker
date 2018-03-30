/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
*/
import * as B from 'react-bootstrap';
import { shallow, mount, render } from 'enzyme';
import React from 'react';
import ReactDOM from 'react-dom';
import ViewReports from './viewreports';
// Renders and checks it renders without crashing
it('renders without crashing', () => {
//Mounts on a dummy Node
  const mountNode = document.createElement('div');
  ReactDOM.render(<ViewReports/>,mountNode);
});
it('tr renders without crashing', () => {
// Checks if tr is rendered or not
  const wrapper = shallow(<ViewReports />);
  expect(wrapper.find('tr').length).toBe(1);
});
it('tr renders without crashing', () => {
// Checks if span is rendered or not
  const wrapper = shallow(<ViewReports />);
  expect(wrapper.find('span').length).toBe(3);
});
