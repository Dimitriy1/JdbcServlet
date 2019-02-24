package jdbc.service;

import jdbc.dao.RoleDao;
import jdbc.model.Role;

public class RoleServiceImpl implements RoleService {
    public RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}
