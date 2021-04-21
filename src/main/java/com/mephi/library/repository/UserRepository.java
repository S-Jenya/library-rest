package com.mephi.library.repository;

import com.mephi.library.model.User;
import com.mephi.library.postRequestResponse.response.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String s);
    User findUserByEmail(String s);
    Long countUserByName(String name);
    Long countUserByEmail(String email);


    @Query("select u from User u where u.login = ?1")
    User findUserByLoginCustomQuery(String name);

    @Query("select u from User u where u.email = ?1")
    User findUserByEmailCustomQuery(String email);

    @Query("SELECT u FROM User u JOIN u.books b WHERE b.idBook = ?1")
    List<User> findListUserBooks(Long idBook);

    @Query(value = "select u.*\n" +
            "from (\n" +
            "         select c.*\n" +
            "         from book b\n" +
            "                  join comment c on b.id_book = c.id_book\n" +
            "         where b.id_book = ?1\n" +
            "     ) as userComm,\n" +
            "     my_user as u\n" +
            "where userComm.id_user = u.id_user",
            nativeQuery = true)
    List<User> findListUserComments(Long idBook);
}
