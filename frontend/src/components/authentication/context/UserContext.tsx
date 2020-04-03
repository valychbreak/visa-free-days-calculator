import React from 'react'
import User from '../../../common/User'
import AccessToken from '../../../common/AccessToken';

export interface UserContext {
    isAuthenticated(): boolean;
    getUser(): User | undefined;
    setUser(user: User): void;
    authorizeWithTemporaryUser(): Promise<AccessToken>;
    logout(): void;
}

const userContext = React.createContext<UserContext>({
    isAuthenticated: () => {
        throw Error("Not implemented");
    },
    getUser: () => {
        throw Error("Not implemented");
    },
    setUser: () => {
        throw Error("Not implemented");
    },
    authorizeWithTemporaryUser: () => {
        throw Error("Not implemented");
    },
    logout: () => {
        throw Error("Not implemented");
    }
});

export const UserContextProviderComponent = userContext.Provider;
export const UserContextConsumer = userContext.Consumer;

export default userContext;