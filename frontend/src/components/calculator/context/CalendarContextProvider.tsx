import React, { Component } from "react";
import Axios from "axios";
import TravelPeriod from "../../../common/TravelPeriod";
import CalculatorContext from "./CalculatorContext";
import { withSnackbar, WithSnackbarProps } from "notistack";

type CalculatorProviderState = {
    travelPeriods: TravelPeriod[],
    failed: boolean
}

class CalculatorContextProvider extends Component<WithSnackbarProps, CalculatorProviderState> {
    static DEFAULT_STATE: CalculatorProviderState = {
        travelPeriods: [],
        failed: false
    }

    constructor(props: any) {
        super(props);
        this.state = CalculatorContextProvider.DEFAULT_STATE;
    }

    componentDidMount() {
        this.loadTravelPeriods();
    }

    render() {
        return (
            <CalculatorContext.Provider value={{
                travelPeriods: this.state.travelPeriods,
                addNewPeriod: (travelPeriod: TravelPeriod) => this.addTravelPeriod(travelPeriod),
                deleteTravelPeriod: (travelPeriod: TravelPeriod) => this.deleteTravelPeriod(travelPeriod)
            }}>
                {this.props.children}
            </CalculatorContext.Provider>
        )
    }

    private addTravelPeriod(travelPeriod: TravelPeriod) {
        const params = new URLSearchParams();
        params.append('start', travelPeriod.start.format('YYYY-MM-DD'));
        params.append('end', travelPeriod.end.format('YYYY-MM-DD'));
        params.append('country', travelPeriod.country);
        params.append('note', travelPeriod.note);

        const config = {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        };

        return Axios.post("http://localhost:8080/api/period/add", params, config)
            .then(response => {
                const createTravelPeriod = (response.data as TravelPeriod);
                this.setState({ travelPeriods: [...this.state.travelPeriods, createTravelPeriod] });

                this.props.enqueueSnackbar('Created new travel period', {variant: 'success'});
                return createTravelPeriod;
            }).catch(error => {
                this.props.enqueueSnackbar('Failed to add new travel period. ' + error, {variant: 'error'});
                throw error;
            });
    }

    private deleteTravelPeriod(travelPeriod: TravelPeriod) {
        Axios.delete('http://localhost:8080/api/period/' + travelPeriod.id + '/delete')
            .then(reponse => {
                const newTravelPeriods = this.state.travelPeriods.filter(existingTravelPeriod => existingTravelPeriod.id !== travelPeriod.id);
                this.setState({travelPeriods: [...newTravelPeriods]});

                this.props.enqueueSnackbar('Travel period was removed');
            }).catch(error => {
                this.props.enqueueSnackbar('Failed to delete travel period. ' + error, {variant: 'error'});
            });
    }

    private loadTravelPeriods() {
        Axios.get('http://localhost:8080/api/period/all')
            .then(res => {
                this.setState({ travelPeriods: res.data, failed: false });
            })
            .catch(err => {
                this.setState({ travelPeriods: [], failed: true });
                this.props.enqueueSnackbar('Failed to fetch travel periods. ' + err, {variant: 'error'});
            });
    }
}

export default withSnackbar(CalculatorContextProvider);