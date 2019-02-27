package jdbc.service;

import jdbc.model.Role;

import java.util.Set;

public interface RoleService {
    void addRole(Role role);

    Set<Role> getRoles();
}
