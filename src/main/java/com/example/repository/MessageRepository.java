package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("DELETE FROM Message WHERE messageId = :messageIdVar")
    int deleteMessageByMessageId(@Param("messageIdVar") Integer messageId);
}
