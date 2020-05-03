import { Component } from "react";
import Axios from "axios";
import { RouteComponentProps, withRouter } from "react-router-dom";

type ErrorHandlerState = {
    error: string;
    shouldLogout: boolean;
}

class ErrorHandler extends Component<RouteComponentProps, ErrorHandlerState> {
  
    requestInterceptor: any;
    responseInterceptor: any;

    constructor(props: RouteComponentProps) {
        super(props);

        this.state = {error: '', shouldLogout: false}
    }

    componentDidMount() {
        this.requestInterceptor = Axios.interceptors.request.use(req => {
            this.setState({ error: '' });
            return req;
        });
  
        this.responseInterceptor = Axios.interceptors.response.use(
            res => res,
            error => {
                if (error.response && error.response.status === 401) {
                    console.log('Captured error');
                    // this.setState({error: error, shouldLogout: true});
                    //this.props.history.push('/login')
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

    logout() {
        this.setState({error: '', shouldLogout: false});
        console.log('Logged out');
        return 'logged-out';
    }
};

export default withRouter(ErrorHandler);