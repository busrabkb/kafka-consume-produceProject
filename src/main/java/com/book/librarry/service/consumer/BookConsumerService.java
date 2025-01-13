package com.book.librarry.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class BookConsumerService {

    @KafkaListener(topics = "books-topic", groupId = "book-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}

