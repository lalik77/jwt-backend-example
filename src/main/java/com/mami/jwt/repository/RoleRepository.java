package com.mami.jwt.repository;

import com.mami.jwt.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,String> {
}
