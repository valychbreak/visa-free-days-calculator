import AccessToken from '../../../common/AccessToken';
import Axios from 'axios';

export class TokenProvider {
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
            console.log('Removing token as it expired');
        }

        return this._token.accessToken;
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
        if (!token) {
            return false;
        }

        let expirationDate = this.getExpirationDate(token.accessToken);
        return !!expirationDate ?  Date.now() > expirationDate : false;
    }

    private getExpirationDate(jwtToken?: string): number | null {
        if (!jwtToken) {
            return null;
        }
    
        const jwt = JSON.parse(atob(jwtToken.split('.')[1]));
    
        // multiply by 1000 to convert seconds into milliseconds
        return jwt?.exp ? jwt.exp * 1000 : null;
    };
}

export const tokenProvider = new TokenProvider();

// Leaving here and not in component because of page reloading issue (authorization header is not being set)
Axios.interceptors.request.use(req => {
    return tokenProvider.getToken()
        .then(token => {
            if (token) {
                req.headers.authorization = 'Bearer ' + token;
            }
            return req;
        });
});