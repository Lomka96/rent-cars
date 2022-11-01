package com.upskill.rentcars.controller;

import com.upskill.rentcars.config.jwt.JwtProvider;
import com.upskill.rentcars.model.db.Role;
import com.upskill.rentcars.model.dto.AuthRequest;
import com.upskill.rentcars.model.dto.AuthData;
import com.upskill.rentcars.model.dto.RegistrationRequest;
import com.upskill.rentcars.model.db.User;
import com.upskill.rentcars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFirstName(registrationRequest.getName());
        user.setLastName(registrationRequest.getSurname());
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthData auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        String userLogin = jwtProvider.getLoginFromToken(token);
        Role role = userService.findByLogin(userLogin).getRole();
        return new AuthData(token, role);
    }
}
