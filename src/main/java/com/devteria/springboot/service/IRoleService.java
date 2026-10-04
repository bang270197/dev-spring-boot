package com.devteria.springboot.service;

import com.devteria.springboot.dto.request.PermissionRequest;
import com.devteria.springboot.dto.request.RoleRequest;
import com.devteria.springboot.dto.response.PermissionResponse;
import com.devteria.springboot.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    void delete(String name);
}
