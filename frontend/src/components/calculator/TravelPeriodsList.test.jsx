import React from 'react';
import { render, screen } from '@testing-library/react';
import TravelPeriodsList from './TravelPeriodsList';
import TravelPeriod from '../../common/TravelPeriod';
import moment from 'moment';
 
 
describe('TravelPeriodsList', () => {
  test('Shows text when no periods exist', () => {
    render(<TravelPeriodsList travelPeriods={[]}/>);
    expect(screen.queryByText("None created so far.")).toBeInTheDocument();
  });

  test('Displays a travel period', () => {
    let travelPeriod = new TravelPeriod(moment("2020-01-01"), moment("2020-01-05"), "Poland");

    render(<TravelPeriodsList travelPeriods={[travelPeriod]}/>);

    expect(screen.queryByText("None created so far.")).toBeNull();
    expect(screen.queryByText(/01-01-2020/)).toBeInTheDocument();
    expect(screen.queryByText(/05-01-2020/)).toBeInTheDocument();
    expect(screen.queryByText(/Poland/)).toBeInTheDocument();
  });

  it.each`
    startDate       | endDate         | expectedDays
    ${"2020-01-01"} | ${"2020-01-01"} | ${"1 day"}
    ${"2020-01-01"} | ${"2020-01-05"} | ${"5 days"}
    ${"2019-12-28"} | ${"2020-01-01"} | ${"5 days"}
  `('should return $expectedDays for difference in dates: $startDate and $endDate', ({startDate, endDate, expectedDays}) => {
    let travelPeriod = new TravelPeriod(moment(startDate), moment(endDate), "Poland");

    render(<TravelPeriodsList travelPeriods={[travelPeriod]}/>);

    expect(screen.queryByText(expectedDays.toString())).toBeInTheDocument();
  });
});