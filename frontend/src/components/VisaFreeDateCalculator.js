
import React, {Component} from 'react'
import Axios from 'axios'

class VisaFreeDateCalculator extends Component {

    constructor(props) {
        super(props);

        this.state = {
            travelPeriods: [],
            failed: false,
            startDate: '', 
            endDate: '', 
            country: '', 
            note: ''
        };

        this.saveNewPeriod = this.saveNewPeriod.bind(this);
        this.handlePeriodInputChange = this.handlePeriodInputChange.bind(this);
    }

    componentDidMount() {
        Axios.get('http://localhost:8080/api/period/all')
            .then(res => {
                this.setState({travelPeriods: res.data, failed: false})
            })
            .catch(err => {
                this.setState({travelPeriods: [], failed: true})
            })
    }

    saveNewPeriod(event) {
        console.log('Created new period');
        event.preventDefault();

        const newTravelPeriod = {start: this.state.startDate, end: this.state.endDate, country: this.state.country, note: this.state.note}
        console.log(newTravelPeriod);

        this.setState(state => {
            const periods = state.travelPeriods.concat(newTravelPeriod);

            return {travelPeriods: periods}
        });
    }

    handlePeriodInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({[inputName]: inputValue});
    }

    render() {
        return (
            <div>
                <h2>Calculator</h2>
                <h3>New period</h3>
                <div>
                    <form onSubmit={this.saveNewPeriod}>
                        <label>Start</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="startDate" />

                        <label>End</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="endDate" />

                        <label>Country</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="country" />

                        <label>Note</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="note" />

                        <button type="submit">Add</button>
                    </form>
                </div>
                <h3>Added periods</h3>
                <ul>
                {this.state.travelPeriods.map((period, index) => <li key={index}>{period.start} - {period.end}, {period.country}. Note: {period.note}</li>)}
                </ul>
            </div>
        )
    }
}

export default VisaFreeDateCalculator;