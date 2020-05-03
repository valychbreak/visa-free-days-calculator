
class AccessToken {
    accessToken: string;
    refreshToken: string;
    tokenType: string;
    expiresIn: number;

    static from(tokenJson: any): AccessToken {
        const token = tokenJson.access_token;
        const refreshToken = tokenJson.refresh_token;
        const tokenType = tokenJson.token_type;
        const expiresIn = tokenJson.expires_in;

        if (!token || !refreshToken || !tokenType || !expiresIn) {
            throw new Error('Failed to parse token response');
        }

        return new AccessToken(token, refreshToken, tokenType, expiresIn);
    }

    constructor(accessToken: string, refreshToken: string, tokenType: string, expiresIn: number) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }
}

export default AccessToken;