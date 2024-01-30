package com.mami.jwt.service;

import com.mami.jwt.entity.Role;
import com.mami.jwt.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
