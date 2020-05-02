import AccessToken from "../../common/AccessToken";
import { useState, useEffect } from "react";
import React from "react";
import { tokenProvider } from "./context/TokenProvider";

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