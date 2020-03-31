import React, { Component } from 'react';
import {Link, withRouter, RouteComponentProps } from 'react-router-dom'
import { AppBar, Toolbar, Drawer, List, ListItem, ListItemText, ListItemIcon, IconButton, Typography, Divider, withStyles, WithStyles, Button } from '@material-ui/core';
import { Home, Flight, Close, AccountCircle, ExitToApp } from '@material-ui/icons';
import MenuIcon from '@material-ui/icons/Menu'
import './Header.css'
import UserContext from './authentication/context/UserContext';

const useStyles = () => ({
    title: {
        flexGrow: 1
    }
});

interface HeaderState {
    drawerOpen: boolean
}

class Header extends Component<WithStyles & RouteComponentProps, HeaderState> {
    constructor(props: any) {
        super(props);

        this.state = {drawerOpen: false}
    }

    toggleDrawer(isOpen?: boolean): void {
        if (isOpen === undefined) {
            this.setState({drawerOpen: !this.state.drawerOpen})
        } else {
            this.setState({drawerOpen: isOpen})
        }
    }

    render() {
        return (
            <div>
                {this.renderApplicationBar()}
                {/* In order to take "physical" space on DOM */}
                <Toolbar />
                {this.renderDrawer()}
            </div>
        )
    }

    renderApplicationBar() {
        const { classes } = this.props;
        return (
            <AppBar position="fixed">
                <Toolbar>
                    <IconButton onClick={() => this.toggleDrawer()}>
                        <MenuIcon fontSize="large" />
                    </IconButton>
                    <Typography className={classes.title} variant="h5" color="inherit">Visa calculator</Typography>
                    <UserContext.Consumer>
                        {context => (
                            <div>
                                {context.isAuthenticated() ? this.profileButton() : this.loginButton()}
                            </div>
                        )}
                    </UserContext.Consumer>
                    
                </Toolbar>
            </AppBar>
        )
    }

    profileButton() {
        return (
            <Button color="inherit" size="large" startIcon={<AccountCircle />} onClick={e => this.props.history.push('/profile')}>
                Profile
            </Button>
        )
    }

    loginButton() {
        return (
            <Button color="inherit" size="large" startIcon={<ExitToApp />} onClick={e => this.props.history.push('/login')}>
                Login
            </Button>
        )
    }

    renderDrawer() {
        return (
            <Drawer anchor="left" variant="temporary" open={this.state.drawerOpen} onClose={()=>this.toggleDrawer(false)}>
                <List className="sidebar" >
                    <ListItem>
                        <IconButton onClick={() => this.toggleDrawer(false)}><Close fontSize="large" /></IconButton>
                        <ListItemText><Typography variant="h6">Menu</Typography></ListItemText>
                    </ListItem>
                    <Divider />
                    <ListItem button component={Link} to="/" onClick={() => this.toggleDrawer(false)}>
                        <ListItemIcon><Home /></ListItemIcon>
                        <ListItemText>Home</ListItemText>
                    </ListItem>
                    <ListItem button component={Link} to="/calculator" onClick={() => this.toggleDrawer(false)}>
                        <ListItemIcon><Flight /></ListItemIcon>
                        <ListItemText>Calculator</ListItemText>
                    </ListItem>
                </List>
            </Drawer>
        )
    }
}

export default withStyles(useStyles)(withRouter(Header));