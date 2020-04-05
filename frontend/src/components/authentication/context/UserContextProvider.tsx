import React, { Component } from "react";
import {UserContextProviderComponent} from "./UserContext";
import User from "../../../common/User";
import AccessToken from "../../../common/AccessToken";
import Axios from "axios";
import Api from "../../../common/Api";

type ProviderState = {
    user?: User;
    accessToken?: string;
}

const DEFAULT_STATE: ProviderState = {user: undefined, accessToken: undefined};

class UserContextProvider extends Component<{}, ProviderState> {

    private static ACCESS_TOKEN_KEY = "authToken";

    constructor(props: any) {
        super(props);

        const tokenJson = localStorage.getItem(UserContextProvider.ACCESS_TOKEN_KEY);
        if (tokenJson !== null) {
            console.log('Using cached token');

            const existingToken: AccessToken = JSON.parse(tokenJson);
            this.applyAccessToken(existingToken.accessToken);

            this.state = { accessToken: existingToken.accessToken };
        } else {
            this.state = DEFAULT_STATE;
        }
    }

    private applyAccessToken(accessToken: string) {
        Axios.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken;
    }

    componentDidMount() {
        const existingToken = this.state.accessToken;
        if (existingToken !== undefined) {
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
        return !!this.state.accessToken;
    }

    private getUser(): User | undefined {
        return this.state.user;
    }

    private setUser(user: User) {
        this.setState({user});
    }

    private async authorizeWithTemporaryUser(): Promise<AccessToken> {
        const response = await Axios.get('http://localhost:8080/api/user/temporary');
        const accessToken = this.toAccessToken(response.data);

        localStorage.setItem(UserContextProvider.ACCESS_TOKEN_KEY, JSON.stringify(accessToken));
        this.applyAccessToken(accessToken.accessToken);
        this.setState({accessToken: accessToken.accessToken})

        this.fetchUserOrLogout();
        return accessToken;
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
        localStorage.removeItem(UserContextProvider.ACCESS_TOKEN_KEY);
        this.setState(DEFAULT_STATE);
    }
}

export default UserContextProvider;