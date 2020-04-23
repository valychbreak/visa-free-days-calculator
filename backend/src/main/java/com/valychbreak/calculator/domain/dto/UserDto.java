package com.valychbreak.calculator.domain.dto;

import com.valychbreak.calculator.domain.User;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Introspected
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean isTemporary;

    public UserDto(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
        this.setTemporary(user.isTemporary());
    }
}
