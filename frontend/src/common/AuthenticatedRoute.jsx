import React from "react";
import UserContext from "../components/authentication/context/UserContext";
import { Route, Redirect } from "react-router-dom";


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

export default AuthenticatedRoute;