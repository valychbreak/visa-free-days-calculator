import React, { Component } from "react";
import UserContext from "./UserContext";
import User from "../../../common/User";
import AccessToken from "../../../common/AccessToken";

type ProviderState = {
    user?: User;
    accessToken?: string;
}

class UserContextProvider extends Component<{}, ProviderState> {
    
    constructor(props: any) {
        super(props);

        this.state = {user: undefined, accessToken: undefined};
    }

    render() {
        return (
            <UserContext.Provider value={{
                isAuthenticated: () => this.isAuthenticated(),
                getUser: () => this.getUser(),
                setUser: (user: User) => this.setUser(user),
                authorizeWithTemporaryUser: () => this.authorizeWithTemporaryUser()
            }}>
                {this.props.children}
            </UserContext.Provider>
        )
    }

    isAuthenticated(): boolean {
        return !!this.state.accessToken;
    }

    getUser(): User | null {
        return null;
    }

    setUser(user: User) {
        this.setState({user});
    }

    authorizeWithTemporaryUser(): AccessToken {
        const accessToken: AccessToken = new AccessToken("temp", "temp", "temp", 36000);
        this.setState({accessToken: accessToken.accessToken})
        return accessToken;
    }
}

export default UserContextProvider;