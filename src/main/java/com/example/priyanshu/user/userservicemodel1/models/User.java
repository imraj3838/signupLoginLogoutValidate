package com.example.priyanshu.user.userservicemodel1.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel {

    private String name;
    private String email;
    private String hashPassword;
    @ManyToMany
    private List<Role> roles;
    private boolean isVerified;
}
