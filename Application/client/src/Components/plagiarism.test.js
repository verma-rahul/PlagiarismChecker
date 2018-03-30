/**
 *
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 *
*/
import React from 'react';
import ReactDOM from 'react-dom';
import Plagiarism from './plagiarism';
import { shallow, mount, render } from 'enzyme';

// Renders and checks it renders without crashing
it('renders without crashing', () => {
  const mountNode = document.createElement('div');
  ReactDOM.render(<Plagiarism/>,mountNode);
});
it('tr renders without crashing', () => {
// Checks if div is rendered or not
  const wrapper = shallow(<Plagiarism />);
  expect(wrapper.find('div').length).toBe(1);
});
