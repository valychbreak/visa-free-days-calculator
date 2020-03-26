import React from 'react';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import VisaFreeDateCalculator from './components/calculator/VisaFreeDateCalculator';
import ReactMainPage from './components/ReactMainPage';
import { StylesProvider, Container, Box } from '@material-ui/core';
import { SnackbarProvider } from 'notistack';
import Header from './components/Header';

function App() {
  return (
    // Overrides Material UI styles with CSS 
    <StylesProvider injectFirst>
      <SnackbarProvider>
        <Router>
            <Header />
            <Container>
              <Box>
                <Switch>
                  <Route exact path='/' component={ReactMainPage} />
                  <Route path='/calculator' component={VisaFreeDateCalculator} />
                </Switch>
              </Box>
            </Container>
            
        </Router>
      </SnackbarProvider>
    </StylesProvider>
  );
}

export default App;
