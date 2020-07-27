package com.project.internetshop.service.impl;

import com.project.internetshop.dao.RoleDao;
import com.project.internetshop.lib.Inject;
import com.project.internetshop.lib.Service;
import com.project.internetshop.model.Role;
import com.project.internetshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Inject
    private RoleDao roleDao;

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
    }
}
