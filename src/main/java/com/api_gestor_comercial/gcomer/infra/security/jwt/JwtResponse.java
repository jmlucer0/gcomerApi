package com.api_gestor_comercial.gcomer.infra.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class JwtResponse {
    private String token;
}
