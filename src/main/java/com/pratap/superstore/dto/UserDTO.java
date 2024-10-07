package com.pratap.superstore.dto;

import com.pratap.superstore.UserRole;
import lombok.Data;

@Data
public class UserDTO {


    private Long id;

    private String name;

    private String email;

    private UserRole userRole;



}
