import React, { Component } from "react";
import CalculatorContext from "./context/CalculatorContext";
import './TravelPeriodForm.css'
import moment from "moment";

class TravelPeriodFrom extends Component {

    constructor(props) {
        super(props);

        this.state = {
            startDate: '', 
            endDate: '', 
            country: '', 
            note: '',
            errors: {
                travelPeriodError: []
            }
        };

        this.saveNewPeriod = this.saveNewPeriod.bind(this);
        this.handlePeriodInputChange = this.handlePeriodInputChange.bind(this);
    }

    render() {
        return (
            <CalculatorContext.Consumer>
                {context => (
                    <form onSubmit={e => this.saveNewPeriod(context, e)}>
                        <label>Start</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="startDate" />

                        <label>End</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="endDate" />

                        <label>Country</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="country" />

                        <label>Note</label>
                        <input type="text" onChange={this.handlePeriodInputChange} name="note" />

                        <button type="submit">Add</button>
                        <small className="hint">Date format should be dd-mm-yyyy</small>
                        {this.state.errors.travelPeriodError.map((error, index) =>
                            <div className="formError" key={index}>{error}</div>
                        )}
                    </form>
                )}
            </CalculatorContext.Consumer>
        )
    }

    saveNewPeriod(context, event) {
        event.preventDefault();

        const newTravelPeriod = {start: this.toDate(this.state.startDate), end: this.toDate(this.state.endDate), country: this.state.country, note: this.state.note}

        if (this.validate(newTravelPeriod)) {
            context.addNewPeriod(newTravelPeriod);
        }
    }

    toDate(date) {
        return moment(date, "DD-MM-YYYY", true);
    }

    validate(travelPeriod) {
        var errors = [];

        if (this.isBlank(travelPeriod.start)) {
            errors.push('Provide value for start date');
        }

        if (!travelPeriod.start.isValid()) {
            errors.push('Incorrect format of Start date')
        }

        if (this.isBlank(travelPeriod.end)) {
            errors.push('Provide value for end date');
        }

        if (!travelPeriod.end.isValid()) {
            errors.push('Incorrect format of End date')
        }

        if (this.isBlank(travelPeriod.country)) {
            errors.push('Provide value for country');
        }

        this.setState({errors: {travelPeriodError: errors}});
        
        return errors.length === 0;
    }

    isBlank(str) {
        return (!str);
    }

    handlePeriodInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({[inputName]: inputValue});
    }
}

export default TravelPeriodFrom;