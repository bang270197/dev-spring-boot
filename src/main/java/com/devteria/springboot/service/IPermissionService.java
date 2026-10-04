package com.devteria.springboot.service;

import com.devteria.springboot.dto.request.PermissionRequest;
import com.devteria.springboot.dto.response.PermissionResponse;
import com.devteria.springboot.repository.PermissionRepository;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String name);
}
