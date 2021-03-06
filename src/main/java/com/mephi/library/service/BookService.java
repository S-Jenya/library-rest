package com.mephi.library.service;

import com.mephi.library.model.Author;
import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import com.mephi.library.postRequestResponse.DB.BookDB;
//import com.mephi.library.repository.BookDataRepository;
import com.mephi.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;
//    private final BookDataRepository bookDataRepository;

    public BookService(BookRepository bookRepository, GenreService genreService) {
        this.bookRepository = bookRepository;
//        this.bookDataRepository = bookDataRepository;
        this.genreService = genreService;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void delBook(Book book) {
        bookRepository.delete(book);
    }

    public Stream<Book> getAllBook() {
        return bookRepository.findAll().stream();
    }

    public Book getFile(Long id) {
        return bookRepository.findById(id).get();
    }

    public BookDB getBookFoCards(Long id) {
        return bookRepository.findBookByIdCustomQuery(id);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    public boolean imageIsExist(Long idBook) {
        byte data = bookRepository.findBookImageByIdBook(idBook);
        return false;
    }

    public List<Book> getBookByGenre(Genre genre) {
        return bookRepository.findBookByGenre(genre);
    }

    public List<Book> getBookByAuthor(Author author) {
        return bookRepository.findBookByAuthor(author);
    }

    public Long countBookByGenre(Genre genre) {
        return bookRepository.countBookByGenre(genre);
    }

    public Stream<Book> FilterBook(String mode, String strSearch) {
        Stream<Book> books = null;
        if(mode.equals("all")) {
            books = bookRepository.findAll().stream();
        }
        if(mode.equals("byName")) {
            books = bookRepository.findBookByNameContaining(strSearch).stream();
        } else if (mode.equals("byGenreName")) {
            Genre genre = genreService.findGenreByName(strSearch);
            books = bookRepository.findBookByGenre(genre).stream();
        }
        return books;
    }

    public Long countBookByAuthor(Author author) {
        return bookRepository.countBookByAuthor(author);
    }
}
