package com.mephi.library.controller;

import com.mephi.library.model.Role;
import com.mephi.library.model.User;
import com.mephi.library.postRequestResponse.Data;
import com.mephi.library.postRequestResponse.response.UserInfo;
import com.mephi.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4000")
public class UserController {

    private final AdminService adminService;

    @Autowired
    public UserController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/getRole")
    public List<Role> getAllRole() {
        List<Role> role = adminService.FindAllRole();
        return role;
    }

    @GetMapping("/admin/getUsers")
    public List<User> getUsers() {
        List<User> users = adminService.FindAllUser();
        return users;
    }

    @RequestMapping(value = "/admin/addRole", method = RequestMethod.POST)
    public void addRole(@RequestBody Data data) {
        Role role = new Role();
        role.setName(data.getName());
        adminService.saveRole(role);
    }

    @RequestMapping(value = "/admin/updRole", method = RequestMethod.POST)
    public void updRole(@RequestBody Data data) {
        adminService.updateRoleName(data.getName(), data.getId());
    }

    @GetMapping("/usertest")
    @PreAuthorize("hasRole('USER')")
    public String moderatorAccess() {
        return "USER Board.";
    }

    @GetMapping("/admintest")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "ADMIN Board.";
    }
}
