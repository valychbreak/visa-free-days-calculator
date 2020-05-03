import React, { Component } from "react";
import { Button, Paper, Typography, withStyles, WithStyles } from "@material-ui/core";
import { UserContext, UserContextConsumer } from "./context/UserContext";
import { RouteComponentProps, withRouter } from "react-router-dom";
import { withSnackbar, WithSnackbarProps } from "notistack";
import { authenticationManager } from "./Authentication";

const useStyles = () => ({
    paper: {
        padding: '1em'
    }
});

class LoginPage extends Component<RouteComponentProps & WithStyles & WithSnackbarProps> {
    render() {
        const { classes } = this.props;
        return (
            <UserContextConsumer>
                {context => (
                    <Paper className={classes.paper}>
                        <Typography variant="h4">You are not authorized.</Typography>
                        <Typography>Click button below to try with temporary user.</Typography>
                        <Button color='primary' variant="contained" 
                                onClick={e => this.authorizeWithTempUser(context)}
                        >
                            Try now!
                        </Button>
                    </Paper>
                )}
            </UserContextConsumer>
        )
    }

    private authorizeWithTempUser(context: UserContext) {
        context.authorizeWithTemporaryUser()
            .then(accessToken => {
                this.props.history.push('/profile');
            }).catch(error => {
                this.props.enqueueSnackbar('Failed to login as temporary user. ' + error, {variant: 'error'});
            });
    }
}

export default withRouter(withSnackbar(withStyles(useStyles)(LoginPage)));