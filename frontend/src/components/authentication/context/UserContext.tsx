import React from 'react'
import User from '../../../common/User'
import AccessToken from '../../../common/AccessToken';

type UserContext = {
    isAuthenticated(): boolean;
    getUser(): User | null;
    setUser(user: User): void;
    authorizeWithTemporaryUser(): AccessToken;
}

export default React.createContext<UserContext>({
    isAuthenticated: () => {
        throw Error("Not implemented");
    },
    getUser: () => {
        throw Error("Not implemented");
    },
    setUser: () => {
        throw Error("Not implemented")
    },
    authorizeWithTemporaryUser: () => {
        throw Error("Not implemented")
    }

})