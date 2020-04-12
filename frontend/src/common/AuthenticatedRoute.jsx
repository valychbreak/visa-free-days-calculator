import React from "react";
import UserContext from "../components/authentication/context/UserContext";
import { Route, Redirect } from "react-router-dom";
import Axios from "axios";


const AuthenticatedRoute = ({ component: Component, ...rest }) => (
    <UserContext.Consumer>
        {context => (
            <Route {...rest} render={(props) => (
                context.isAuthenticated() === true ? 
                   <Component {...props} /> : <Redirect to={{ pathname: '/login', state: { from: props.location }}} />   
             )} />
        )}
    </UserContext.Consumer>
);

const isProd = process.env.NODE_ENV === 'production';

if (isProd) {
    console.log('Loading production config...');
} else {
    console.log('Loading dev config...');
    Axios.defaults.baseURL = "http://localhost:8080";
}

export default AuthenticatedRoute;