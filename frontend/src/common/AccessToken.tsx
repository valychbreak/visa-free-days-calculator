
class AccessToken {
    accessToken: string;
    refreshToken: string;
    tokenType: string;
    expiresIn: number;

    constructor(accessToken: string, refreshToken: string, tokenType: string, expiresIn: number) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}

export default AccessToken;