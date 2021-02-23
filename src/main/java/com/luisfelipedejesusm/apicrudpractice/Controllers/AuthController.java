package com.luisfelipedejesusm.apicrudpractice.Controllers;

import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.LoginRequest;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.SignupRequest;
import com.luisfelipedejesusm.apicrudpractice.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> AuthenticateUser(@Valid @RequestBody LoginRequest login){
        return authService.authenticateUser(login);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        return authService.registerUser(signupRequest);
    }
}
