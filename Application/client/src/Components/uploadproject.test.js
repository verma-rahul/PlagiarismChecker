/**
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
*/
import React from 'react';
import ReactDOM from 'react-dom';
import UploadProject from './uploadproject';
import { shallow } from 'enzyme';
// Renders and checks it renders without crashing
it('renders without crashing', () => {
//Mounts on a dummy Node
  const mountNode = document.createElement('div');
  ReactDOM.render(<UploadProject/>,mountNode);
});

it('tr renders without crashing', () => {
// Checks if div is rendered or not
  const wrapper = shallow(<UploadProject />);
  expect(wrapper.find('div').length).toBe(1);
});
