
class User {
    username: string;
    temporary: boolean;
    roles: string[];

    constructor(username: string, isTemporary?: boolean) {
        this.username = username;
        this.roles = [];

        if (!isTemporary) {
            this.temporary = false;
        } else {
            this.temporary = isTemporary;
        }
    }
}

export default User;