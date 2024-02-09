package com.ds.spring.p1.facade.impl;

import com.ds.spring.p1.dtos.RoleDTO;
import com.ds.spring.p1.facade.RoleFacade;
import com.ds.spring.p1.security.services.RoleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultRoleFacade implements RoleFacade {

    private final RoleService roleService;

    public DefaultRoleFacade(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleService.findAll();
    }
}
