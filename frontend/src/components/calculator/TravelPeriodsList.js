import React, { Component } from 'react'
import Moment from 'react-moment';

class TraverlPeriodsList extends Component {

    render() {
        return (
            <table>
                <thead>
                    <tr>
                        <th>Travel Period</th>
                        <th>Country</th>
                        <th>Note</th>
                    </tr>
                </thead>    
                <tbody>
                    {this.props.travelPeriods.map((period, index) => (
                        <tr key={index}>
                            <td><Moment date={period.start} format="DD-MM-YYYY"/> - <Moment date={period.end} format="DD-MM-YYYY"/></td>
                            <td>{period.country}</td>
                            <td>{period.note}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        )
    }
}

export default TraverlPeriodsList;