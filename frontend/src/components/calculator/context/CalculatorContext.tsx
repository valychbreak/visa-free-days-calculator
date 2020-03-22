import React from 'react'
import TravelPeriod from '../../../common/TravelPeriod';


type CalculatorContext = {
    travelPeriods: TravelPeriod[],
    addNewPeriod(travelPeriod: TravelPeriod): Promise<TravelPeriod>;
}

export default React.createContext<CalculatorContext>({
    travelPeriods: [],
    addNewPeriod: (travelPeriod: TravelPeriod) => {
        throw new Error('Not implemented');
    }
});