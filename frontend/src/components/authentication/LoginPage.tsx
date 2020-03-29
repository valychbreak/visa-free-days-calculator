import React, { Component } from "react";
import { Button, Paper, Typography, withStyles, WithStyles } from "@material-ui/core";
import UserContext from "./context/UserContext";

const useStyles = () => ({
    paper: {
        padding: '1em'
    }
});

class LoginPage extends Component<WithStyles> {
    render() {
        const { classes } = this.props;
        return (
            <UserContext.Consumer>
                {context => (
                    <Paper className={classes.paper}>
                        <Typography variant="h4">You are not authorized.</Typography>
                        <Typography>Click button below to try with temporary user.</Typography>
                        <Button color='primary' variant="contained" 
                                onClick={e => context.authorizeWithTemporaryUser()}
                        >
                            Try now!
                        </Button>
                    </Paper>
                )}
            </UserContext.Consumer>
        )
    }
}

export default withStyles(useStyles)(LoginPage);