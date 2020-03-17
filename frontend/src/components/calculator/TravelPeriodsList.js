import React, { Component } from 'react'

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
                            <td>{period.start} - {period.end}</td><td>{period.country}</td><td>{period.note}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        )
    }
}

export default TraverlPeriodsList;