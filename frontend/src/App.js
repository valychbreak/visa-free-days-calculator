import React from 'react';
import {BrowserRouter as Router, Link, Switch, Route} from 'react-router-dom'
import VisaFreeDateCalculator from './components/calculator/VisaFreeDateCalculator';
import ReactMainPage from './components/ReactMainPage';
import { StylesProvider } from '@material-ui/core';

function App() {
  return (
    // Overrides Material UI styles with CSS 
    <StylesProvider injectFirst>
      <Router>
        <div className="main">
          <header className="header">
            <ul className='nav-bar'>
              <li><Link to={'/'} className='nav-link'>Main Page</Link></li>
              <li><Link to={'/calculator'} className='nav-link'>Visa-Free Days Calculator</Link></li>
            </ul>
          </header>

          <Switch>
            <Route exact path='/' component={ReactMainPage} />
            <Route path='/calculator' component={VisaFreeDateCalculator} />
          </Switch>
        </div>
      </Router>
    </StylesProvider>
  );
}

export default App;
