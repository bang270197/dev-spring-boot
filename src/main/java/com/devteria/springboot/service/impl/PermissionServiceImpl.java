package com.devteria.springboot.service.impl;

import com.devteria.springboot.dto.request.PermissionRequest;
import com.devteria.springboot.dto.response.PermissionResponse;
import com.devteria.springboot.dto.response.UserDto;
import com.devteria.springboot.entity.Permission;
import com.devteria.springboot.entity.User;
import com.devteria.springboot.enums.ErrorCode;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.mapper.PermissionMapper;
import com.devteria.springboot.repository.PermissionRepository;
import com.devteria.springboot.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)

public class PermissionServiceImpl implements IPermissionService {
    private static final Logger log = LogManager.getLogger(PermissionServiceImpl.class);

    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permissionRepository.save(permission) ;

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        List<PermissionResponse> permissions = permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
        return permissions;
    }

    @Override
    public void delete(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn("Permission not found for delete with id: {}", name);
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                });
        permissionRepository.delete(permission);

    }
}
