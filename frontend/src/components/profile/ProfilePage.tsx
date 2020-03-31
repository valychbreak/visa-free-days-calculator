import React, { Component } from "react";
import { Paper, Button, Typography, WithStyles, withStyles } from "@material-ui/core";
import { UserContextConsumer, UserContext } from "../authentication/context/UserContext";

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
                        <Typography>You are logged in as <b>{context.getUser()?.username}</b></Typography>
                        <Button variant="contained" onClick={e => this.logout(context)}>Logout</Button>
                    </Paper>
                )}
            </UserContextConsumer>
        )
    }

    private logout(context: UserContext) {
        context.logout();
    }
}

export default withStyles(useStyles)(ProfilePage);