package com.mephi.library.controller;

import com.mephi.library.model.Role;
import com.mephi.library.model.User;
import com.mephi.library.postRequest.UserRegistration;
import com.mephi.library.service.AdminService;
import com.mephi.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminService adminService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, AdminService adminService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
    }

    @RequestMapping(value="/registration/createUser", method= RequestMethod.POST)
    public void createUser(@RequestBody UserRegistration data){
        try {
            User user = new User();
            Role role = adminService.getUserRoleByName("USER");
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            user.setRole(role);
            userService.createUser(user);
            System.out.println("Пользователь создан");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
