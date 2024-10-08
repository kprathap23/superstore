package com.pratap.superstore.dto;

import com.pratap.superstore.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {


    private Long id;

    private String name;

    private String email;

    private UserRole userRole;



}
