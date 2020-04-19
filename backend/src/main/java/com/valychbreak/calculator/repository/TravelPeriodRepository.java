package com.valychbreak.calculator.repository;

import com.valychbreak.calculator.domain.TravelPeriod;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TravelPeriodRepository extends CrudRepository<TravelPeriod, Long> {

}
