package com.mephi.library.service;

import com.mephi.library.model.User;
import com.mephi.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByName(String name){
        return userRepository.findByNameCustomQuery(name);
    }

    public User findUserById(Long idUser) {
        return  userRepository.findById(idUser).get();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
}
