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

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setTemporary(user.isTemporary());
        return userDto;
    }
}
