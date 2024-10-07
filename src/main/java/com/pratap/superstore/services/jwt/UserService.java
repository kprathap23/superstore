package com.pratap.superstore.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

   UserDetailsService userDetailsService();

}
