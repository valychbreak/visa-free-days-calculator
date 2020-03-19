
import Axios from 'axios';
import React, { Component } from 'react';
import TravelPeriodFrom from './TravelPeriodForm';
import CalculatorContextProvider from './context/CalendarContextProvider';
import CalculatorContext from './context/CalculatorContext';
import TraverlPeriodsList from './TravelPeriodsList';
import { Box, Paper } from '@material-ui/core';
import './VisaFreeDateCalculator.css'

class VisaFreeDateCalculator extends Component {

    constructor(props) {
        super(props);

        this.state = {
            travelPeriods: [],
            failed: false,
        };
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
                        <Paper elevation={3}>
                            <TravelPeriodFrom />
                        </Paper>
                    </Box>
                    <Box m="auto">
                        <h3>Added periods</h3>
                        <Paper>
                            <CalculatorContext.Consumer>
                                {context => (
                                    <TraverlPeriodsList travelPeriods={context.travelPeriods} />
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