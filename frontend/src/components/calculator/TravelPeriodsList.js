import React, { Component } from 'react'

class TraverlPeriodsList extends Component {

    render() {
        return (
            <table>
                <tr>
                    <th>Travel Period</th>
                    <th>Country</th>
                    <th>Note</th>
                </tr>
                {this.props.travelPeriods.map((period, index) => (
                    <tr key={index}>
                        <td>{period.start} - {period.end}</td><td>{period.country}</td><td>{period.note}</td>
                    </tr>
                ))}
            </table>
        )
    }
}

export default TraverlPeriodsList;