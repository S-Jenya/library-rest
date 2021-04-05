package com.mephi.library.repository;

import com.mephi.library.model.User;
import com.mephi.library.postRequestResponse.response.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.login = ?1")
    User findByNameCustomQuery(String s);

    @Query("select u.idUser, u.login, u.name, u.role from User u")
    List<UserInfo> findAllUserCustomQuery();

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
