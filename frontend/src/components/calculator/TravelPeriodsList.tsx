import React, { Component } from 'react'
import Moment from 'react-moment';
import { TableContainer, Table, TableRow, TableCell, TableHead, TableBody, IconButton } from '@material-ui/core';
import { Delete } from '@material-ui/icons'
import TravelPeriod from '../../common/TravelPeriod';

type TravelPeriodListProps = {
    travelPeriods: TravelPeriod[];
    onTravelPeriodDelete(travelPeriod: TravelPeriod): void;
}

class TraverlPeriodsList extends Component<TravelPeriodListProps> {

    render() {
        return (
            <TableContainer >
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Travel Period</TableCell>
                            <TableCell>Country</TableCell>
                            <TableCell>Note</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.props.travelPeriods.map((period, index) => (
                            <TableRow key={index}>
                                <TableCell>
                                    <Moment date={period.start} format="DD-MM-YYYY"/> - <Moment date={period.end} format="DD-MM-YYYY"/>
                                </TableCell>
                                <TableCell>{period.country}</TableCell>
                                <TableCell>{period.note}</TableCell>
                                <TableCell>
                                    <IconButton color="primary" onClick={(e) => this.props.onTravelPeriodDelete(period)}><Delete /></IconButton>
                                </TableCell>
                            </TableRow>
                        ))} 
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default TraverlPeriodsList;