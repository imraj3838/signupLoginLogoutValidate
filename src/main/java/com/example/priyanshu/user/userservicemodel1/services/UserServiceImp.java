package com.example.priyanshu.user.userservicemodel1.services;

import com.example.priyanshu.user.userservicemodel1.dtos.LoginRequestDto;
import com.example.priyanshu.user.userservicemodel1.models.Token;
import com.example.priyanshu.user.userservicemodel1.models.User;
import com.example.priyanshu.user.userservicemodel1.repositories.TokenRepository;
import com.example.priyanshu.user.userservicemodel1.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public UserServiceImp(BCryptPasswordEncoder bcryptPasswordEncoder, UserRepository userRepository,TokenRepository tokenRepository
    ) {
        this.tokenRepository = tokenRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userRepository = userRepository;
    }
    private LoginRequestDto loginRequestDto;


    @Override
    public User signUp(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashPassword(bcryptPasswordEncoder.encode(password));
        User us = userRepository.save(user);
        return us;
    }


    public Token login(String email, String password){

        // check first if this email is present in database
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return null;
        }
        User user = userOptional.get();
        if(!bcryptPasswordEncoder.matches(password, user.getHashPassword())){
            return null;
        }

        Token token = new Token();
        LocalDate currentDate = LocalDate.now();

        // Add 30 days to the current date
        LocalDate futureDate = currentDate.plusDays(30);

        // Convert LocalDate to Date
        Date futureDateAsDate = java.sql.Date.valueOf(futureDate);
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setExpiryAt(futureDateAsDate);
        Token t = tokenRepository.save(token);
        return t;
    }

    @Override
    public User validateUser(String token) {
        Optional<Token> tkn = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token,false,new Date());
        if(tkn.isEmpty()){
            return null;
        }
        return tkn.get().getUser();
    }

    public void logout(String token){
       Optional<Token> oken = tokenRepository.findByValueAndDeleted(token,false);
       if(oken.isEmpty()){
           // token deleted or token expired
       }
       Token tkn = oken.get();
       tkn.setDeleted(true);
       tokenRepository.save(tkn);
       return;

    }

}
