package com.valychbreak.calculator.repository;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface TravelPeriodRepository extends CrudRepository<TravelPeriod, Long> {
    List<TravelPeriod> findByUser(User user);
}
