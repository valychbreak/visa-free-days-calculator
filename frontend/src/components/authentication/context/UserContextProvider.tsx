import React, { Component } from "react";
import {UserContextProviderComponent} from "./UserContext";
import User from "../../../common/User";
import AccessToken from "../../../common/AccessToken";

type ProviderState = {
    user?: User;
    accessToken?: string;
}

const DEFAULT_STATE: ProviderState = {user: undefined, accessToken: undefined};

class UserContextProvider extends Component<{}, ProviderState> {

    constructor(props: any) {
        super(props);

        this.state = DEFAULT_STATE;
    }

    render() {
        return (
            <UserContextProviderComponent value={{
                isAuthenticated: () => this.isAuthenticated(),
                getUser: () => this.getUser(),
                setUser: (user: User) => this.setUser(user),
                authorizeWithTemporaryUser: () => this.authorizeWithTemporaryUser(),
                logout: () => this.logout()
            }}>
                {this.props.children}
            </UserContextProviderComponent>
        )
    }

    isAuthenticated(): boolean {
        return !!this.state.accessToken;
    }

    getUser(): User | undefined {
        return this.state.user;
    }

    setUser(user: User) {
        this.setState({user});
    }

    authorizeWithTemporaryUser(): AccessToken {
        const user = new User("temp_user");
        const accessToken: AccessToken = new AccessToken("temp", "temp", "temp", 36000);
        this.setState({user: user, accessToken: accessToken.accessToken});
        return accessToken;
    }

    logout() {
        this.setState(DEFAULT_STATE);
    }
}

export default UserContextProvider;