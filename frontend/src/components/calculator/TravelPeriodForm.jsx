import React, { Component } from "react";
import CalculatorContext from "./context/CalculatorContext.tsx";
import './TravelPeriodForm.css'
import moment from "moment";
import {Button, FormControl, InputLabel, Input, FormHelperText, Grid} from "@material-ui/core"


const DEFAULT_STATE = {
    startDate: '', 
    endDate: '', 
    country: '', 
    note: '',
    errors: {
        startDate: '',
        endDate: '',
        country: ''
    }
}

class TravelPeriodFrom extends Component {

    constructor(props) {
        super(props);

        this.state = DEFAULT_STATE;

        this.saveNewPeriod = this.saveNewPeriod.bind(this);
        this.handlePeriodInputChange = this.handlePeriodInputChange.bind(this);
    }

    render() {
        return (
            <CalculatorContext.Consumer>
                {context => (
                    <form onSubmit={e => this.saveNewPeriod(context, e)}>
                        <Grid container spacing={1}>
                            <Grid item xs={6}>
                                <FormControl required error={!!this.state.errors.startDate} fullWidth >
                                    <InputLabel htmlFor="startDate">Start date</InputLabel>
                                    <Input id="startDate" name="startDate" value={this.state.startDate} onChange={this.handlePeriodInputChange} />
                                    <FormHelperText id="startDateHelperText">{this.state.errors.startDate}</FormHelperText>
                                </FormControl>
                            </Grid>

                            <Grid item xs={6}>
                                <FormControl required error={!!this.state.errors.endDate} fullWidth > 
                                    <InputLabel htmlFor="endDate">End date</InputLabel>
                                    <Input id="endDate" name="endDate" value={this.state.endDate} onChange={this.handlePeriodInputChange} />
                                    <FormHelperText id="endHelperText">{this.state.errors.endDate}</FormHelperText>
                                </FormControl>
                            </Grid>

                            <Grid item xs={6}>
                                <FormControl required error={!!this.state.errors.country} fullWidth >
                                    <InputLabel htmlFor="country">Country</InputLabel>
                                    <Input id="country" name="country" value={this.state.country} onChange={this.handlePeriodInputChange} />
                                    <FormHelperText id="countryHelperText">{this.state.errors.country}</FormHelperText>
                                </FormControl>
                            </Grid>

                            <Grid item xs={6}>
                                <FormControl fullWidth >
                                    <InputLabel htmlFor="note">Note</InputLabel>
                                    <Input id="note" name="note" value={this.state.note} onChange={this.handlePeriodInputChange} />
                                </FormControl>
                            </Grid>

                            <Button type="submit" color="primary" variant="contained" fullWidth>Add</Button>
                        </Grid>
                    </form>
                )}
            </CalculatorContext.Consumer>
        )
    }

    saveNewPeriod(context, event) {
        event.preventDefault();

        const newTravelPeriod = {start: this.toDate(this.state.startDate), end: this.toDate(this.state.endDate), country: this.state.country, note: this.state.note}
        
        if (this.validate(newTravelPeriod)) {
            context.addNewPeriod(newTravelPeriod)
                .then(response => {
                    this.setState(DEFAULT_STATE);
                }).catch(error => {
                    
                });
        }
    }

    toDate(date) {
        return moment(date, "DD-MM-YYYY", true);
    }

    validate(travelPeriod) {
        var fieldErrors = {startDate: '', endDate: '', country: ''}

        if (!travelPeriod.start.isValid()) {
            fieldErrors.startDate = 'Date format should be dd-mm-yyyy';
        }

        if (!travelPeriod.end.isValid()) {
            fieldErrors.endDate = 'Date format should be dd-mm-yyyy';
        }

        if (this.isBlank(travelPeriod.country)) {
            fieldErrors.country = 'Provide value for country'
        }

        this.setState({errors: fieldErrors});
        
        return !fieldErrors.startDate && !fieldErrors.endDate && !fieldErrors.country;
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