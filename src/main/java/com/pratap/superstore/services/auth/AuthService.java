package com.pratap.superstore.services.auth;

import com.pratap.superstore.dto.SignUpRequest;
import com.pratap.superstore.dto.UserDTO;

public interface AuthService {



   UserDTO signup(SignUpRequest signUpRequest);


    Boolean hasUserWithEmail(String email);



}
