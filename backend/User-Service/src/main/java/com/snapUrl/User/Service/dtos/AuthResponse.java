package com.snapUrl.User.Service.dtos;

import com.snapUrl.User.Service.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String email;
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;

    public AuthResponse() {

    }
}
