package com.pratap.superstore.controllers;

import com.pratap.superstore.dto.LoginRequest;
import com.pratap.superstore.dto.LoginResponse;
import com.pratap.superstore.dto.SignUpRequest;
import com.pratap.superstore.dto.UserDTO;
import com.pratap.superstore.models.User;
import com.pratap.superstore.repository.UserRepository;
import com.pratap.superstore.services.auth.AuthService;
import com.pratap.superstore.services.jwt.UserService;
import com.pratap.superstore.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final JWTUtil jwtUtil;

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest)
    {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));


        }
        catch (BadCredentialsException e)
        {
            throw new BadCredentialsException("Incorrect email/password");
        }

       final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(loginRequest.getEmail());

        Optional<User> user = userRepository.findFirstByEmail(loginRequest.getEmail());

        final String jwtToken = jwtUtil.generateToken(userDetails);

        LoginResponse loginResponse= new LoginResponse();

        if(user.isPresent())
        {
            loginResponse.setJwt(jwtToken);
            loginResponse.setUserRole(user.get().getUserRole());
            loginResponse.setUserId(user.get().getId());
        }

        return loginResponse;


    }



    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        if (authService.hasUserWithEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists");

        } else {


            UserDTO userDTO = authService.signup(signUpRequest);

            if (userDTO == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            else
                return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

        }

    }





}
