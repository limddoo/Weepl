package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.Chatbot;

public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {

}
