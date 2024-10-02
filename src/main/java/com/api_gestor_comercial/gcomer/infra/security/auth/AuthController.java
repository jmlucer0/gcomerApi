package com.api_gestor_comercial.gcomer.infra.security.auth;

import com.api_gestor_comercial.gcomer.domain.user.User;
import com.api_gestor_comercial.gcomer.domain.user.UserRepository;
import com.api_gestor_comercial.gcomer.infra.security.jwt.*;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private CustomUserDetailService userDetailsService;

    private JwtUtil jwtUtil;

    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil, CustomUserDetailService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthUserRegister request) {
        try {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body("El nombre de usuario ya está en uso.");
            }

            User newUser = userDetailsService.createNewUser(request);

            userRepository.save(newUser);

            return ResponseEntity.ok("Usuario registrado exitosamente!");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al registrar el usuario.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Nombre de usuario o contraseña inválidos.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
