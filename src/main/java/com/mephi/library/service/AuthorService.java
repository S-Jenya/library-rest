package com.mephi.library.service;

import com.mephi.library.model.Author;
import com.mephi.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void updateAuthorName(Long idAuthor, String firstName, String lastName, String patronymic){
        authorRepository.updAuthorName(idAuthor, firstName, lastName, patronymic);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long idAuthor) {
        return authorRepository.getAuthorByIdAuthor(idAuthor);
    }
}

