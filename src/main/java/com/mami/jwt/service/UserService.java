package com.mami.jwt.service;

import com.mami.jwt.entity.AppUser;
import com.mami.jwt.entity.Role;
import com.mami.jwt.repository.RoleRepository;
import com.mami.jwt.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser registerNewUser(AppUser user) {

        Role role = roleRepository.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void initRolesAndUsers() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);

        AppUser adminAppUser = new AppUser();
        adminAppUser.setUserName("admin77");
        adminAppUser.setUserFirstName("admin");
        adminAppUser.setUserLastName("admin");
        adminAppUser.setUserPassword(getEncodedPassword("admin@password77"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminAppUser.setRole(adminRoles);
        userRepository.save(adminAppUser);

//        AppUser appUser = new AppUser();
//        appUser.setUserName("lalik77");
//        appUser.setUserFirstName("Alex");
//        appUser.setUserLastName("Mami");
//        appUser.setUserPassword(getEncodedPassword("user@password77"));
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        appUser.setRole(userRoles);
//        userRepository.save(appUser);
    }
}
