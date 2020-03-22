import { Component } from "react";
import Axios from "axios";

type ErrorHandlerState = {
    error: string
}

class ErrorHandler extends Component<any, ErrorHandlerState> {
  
    requestInterceptor: any;
    responseInterceptor: any;

    componentDidMount() {
        // Set axios interceptors
        this.requestInterceptor = Axios.interceptors.request.use(req => {
            this.setState({ error: '' });
            return req;
        });
  
        this.responseInterceptor = Axios.interceptors.response.use(
            res => res,
            error => {
                alert('Error happened');
                this.setState({ error });
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

export default ErrorHandler;