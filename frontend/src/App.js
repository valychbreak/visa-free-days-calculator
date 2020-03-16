import React from 'react';
import {BrowserRouter as Router, Link, Switch, Route} from 'react-router-dom'
import VisaFreeDateCalculator from './components/VisaFreeDateCalculator';
import ReactMainPage from './components/ReactMainPage';

function App() {
  return (
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
  );
}

export default App;
