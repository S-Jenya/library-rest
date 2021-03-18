package com.mephi.library.repository;

import com.mephi.library.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Genre g set g.name = ?1 where g.idGenre = ?2")
    void updGenreName(String genreName, Long genreId);

    @Query("select g from Genre g where g.idGenre = ?1")
    Genre findGenreByIdCustomQuery(Long idGenre);
}
