import React, { Component } from "react";
import { Paper, Button, Typography, WithStyles, withStyles } from "@material-ui/core";

const useStyles = () => ({
    paper: {
        padding: '1em'
    }
})

class ProfilePage extends Component<WithStyles> {
    render() {
        const {classes} = this.props;
        return (
            <Paper className={classes.paper}>
                <Typography>You are logged in as *TODO*</Typography>
                <Button variant="contained">Logout</Button>
            </Paper>
        )
    }
}

export default withStyles(useStyles)(ProfilePage);