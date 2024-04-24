package com.example.priyanshu.user.userservicemodel1.controllers;

import com.example.priyanshu.user.userservicemodel1.dtos.LoginRequestDto;
import com.example.priyanshu.user.userservicemodel1.dtos.LogoutRequestDto;
import com.example.priyanshu.user.userservicemodel1.dtos.SignUpRequestDto;
import com.example.priyanshu.user.userservicemodel1.models.Token;
import com.example.priyanshu.user.userservicemodel1.models.User;
import com.example.priyanshu.user.userservicemodel1.services.UserService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/User")
public class userController {

    @Autowired
    private UserService userService;

// Initially this was done by me it successfully sent the data to the database but cannot sent the password
    //the problem was i had to use logindto class instead of user class directly
//    @GetMapping("/login")
//    public User login(@RequestBody User user) {
//        return userService.getUserByEmailAndPassword(user.getEmail(),user.getHashPassword());
//    }

//     here i have made as naman bhalla sir used the signupdto class
    @PostMapping("/save")
    public User signUp( @RequestBody SignUpRequestDto signUpRequestDto){
        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();
        String name = signUpRequestDto.getName();
        return userService.signUp(email, password, name);
    }


    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Token savedtoken = userService.login(email,password);
        return savedtoken;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto){
        String ss = requestDto.getToken();
        userService.logout(ss);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable @NotNull String token){
        return userService.validateUser(token);
    }
}
