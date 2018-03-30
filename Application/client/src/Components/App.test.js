
/**
 *
 * @version 0.0.1
 * @author [Rahul Verma](https://github.com/Verma92)
 *
*/
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { shallow } from 'enzyme';

// Renders and checks it renders without crashing
it('renders without crashing', () => {
  //Mounts on a dummy Node
  const mountNode = document.createElement('div');
  ReactDOM.render(<App/>,mountNode);
  const wrapper = shallow(<App />);
  expect(wrapper.is('Router')).toBe(true);
});
