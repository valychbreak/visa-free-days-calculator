package com.valychbreak.calculator.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isTemporary;

    private List<TravelPeriod> travelPeriods;
}
