package com.mephi.library.service;

import com.mephi.library.model.Author;
import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import com.mephi.library.postRequestResponse.DB.BookDB;
//import com.mephi.library.repository.BookDataRepository;
import com.mephi.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class BookService {
    private final BookRepository bookRepository;
//    private final BookDataRepository bookDataRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
//        this.bookDataRepository = bookDataRepository;
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

    public BookDB getBookDataById(Long id) {
        return bookRepository.findBookByIdCustomQuery(id);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    public boolean imageIsExist(Long idBook) {
        byte data = bookRepository.findBookImageByIdBook(idBook);
        return false;
    }

    public Long countBookByGenre(Genre genre) {
        return bookRepository.countBookByGenre(genre);
    }

    public Long countBookByAuthor(Author author) {
        return bookRepository.countBookByAuthor(author);
    }
}
