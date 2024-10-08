package com.pratap.superstore.services.auth;

import com.pratap.superstore.enums.UserRole;
import com.pratap.superstore.dto.SignUpRequest;
import com.pratap.superstore.dto.UserDTO;
import com.pratap.superstore.models.User;
import com.pratap.superstore.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;


    @PostConstruct
    public void createAnAdminAccount() {

        Optional<User> userAdmin = userRepository.findByUserRole(UserRole.ADMIN);

        if (userAdmin.isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("Admin@test.com");
            adminUser.setUserRole(UserRole.ADMIN);
            adminUser.setPassword(new BCryptPasswordEncoder().encode("Admin"));

            userRepository.save(adminUser);

            System.out.println("Admin user created");

        }
        else
        {
            System.out.println("Admin user already exists");

        }


    }

    @Override
    public UserDTO signup(SignUpRequest signUpRequest) {

        User newuser = new User();
        newuser.setName(signUpRequest.getName());
        newuser.setEmail(signUpRequest.getEmail());
        newuser.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        newuser.setUserRole(UserRole.CUSTOMER);

        return userRepository.save(newuser).getUserDTO();


    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }


}
