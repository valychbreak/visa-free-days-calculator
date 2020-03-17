
import Axios from 'axios';
import React, { Component } from 'react';
import TravelPeriodFrom from './TravelPeriodForm';
import CalculatorContextProvider from './context/CalendarContextProvider';
import CalculatorContext from './context/CalculatorContext';
import TraverlPeriodsList from './TravelPeriodsList';

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
                <div>
                    <h2>Calculator</h2>
                    
                    <h3>New period</h3>
                    <TravelPeriodFrom />
                    
                    <hr />

                    <CalculatorContext.Consumer>
                        {context => (
                            <div>
                                <h3>Added periods</h3>
                                <TraverlPeriodsList travelPeriods={context.travelPeriods} />
                            </div>
                        )}
                    </CalculatorContext.Consumer>
                </div>
            </CalculatorContextProvider>
        )
    }
}

export default VisaFreeDateCalculator;