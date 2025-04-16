package com.example.repository;

import java.util.List;
import java.util.Optional;
import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("FROM Account WHERE username = :usernameVar AND password = :passwordVar")
    Optional<Account> findByUsernameAndPassword(@Param("usernameVar") String username, @Param("passwordVar") String password);

    @Query("FROM Message WHERE postedBy = :accountIdVar")
    List<Message> findAllMessagesByAccountId(@Param("accountIdVar") Integer accountId);
}

