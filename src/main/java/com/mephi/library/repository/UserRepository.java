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

    /*@Query("SELECT s.id " +
            "FROM Product p " +
            "JOIN p.suppliers s " +
            "WHERE p.id = ?1")
    List<Long> findListSupplierIdByProductId(Long ProductId);*/
}
