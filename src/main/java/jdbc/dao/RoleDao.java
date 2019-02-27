package jdbc.dao;

import jdbc.model.Role;

import java.util.Set;

public interface RoleDao {
    void addRole(Role role);

    Set<Role> getRoles();
}
