package com.mephi.library.service;

import com.mephi.library.model.Role;
import com.mephi.library.model.User;
import com.mephi.library.postRequestResponse.response.UserInfo;
import com.mephi.library.repository.RoleRepository;
import com.mephi.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<Role> FindAllRole() {
        return roleRepository.findAll();
    }

    public List<User> FindAllUser() {
        return userRepository.findAll();
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public void updateRoleName(String nameRole, Long id) {
        roleRepository.updRoleName(nameRole, id);
    }

    public Role getUserRoleByName(String name) {
        return roleRepository.findByNameCustomQuery(name);
    }


}
