import React, { Component } from "react";
import CalculatorContext from "./CalculatorContext";
import Axios from "axios";

const DEFAULT_STATE = {
    travelPeriods: []
}

class CalculatorContextProvider extends Component {
    
    constructor(props) {
        super(props);

        this.state = DEFAULT_STATE;
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
            <CalculatorContext.Provider value={{
                travelPeriods: this.state.travelPeriods,
                addNewPeriod: travelPeriod => {

                    const params = new URLSearchParams();
                    params.append('start', travelPeriod.start.format('YYYY-MM-DD'));
                    params.append('end', travelPeriod.end.format('YYYY-MM-DD'));
                    params.append('country', travelPeriod.country);
                    params.append('note', travelPeriod.note);

                    Axios.post("http://localhost:8080/api/period/add", params, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
                    this.setState({travelPeriods: [...this.state.travelPeriods, travelPeriod]});
                }
            }}>
                {this.props.children}
            </CalculatorContext.Provider>
        )
    }
}

export default CalculatorContextProvider;