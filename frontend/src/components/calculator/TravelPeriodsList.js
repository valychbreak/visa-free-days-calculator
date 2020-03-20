import React, { Component } from 'react'
import Moment from 'react-moment';
import { TableContainer, Table, TableRow, TableCell, TableHead, TableBody } from '@material-ui/core';

class TraverlPeriodsList extends Component {

    render() {
        return (
            <TableContainer >
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Travel Period</TableCell>
                            <TableCell>Country</TableCell>
                            <TableCell>Note</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.props.travelPeriods.map((period, index) => (
                            <TableRow key={index}>
                                <TableCell><Moment date={period.start} format="DD-MM-YYYY"/> - <Moment date={period.end} format="DD-MM-YYYY"/></TableCell>
                                <TableCell>{period.country}</TableCell>
                                <TableCell>{period.note}</TableCell>
                            </TableRow>
                        ))} 
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default TraverlPeriodsList;