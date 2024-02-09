package com.ds.spring.p1.facade;

import com.ds.spring.p1.dtos.RoleDTO;

import java.util.List;

public interface RoleFacade {
    List<RoleDTO> findAll();
}
