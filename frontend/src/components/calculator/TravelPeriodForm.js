import React, { Component } from "react";
import CalculatorContext from "./context/CalculatorContext";

class TravelPeriodFrom extends Component {

    constructor(props) {
        super(props);

        this.state = {
            startDate: '', 
            endDate: '', 
            country: '', 
            note: ''
        };

        this.saveNewPeriod = (context, event) => {
            console.log('Created new period');
            event.preventDefault();
    
            const newTravelPeriod = {start: this.state.startDate, end: this.state.endDate, country: this.state.country, note: this.state.note}
            
            context.addNewPeriod(newTravelPeriod);
            // this.setState(state => {
            //     const periods = state.travelPeriods.concat(newTravelPeriod);
    
            //     return {travelPeriods: periods}
            // });
    
            this.saveNewPeriod = this.saveNewPeriod.bind(this);
            this.handlePeriodInputChange = this.handlePeriodInputChange.bind(this);
        }
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
                    </form>
                )}
            </CalculatorContext.Consumer>
        )
    }

    handlePeriodInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({[inputName]: inputValue});
    }
}

export default TravelPeriodFrom;