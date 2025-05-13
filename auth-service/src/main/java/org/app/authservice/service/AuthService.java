package org.app.authservice.service;


import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.app.authservice.dto.LoginRequestDTO;
import org.app.authservice.model.User;
import org.app.authservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token=userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                .map(u->jwtUtil.generateToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()));
        return token;
    }

    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }






}
