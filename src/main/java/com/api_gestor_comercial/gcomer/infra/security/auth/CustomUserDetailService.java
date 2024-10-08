package com.api_gestor_comercial.gcomer.infra.security.auth;

import com.api_gestor_comercial.gcomer.domain.user.Role;
import com.api_gestor_comercial.gcomer.domain.user.User;
import com.api_gestor_comercial.gcomer.domain.user.UserPrincipal;
import com.api_gestor_comercial.gcomer.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public CustomUserDetailService(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserPrincipal(user);
    }

    public User createNewUser(AuthUserRegister request){
       User newUser = new User();
       newUser.setUsername(request.getUsername());
       newUser.setPassword(passwordEncoder.encode(request.getPassword()));
       newUser.setRole(request.getRoleName());
       newUser.setEmail(request.getEmail());
       return newUser;
    }

    @Transactional
    public boolean deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (user.getRole() != Role.ADMIN) {
            userRepository.deleteById(id);
            System.out.println(userRepository.findById(id).toString());
            return true;
        } else {
            System.out.println("Usuarios con el Rol 'ADMIN' no pueden ser eliminados");
            return false;
        }
    }
}
