package com.example.priyanshu.user.userservicemodel1.services;

import com.example.priyanshu.user.userservicemodel1.models.Token;
import com.example.priyanshu.user.userservicemodel1.models.User;

public interface UserService {

    public Token login(String email, String password);

    public User validateUser(String token);

    User signUp(String email, String password, String name);

    void logout(String lue);


}
