package com.book.librarry.service;

import com.book.librarry.dto.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    public BookProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendBook(Book book) {
        kafkaTemplate.send(topic, book.toString());
        System.out.println("Produced book: " + book);
        return "\"Produced book: \" + book";
    }
}

