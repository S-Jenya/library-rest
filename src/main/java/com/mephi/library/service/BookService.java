package com.mephi.library.service;

import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import com.mephi.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public Stream<Book> getAllBook() {
        return bookRepository.findAll().stream();
    }

    public Book getFile(Long id) {
        return bookRepository.findById(id).get();
    }

    public Long countBookByGenre(Genre genre) {
        return bookRepository.countBookByGenre(genre);
    }
}
