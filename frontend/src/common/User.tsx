
class User {
    username: string;
    roles: string[];

    constructor(username: string) {
        this.username = username;
        this.roles = [];
    }
}

export default User;