package com.ardwiinoo.loansapi.model.dto.user;

import com.ardwiinoo.loansapi.model.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole userRole;
    private boolean isEnable;
}
