import { Moment } from "moment";

class TravelPeriod {
    id?: number;
    start: Moment;
    end: Moment;
    country: string;
    note: string;

    constructor(start: Moment, end: Moment, country: string, note = '') {
        this.start = start;
        this.end = end;
        this.country = country;
        this.note = note;
    }
}

export default TravelPeriod;