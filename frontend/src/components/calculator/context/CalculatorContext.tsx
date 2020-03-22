import React from 'react'
import TravelPeriod from '../../../common/TravelPeriod';


type CalculatorContext = {
    travelPeriods: TravelPeriod[],
    addNewPeriod(travelPeriod: TravelPeriod): Promise<TravelPeriod>;
    deleteTravelPeriod(travelPeriod: TravelPeriod): void;
}

export default React.createContext<CalculatorContext>({
    travelPeriods: [],
    addNewPeriod: (travelPeriod: TravelPeriod) => {
        throw new Error('Not implemented');
    },
    deleteTravelPeriod: (travelPeriod: TravelPeriod) => {
        throw new Error("Note implemented");
    }
});