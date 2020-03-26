import React, { Component } from 'react';
import {Link} from 'react-router-dom'
import { AppBar, Toolbar, Drawer, List, ListItem, ListItemText, ListItemIcon, IconButton, Typography, Divider } from '@material-ui/core';
import { Home, Flight, Close } from '@material-ui/icons';
import MenuIcon from '@material-ui/icons/Menu'
import './Header.css'

type HeaderState = {
    drawerOpen: boolean
}

class Header extends Component<{}, HeaderState> {
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
                <AppBar position="fixed">
                    <Toolbar>
                        <IconButton onClick={() => this.toggleDrawer()}>
                            <MenuIcon fontSize="large" />
                        </IconButton>
                        <Typography variant="h5" color="inherit">Visa calculator</Typography>
                    </Toolbar>
                </AppBar>
                <Toolbar />
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
            </div>
        )
    }
}

export default Header;