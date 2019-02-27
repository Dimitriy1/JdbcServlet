package jdbc.service;

import jdbc.dao.RoleDao;
import jdbc.model.Role;

import java.util.Set;

public class RoleServiceImpl implements RoleService {
    public RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public Set<Role> getRoles() {
        return roleDao.getRoles();
    }
}
