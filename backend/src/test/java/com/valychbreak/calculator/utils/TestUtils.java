package com.valychbreak.calculator.utils;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static User createUser(String username) {
        return createUser(username, new ArrayList<>());
    }
    public static User createUser(String username, List<TravelPeriod> travelPeriodList) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("test");
        user.setEmail("t@t.com");
        user.setTravelPeriods(travelPeriodList);
        return user;
    }
}
