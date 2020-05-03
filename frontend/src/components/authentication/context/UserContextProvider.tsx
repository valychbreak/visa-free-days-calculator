import React, { Component } from "react";
import {UserContextProviderComponent} from "./UserContext";
import User from "../../../common/User";
import AccessToken from "../../../common/AccessToken";
import Axios from "axios";
import Api from "../../../common/Api";
import { AuthenticationAwareProp, authenticationManager, withAuth } from "../Authentication";

type ProviderState = {
    user?: User;
}

const DEFAULT_STATE: ProviderState = {user: undefined};

class UserContextProvider extends Component<AuthenticationAwareProp, ProviderState> {

    constructor(props: any) {
        super(props);
        this.state = DEFAULT_STATE;
    }

    componentDidMount() {
        if (this.props.isLoggedIn) {
            this.fetchUserOrLogout();
        }
    }

    private fetchUserOrLogout(): Promise<User> {
        return Api.fetchUserInfo().then(user => {
            this.setUser(user);
            return user;
        }).catch(error => {
            const status = error.response?.status;
            if (status !== null && status === 401) {
                console.log('Removing cached token because it was expired or not valid anymore')
                this.logout();
            }
            return error;
        });
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

    private isAuthenticated(): boolean {
        return this.props.isLoggedIn;
    }

    private getUser(): User | undefined {
        return this.state.user;
    }

    private setUser(user: User) {
        this.setState({user});
    }

    private async authorizeWithTemporaryUser(): Promise<AccessToken> {
        const response = await Axios.get('/api/user/temporary');
        const accessToken = this.toAccessToken(response.data);

        authenticationManager.login(accessToken);
        return this.fetchUserOrLogout().then(_ => accessToken);
    }

    private toAccessToken(jsonObject: any): AccessToken {
        const token = jsonObject.access_token;
        const refreshToken = jsonObject.refresh_token;
        const tokenType = jsonObject.token_type;
        const expiresIn = jsonObject.expires_in;

        if (!token || !refreshToken || !tokenType || !expiresIn) {
            throw new Error('Failed to parse token response');
        }

        return new AccessToken(token, refreshToken, tokenType, expiresIn);
    }

    private logout() {
        this.setState(DEFAULT_STATE);
        authenticationManager.logout();
    }
}

export default withAuth(UserContextProvider);