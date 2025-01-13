package com.book.librarry.controller;

import com.book.librarry.dto.Book;
import com.book.librarry.service.BookProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookProducerService bookProducerService;


    public BookController(BookProducerService bookProducerService) {
        this.bookProducerService = bookProducerService;
    }

    @PostMapping("/publish")
    public String publishBook(@RequestBody Book book) {
      return   bookProducerService.sendBook( book);

    }
}
