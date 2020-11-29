import moment, { Moment, Duration } from "moment";

class TravelPeriod {
    id?: number;
    start: Moment;
    end: Moment;
    country: string;
    note: string;

    static from(travelPeriodJson: any) {
        let travelPeriod = new TravelPeriod(this.toDate(travelPeriodJson.start), this.toDate(travelPeriodJson.end), travelPeriodJson.country, travelPeriodJson.note);

        if (travelPeriodJson.id) {
            travelPeriod.id = travelPeriodJson.id;
        }
        return travelPeriod;
    }

    private static toDate(date: string) {
        return moment(date, "YYYY-MM-DD", true);
    }
    

    constructor(start: Moment, end: Moment, country: string, note = '') {
        this.start = start;
        this.end = end;
        this.country = country;
        this.note = note;
    }

    get duration(): Duration {
        return moment.duration(this.end.diff(this.start)).add(1, 'day');
    }
}

export default TravelPeriod;