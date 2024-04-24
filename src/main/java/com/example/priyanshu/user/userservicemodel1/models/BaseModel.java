package com.example.priyanshu.user.userservicemodel1.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private boolean deleted;
}
