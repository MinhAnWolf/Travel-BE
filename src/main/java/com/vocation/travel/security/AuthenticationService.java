package com.vocation.travel.security;

import com.vocation.travel.common.constant.TimeConstant;
import com.vocation.travel.dto.AuthDTO.LoginRequest;
import com.vocation.travel.dto.AuthDTO.Response;
import com.vocation.travel.dto.UsersDTO;
import com.vocation.travel.entity.User;
import com.vocation.travel.model.AuthUser;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.util.Utils;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Login service.
 *
 * @author Minh An
 * */
@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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
        String idU = userDetails.getUser().getUserId();
        String atToken = tokenService.generateToken(authentication, idU, TimeConstant.minuteAt);
        String rfToken = tokenService.generateToken(authentication, idU, TimeConstant.minuteRf);
        return new Response("User logged in successfully", atToken, rfToken, idU);
    }

    /**
     * Register.
     *
     * @param usersDto UsersDTO
     * @return Response
     * */
    public Response register(UsersDTO usersDto) {
        if (checkInputParams(usersDto) || checkExistEmailUserName(usersDto)) {
            return new Response("Register fail", null, null, null);
        }
        User user = new User();
        user.setUsername(usersDto.getUsername());
        user.setEmail(usersDto.getEmail());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        userRepository.save(user);
        return new Response("Register success", null, null, null);
    }

    /**
     * check exist email and username.
     *
     * @param usersDto UsersDTO
     * @return boolean
     * */
    private Boolean checkExistEmailUserName(UsersDTO usersDto) {
        Optional<User> userByUserName = userRepository.findByUsername(usersDto.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(usersDto.getEmail());

      return userByUserName.isPresent() && userByEmail.isPresent();
    }

    /**
     * Check input params.
     *
     * @param usersDto UsersDTO
     * @return boolean
     * */
    private boolean checkInputParams(UsersDTO usersDto) {
      return Objects.isNull(usersDto) || Utils.isEmpty(usersDto.getUsername())
          || Utils.isEmpty(usersDto.getEmail()) || Utils.isEmpty(usersDto.getPassword());
    }
}
