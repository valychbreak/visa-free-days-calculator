import React, { Component } from "react";
import {UserContextProviderComponent} from "./UserContext";
import User from "../../../common/User";
import AccessToken from "../../../common/AccessToken";
import Axios from "axios";

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
        Axios.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken.accessToken;

        const userInfoResponse = await Axios.get('http://localhost:8080/api/user/info');
        const user = (userInfoResponse.data as User);
        this.setState({ user: user, accessToken: accessToken.accessToken });
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
        this.setState(DEFAULT_STATE);
    }
}

export default UserContextProvider;