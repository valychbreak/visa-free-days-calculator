import React, { Component } from 'react';
import TravelPeriodFrom from './TravelPeriodForm';
import CalculatorContextProvider from './context/CalendarContextProvider';
import CalculatorContext from './context/CalculatorContext';
import TraverlPeriodsList from './TravelPeriodsList';
import { Box, Paper, WithStyles, withStyles } from '@material-ui/core';

const useStyles = () => ({
    paper: { 
        padding: '1em'
    },
    mainBlock: {
        width: 700,
        margin: 'auto'
    }
});

class VisaFreeDateCalculator extends Component<WithStyles, {}> {

    render() {
        const { classes } = this.props;
        return (
            <CalculatorContextProvider>
                <div className={classes.mainBlock}>
                    <h2>Calculator</h2>
                    
                    <Box m="auto">
                        <h3>New period</h3>
                        <Paper className={classes.paper}>
                            <TravelPeriodFrom />
                        </Paper>
                    </Box>
                    <Box m="auto">
                        <h3>Added periods</h3>
                        <Paper className={classes.paper}>
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

export default withStyles(useStyles)(VisaFreeDateCalculator);