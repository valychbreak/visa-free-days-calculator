import { Component } from "react";
import Axios from "axios";
import { RouteComponentProps, withRouter, Redirect } from "react-router-dom";
import UserContext from "./authentication/context/UserContext";
import React from "react";

type ErrorHandlerState = {
    error: string;
}

class ErrorHandler extends Component<RouteComponentProps, ErrorHandlerState> {
  
    requestInterceptor: any;
    responseInterceptor: any;

    componentDidMount() {
        this.requestInterceptor = Axios.interceptors.request.use(req => {
            this.setState({ error: '' });
            return req;
        });
  
        this.responseInterceptor = Axios.interceptors.response.use(
            res => res,
            error => {
                if (error.response && error.response.status === 401) {
                    this.props.history.push('/login')
                }
                throw error;
            }
        );
    }
  
    componentWillUnmount() {
        // Remove handlers, so Garbage Collector will get rid of if WrappedComponent will be removed 
        Axios.interceptors.request.eject(this.requestInterceptor);
        Axios.interceptors.response.eject(this.responseInterceptor);
    }
  
    render() {
        return this.props.children;
    }
};

export default withRouter(ErrorHandler);