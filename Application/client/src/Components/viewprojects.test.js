/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
*/
import React from 'react';
import ReactDOM from 'react-dom';
import ViewProjects from './viewprojects';
import * as B from 'react-bootstrap';
import { shallow, mount, render } from 'enzyme';


// Renders and checks it renders without crashing
it('renders without crashing', () => {
//Mounts on a dummy Node
  const mountNode = document.createElement('div');
  ReactDOM.render(<ViewProjects/>,mountNode);
});

it('tr renders without crashing', () => {
// Checks if tr is rendered or not
  const wrapper = shallow(<ViewProjects />);
  expect(wrapper.find('tr').length).toBe(1);
});

it('renders spans without crashing', () => {
// Checks span tr is rendered or not
  const wrapper = shallow(<ViewProjects />);
  expect(wrapper.find('span').length).toBe(2);
});
