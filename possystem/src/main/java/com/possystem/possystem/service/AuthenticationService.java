package com.possystem.possystem.service;

import com.possystem.possystem.auth.AuthenticationRequest;
import com.possystem.possystem.auth.AuthenticationResponse;
import com.possystem.possystem.auth.RegisterRequest;
import com.possystem.possystem.config.JwtService;
import com.possystem.possystem.domain.Role;
import com.possystem.possystem.dto.UserDTO;
import com.possystem.possystem.repository.UserRepository;
import com.possystem.possystem.service.implementation.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    //region injections
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    //endregion

    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 UserServiceImpl userService){
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        UserDTO user = UserDTO.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(userService.toDomain(user));
        String jwtToken = jwtService.generateToken(userService.toDomain(user));

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDTO user = userService.toDto(
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("User Not Found"))
        );

        String jwtToken = jwtService.generateToken(userService.toDomain(user));

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

    }

}