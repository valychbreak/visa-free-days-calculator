import AccessToken from "../../common/AccessToken";
import { useState, useEffect } from "react";
import React from "react";

class TokenProvider {
    private static TOKEN_KEY = 'REACT_TOKEN_AUTH';

    _token?: AccessToken;
    observers: Array<(isLogged: boolean) => void> = [];

    constructor() {
        let savedToken = localStorage.getItem(TokenProvider.TOKEN_KEY);
        this._token = !!savedToken ? JSON.parse(savedToken) : null;
    }

    async getToken(): Promise<string | null> {
        if (!this._token) {
            return null;
        }

        if (this.isExpired(this._token)) {
            this.setToken(undefined);
        }

        return !this._token ? null : this._token.accessToken;
    }

    setToken(token?: AccessToken): void {
        if (token) {
            localStorage.setItem(TokenProvider.TOKEN_KEY, JSON.stringify(token));
        } else {
            localStorage.removeItem(TokenProvider.TOKEN_KEY);
        }

        this._token = token;
        this.notify();
    }

    isLoggedIn(): boolean {
        console.log("Is logged?", !!this._token);
        return !!this._token;
    }

    subscribe(observer: (isLoggedIn: boolean) => void): void {
        this.observers.push(observer);
    }

    unsubscribe(observer: (isLoggedIn: boolean) => void): void {
        this.observers = this.observers.filter(_observer => _observer !== observer);
    }

    private notify(): void {
        const isLogged = this.isLoggedIn();
        this.observers.forEach(observer => observer(isLogged));
    }

    private isExpired(token?: AccessToken): boolean {
        if (!token?.expiresIn) {
            return false;
        }
        return Date.now() > token.expiresIn;
    }
}

const tokenProvider = new TokenProvider();

export const useAuth = () => {
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

export const login: typeof tokenProvider.setToken = (newToken: AccessToken) => {
    tokenProvider.setToken(newToken);
};

export const logout = () => {
    tokenProvider.setToken(undefined);
};

export const withAuth = (Component: any) => {
    return (props: any) => {
        const auth = useAuth();

        return <Component auth={auth} {...props} />
    }
}