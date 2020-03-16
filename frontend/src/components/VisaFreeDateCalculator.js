
import React, {Component} from 'react'
import Axios from 'axios'

class VisaFreeDateCalculator extends Component {

    state = {
        travelPeriods: [],
        failed: false
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
            <div>
                <h2>Calculator</h2>
                <h3>Added periods</h3>
                <ul>
                {this.state.travelPeriods.map(period => <li>{period.start} - {period.end}, {period.country}. Note: {period.note}</li>)}
                </ul>
            </div>
        )
    }
}

export default VisaFreeDateCalculator;