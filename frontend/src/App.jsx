import React from 'react';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import VisaFreeDateCalculator from './components/calculator/VisaFreeDateCalculator';
import ReactMainPage from './components/ReactMainPage';
import { StylesProvider, Container, Box } from '@material-ui/core';
import { SnackbarProvider } from 'notistack';
import Header from './components/Header';
import AuthenticatedRoute from './common/AuthenticatedRoute';
import UserContextProvider from './components/authentication/context/UserContextProvider';
import LoginPage from './components/authentication/LoginPage';

function App() {
  return (
    // Overrides Material UI styles with CSS 
    <StylesProvider injectFirst>
      <SnackbarProvider>
        <Router>
          <Header />
          <Container>
            <Box>
              <UserContextProvider>
                <Switch>
                  <Route exact path='/' component={ReactMainPage} />
                  <Route exact path='/login' component={LoginPage} />
                  <AuthenticatedRoute path='/calculator' component={VisaFreeDateCalculator} />
                </Switch>
              </UserContextProvider>
            </Box>
          </Container>
        </Router>
      </SnackbarProvider>
    </StylesProvider>
  );
}

export default App;
