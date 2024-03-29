package com.vocation.travel.security;

import com.vocation.travel.dto.AuthDTO.LoginRequest;
import com.vocation.travel.dto.AuthDTO.Response;
import com.vocation.travel.entity.User;
import com.vocation.travel.model.AuthUser;
import com.vocation.travel.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Login service.
 *
 * @author Minh An
 * */
@Service
@Log4j2
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Login.
     *
     * @param userLogin LoginRequest
     * @return Response
     * */
    public Response login(LoginRequest userLogin) {
        logger.debug("user login: " + userLogin);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userLogin.username(), userLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        String token = authService.generateToken(authentication);
        Response response = new Response("User logged in successfully", token);
        return response;
    }

//    /**
//     * Register.
//     *
//     * */
//    @Bean
//    public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
//        return args -> {
//            User user = new User();
//            user.setUsername("Rolbin");
//            user.setEmail("rolbin@gmail.com");
//            user.setPassword(passwordEncoder.encode("123456"));
//
//            // Save the user to the database
//            userRepository.save(user);
//        };
//    }
}
