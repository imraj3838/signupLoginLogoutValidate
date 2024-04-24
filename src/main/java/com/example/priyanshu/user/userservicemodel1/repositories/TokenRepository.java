package com.example.priyanshu.user.userservicemodel1.repositories;

import com.example.priyanshu.user.userservicemodel1.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
     Token save(Token token);

      Optional<Token> findByValueAndDeleted(String value, boolean isDeleted);

    Optional<Token> findByValueAndDeletedEqualsAndExpiryAtGreaterThan(String token, boolean deleted, Date date);
}
