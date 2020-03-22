import React, { Component } from "react";
import Axios from "axios";
import TravelPeriod from "../../../common/TravelPeriod";
import CalculatorContext from "./CalculatorContext";

type CalculatorProviderState = {
    travelPeriods: TravelPeriod[],
    failed: boolean
}

class CalculatorContextProvider extends Component<{}, CalculatorProviderState> {
    static DEFAULT_STATE: CalculatorProviderState = {
        travelPeriods: [],
        failed: false
    }

    constructor(props: any) {
        super(props);
        
        this.state = CalculatorContextProvider.DEFAULT_STATE;
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
                addNewPeriod: (travelPeriod: TravelPeriod) => {

                    const params = new URLSearchParams();
                    params.append('start', travelPeriod.start.format('YYYY-MM-DD'));
                    params.append('end', travelPeriod.end.format('YYYY-MM-DD'));
                    params.append('country', travelPeriod.country);
                    params.append('note', travelPeriod.note);

                    this.setState({travelPeriods: [...this.state.travelPeriods, travelPeriod]});

                    return Axios.post("http://localhost:8080/api/period/add", params, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
                }
            }}>
                {this.props.children}
            </CalculatorContext.Provider>
        )
    }
}

export default CalculatorContextProvider;