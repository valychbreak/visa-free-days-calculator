import React, { Component } from 'react'
import Moment from 'react-moment';
import { Grid } from '@material-ui/core';

class TraverlPeriodsList extends Component {

    render() {
        return (
            // <table>
            //     <thead>
            //         <tr>
            //             <th>Travel Period</th>
            //             <th>Country</th>
            //             <th>Note</th>
            //         </tr>
            //     </thead>    
            //     <tbody>
            //         {this.props.travelPeriods.map((period, index) => (
            //             <tr key={index}>
            //                 <td><Moment date={period.start} format="DD-MM-YYYY"/> - <Moment date={period.end} format="DD-MM-YYYY"/></td>
            //                 <td>{period.country}</td>
            //                 <td>{period.note}</td>
            //             </tr>
            //         ))}
            //     </tbody>
            // </table>
            <div>
                <Grid container >
                    <Grid item xs={4}>Travel Period</Grid>
                    <Grid item xs={4}>Country</Grid>
                    <Grid item xs={4}>Note</Grid>
                </Grid>
                {this.props.travelPeriods.map((period, index) => (
                    <Grid container key={index}>
                            <Grid item xs={4}><Moment date={period.start} format="DD-MM-YYYY"/> - <Moment date={period.end} format="DD-MM-YYYY"/></Grid>
                            <Grid item xs={4}>{period.country}</Grid>
                            <Grid item xs={4}>{period.note}</Grid>
                    </Grid>
                ))}
            </div>
        )
    }
}

export default TraverlPeriodsList;