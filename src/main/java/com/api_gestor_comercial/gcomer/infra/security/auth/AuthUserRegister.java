package com.api_gestor_comercial.gcomer.infra.security.auth;

import com.api_gestor_comercial.gcomer.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRegister {

    String username;
    String password;
    String email;
    Role roleName;

}
