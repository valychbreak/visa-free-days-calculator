import User from "./User";
import Axios from "axios";

class Api {
    static API_BASE = 'http://localhost:8080/api';

    async fetchUserInfo(): Promise<User> {
        const response = await Axios.get('/api/user/info');
        return response.data as User;
    }
}

const isProd = process.env.NODE_ENV === 'production';

if (isProd) {
    console.log('Loading production config...');
} else {
    console.log('Loading dev config...');
    Axios.defaults.baseURL = "http://localhost:8080";
}

export default new Api();