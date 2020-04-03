import User from "./User";
import Axios from "axios";

class Api {
    static API_BASE = 'http://localhost:8080/api';

    async fetchUserInfo(): Promise<User> {
        const response = await Axios.get(Api.API_BASE + '/user/info');
        return response.data as User;
    }
}

export default new Api();