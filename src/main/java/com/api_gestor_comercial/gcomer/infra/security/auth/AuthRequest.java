package com.api_gestor_comercial.gcomer.infra.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRequest {
    private String username;
    private String password;
}
