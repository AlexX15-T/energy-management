package com.ds.spring.p1.security.services;

import com.ds.spring.p1.dtos.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAll();
}
