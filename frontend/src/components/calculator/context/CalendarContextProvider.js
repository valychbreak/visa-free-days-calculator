import React, { Component } from "react";
import CalculatorContext from "./CalculatorContext";

const DEFAULT_STATE = {
    travelPeriods: []
}

class CalculatorContextProvider extends Component {
    
    constructor(props) {
        super(props);

        this.state = DEFAULT_STATE;
    }

    render() {
        return (
            <CalculatorContext.Provider value={{
                travelPeriods: this.state.travelPeriods,
                addNewPeriod: travelPeriod => {
                    console.log(travelPeriod);
                    this.setState({travelPeriods: [...this.state.travelPeriods, travelPeriod]});
                }
            }}>
                {this.props.children}
            </CalculatorContext.Provider>
        )
    }
}

export default CalculatorContextProvider;