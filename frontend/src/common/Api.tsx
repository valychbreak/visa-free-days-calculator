import User from "./User";
import Axios from "axios";

class Api {
    static API_BASE = 'http://localhost:8080/api';

    async fetchUserInfo(): Promise<User> {
        const response = await Axios.get('/api/user/info');
        return response.data as User;
    }
}

// TODO: set base URL for Axios for dev build

export default new Api();