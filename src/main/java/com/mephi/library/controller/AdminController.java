package com.mephi.library.controller;

import com.mephi.library.model.Role;
import com.mephi.library.postRequest.Data;
import com.mephi.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/getRole")
    public List<Role> getAllRole() {
        List<Role> role = adminService.FindAllRole();
        return role;
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
}
