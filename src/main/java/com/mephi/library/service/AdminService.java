package com.mephi.library.service;

import com.mephi.library.model.Role;
import com.mephi.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final RoleRepository roleRepository;

    @Autowired
    public AdminService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> FindAllRole() {
        return roleRepository.findAll();
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public void updateRoleName(String nameRole, Long id) {
        roleRepository.updRoleName(nameRole, id);
    }


}
