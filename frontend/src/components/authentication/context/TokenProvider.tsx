import AccessToken from '../../../common/AccessToken';

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

export const tokenProvider = new TokenProvider();