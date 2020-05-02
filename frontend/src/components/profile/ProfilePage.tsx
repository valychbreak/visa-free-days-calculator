import React, { Component } from "react";
import { Paper, Button, Typography, WithStyles, withStyles } from "@material-ui/core";
import { UserContextConsumer, UserContext } from "../authentication/context/UserContext";
import Alert from '@material-ui/lab/Alert';
import { authenticationManager } from "../authentication/Authentication";

const useStyles = () => ({
    paper: {
        padding: '1em'
    }
})

class ProfilePage extends Component<WithStyles> {
    render() {
        const {classes} = this.props;
        return (
            <UserContextConsumer>
                {context => (
                    <Paper className={classes.paper}>
                        <Typography>You are logged in as <b>{this.getUsername(context)}</b></Typography>
                        {context.getUser()?.temporary && 
                            <Alert severity="warning">If you logout, you will not be able to recover this user's data.</Alert>
                        }
                        <Button variant="contained" onClick={e => this.logout(context)}>Logout</Button>
                    </Paper>
                )}
            </UserContextConsumer>
        )
    }

    private getUsername(context: UserContext): string {
        const user = context.getUser();
        if (user === undefined) {
            return '<failed to retreive>'
        }
        else if (user.temporary) {
            return user.username + ' (temporary user)';
        }

        return user.username;
    }

    private logout(context: UserContext) {
        context.logout();
        authenticationManager.logout();
    }
}

export default withStyles(useStyles)(ProfilePage);