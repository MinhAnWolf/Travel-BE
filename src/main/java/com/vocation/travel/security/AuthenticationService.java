package com.vocation.travel.security;

import com.vocation.travel.common.Log;
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
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private final static String SERVICE_NAME = "AuthenticationService";

    /**
     * Login.
     *
     * @param userLogin LoginRequest
     * @return Response
     * */
    public Response login(LoginRequest userLogin) {
        final String METHOD = "login";
        Log.startLog(SERVICE_NAME, METHOD);
        Log.inputLog(userLogin);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.username(), userLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        String idU = userDetails.getUser().getUserId();
        String atToken = tokenService.generateToken(authentication, idU, TimeConstant.minuteAt);
        String rfToken = tokenService.generateToken(authentication, idU, TimeConstant.minuteRf);
        Response response = new Response("User logged in successfully", atToken, rfToken, idU);
        Log.debugLog("Response: ", response);
        Log.endLog(SERVICE_NAME, METHOD);
        return response;
    }

    /**
     * Register.
     *
     * @param usersDto UsersDTO
     * @return Response
     * */
    public Response register(UsersDTO usersDto) {
        final String METHOD = "register";
        Log.startLog(SERVICE_NAME, METHOD);
        if (checkInputParams(usersDto) || checkExistEmailUserName(usersDto)) {
            return new Response("Register fail", null, null, null);
        }
        User user = new User();
        user.setInfoName(usersDto.getFullName());
        user.setAvatar(usersDto.getAvatar());
        user.setUsername(usersDto.getUsername());
        user.setEmail(usersDto.getEmail());
        user.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        userRepository.save(user);
        Response response = new Response("Register success", null, null, null);
        Log.debugLog("Response: ", response);
        Log.endLog(SERVICE_NAME, METHOD);
        return response;
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
