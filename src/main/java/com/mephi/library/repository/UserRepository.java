package com.mephi.library.repository;

import com.mephi.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name = ?1")
    User findByNameCustomQuery(String s);
}
