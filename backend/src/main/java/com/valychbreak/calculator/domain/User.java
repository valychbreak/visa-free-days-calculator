package com.valychbreak.calculator.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isTemporary;

    @OneToMany(mappedBy = "user")
    private List<TravelPeriod> travelPeriods = new ArrayList<>();
}
