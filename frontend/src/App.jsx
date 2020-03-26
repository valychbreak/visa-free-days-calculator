import React from 'react';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import VisaFreeDateCalculator from './components/calculator/VisaFreeDateCalculator';
import ReactMainPage from './components/ReactMainPage';
import { StylesProvider } from '@material-ui/core';
import { SnackbarProvider } from 'notistack';
import Header from './components/Header';

function App() {
  return (
    // Overrides Material UI styles with CSS 
    <StylesProvider injectFirst>
      <SnackbarProvider>
        <Router>
          <div className="main">
            <Header />

            <Switch>
              <Route exact path='/' component={ReactMainPage} />
              <Route path='/calculator' component={VisaFreeDateCalculator} />
            </Switch>
          </div>
        </Router>
      </SnackbarProvider>
    </StylesProvider>
  );
}

export default App;
