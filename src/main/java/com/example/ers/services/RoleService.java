package com.example.ers.services;

import org.springframework.stereotype.Service;
import com.example.ers.models.Role;
import com.example.ers.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    //set empty role to employee
    public void setRoles() {
        if(roleRepository.findByName("employee").isEmpty()) {
            Role employeeRole = new Role();
            employeeRole.setRoleName("employee");
            roleRepository.save(employeeRole);
        }
    }


    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role does not exist"));
    }

}
