package com.luisfelipedejesusm.apicrudpractice.Services;

import com.luisfelipedejesusm.apicrudpractice.Enums.ERole;
import com.luisfelipedejesusm.apicrudpractice.Models.Role;
import com.luisfelipedejesusm.apicrudpractice.Models.User;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.LoginRequest;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.SignupRequest;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Response.JwtResponse;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Response.MessageResponse;
import com.luisfelipedejesusm.apicrudpractice.Repositories.RoleRepository;
import com.luisfelipedejesusm.apicrudpractice.Repositories.UserRepository;
import com.luisfelipedejesusm.apicrudpractice.Security.Jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<?> authenticateUser(LoginRequest login) {
        // Create an Authentication Object using the data provided by the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );
        // Login the user if found
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Create a Jwt Token
        String jwt = jwtUtils.generateJwtToken(authentication);
        // Get authenticated user roles
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        ));
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body( new MessageResponse("Error: Username already exist") );
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body( new MessageResponse("Error: Email already in use"));
        }
        // Create new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(
                roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()->
                        new RuntimeException("Role not found"))
        );
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
    }
}
