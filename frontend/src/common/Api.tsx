import User from "./User";
import Axios from "axios";
import TravelPeriod from "./TravelPeriod";
import moment from "moment";

class Api {
    static API_BASE = 'http://localhost:8080/api';

    async fetchUserInfo(): Promise<User> {
        const response = await Axios.get('/api/user/info');
        return response.data as User;
    }

    async fetchTravelPeriods(): Promise<TravelPeriod[]> {
        const response = await Axios.get('/api/period/all');
        var rawTravelPeriods = response.data;
        var loadedPeriods:TravelPeriod[] = new Array()

        for (var rawTravelPeriod of rawTravelPeriods) {
            let parsedPeriod = TravelPeriod.from(rawTravelPeriod);
            loadedPeriods.push(parsedPeriod);
        }
        return loadedPeriods;
    }

    toDate(date: string) {
        return moment(date, "YYYY-MM-DD", true);
    }
}

let baseURL = process.env.REACT_APP_API_URL;
if (baseURL) {
    Axios.defaults.baseURL = baseURL;
    console.log('API url was set to ' + baseURL);
}

export default new Api();
