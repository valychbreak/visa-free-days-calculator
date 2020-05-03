import AccessToken from "../../common/AccessToken";
import { useState, useEffect } from "react";
import React from "react";
import { tokenProvider, TokenProvider } from "./context/TokenProvider";

export type AuthenticationAwareProp = {
    isLoggedIn: boolean;
}

export const useAuthentication = () => {
    const [isLogged, setIsLogged] = useState(tokenProvider.isLoggedIn());

    useEffect(() => {
        const listener = (newIsLogged: boolean) => {
            setIsLogged(newIsLogged);
        };

        tokenProvider.subscribe(listener);
        return () => {
            tokenProvider.unsubscribe(listener);
        };
    }, []);


    return [isLogged];
}

class AuthenticationManager {
    private _tokenProvider: TokenProvider;
    
    constructor(tokenProvider: TokenProvider) {
        this._tokenProvider = tokenProvider;
    }

    login(newToken: AccessToken): void {
        this._tokenProvider.setToken(newToken);
    }

    logout(): void {
        this._tokenProvider.setToken(undefined);
    }
}

export const authenticationManager = new AuthenticationManager(tokenProvider);

export const withAuth = (Component: any) => {
    return (props: any) => {
        const [logged] = useAuthentication();

        return <Component isLoggedIn={logged} {...props} />
    }
}