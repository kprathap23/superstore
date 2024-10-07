package com.pratap.superstore.dto;

import com.pratap.superstore.UserRole;
import lombok.Data;

@Data
public class LoginResponse
{

    private String jwt;

    private Long userId;

    private UserRole userRole;


}
