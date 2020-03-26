import React, { Component } from 'react';
import TravelPeriodFrom from './TravelPeriodForm';
import CalculatorContextProvider from './context/CalendarContextProvider';
import CalculatorContext from './context/CalculatorContext';
import TraverlPeriodsList from './TravelPeriodsList';
import { Box, Paper } from '@material-ui/core';
import './VisaFreeDateCalculator.css'

class VisaFreeDateCalculator extends Component {

    componentDidMount() {

    }

    render() {
        return (
            <CalculatorContextProvider>
                <div style={{
                    width: 700,
                    margin: 'auto'
                }}>
                    <h2>Calculator</h2>
                    
                    <Box m="auto">
                        <h3>New period</h3>
                        <Paper className="block">
                            <TravelPeriodFrom />
                        </Paper>
                    </Box>
                    <Box m="auto">
                        <h3>Added periods</h3>
                        <Paper className="block">
                            <CalculatorContext.Consumer>
                                {context => (
                                    <TraverlPeriodsList travelPeriods={context.travelPeriods} onTravelPeriodDelete={context.deleteTravelPeriod} />
                                )}
                            </CalculatorContext.Consumer>
                        </Paper>
                    </Box>
                </div>
            </CalculatorContextProvider>
        )
    }
}

export default VisaFreeDateCalculator;