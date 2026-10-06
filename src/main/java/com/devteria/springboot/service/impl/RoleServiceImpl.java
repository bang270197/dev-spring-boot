package com.devteria.springboot.service.impl;

import com.devteria.springboot.dto.request.PermissionRequest;
import com.devteria.springboot.dto.request.RoleRequest;
import com.devteria.springboot.dto.response.PermissionResponse;
import com.devteria.springboot.dto.response.RoleResponse;
import com.devteria.springboot.entity.Permission;
import com.devteria.springboot.entity.Role;
import com.devteria.springboot.enums.ErrorCode;
import com.devteria.springboot.exception.ResourceNotFoundException;
import com.devteria.springboot.mapper.PermissionMapper;
import com.devteria.springboot.mapper.RoleMapper;
import com.devteria.springboot.repository.PermissionRepository;
import com.devteria.springboot.repository.RoleRepository;
import com.devteria.springboot.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class RoleServiceImpl implements IRoleService {
    private static final Logger log = LogManager.getLogger(RoleServiceImpl.class);
    private final PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    RoleRepository roleRepository;

    @Override
    public RoleResponse create(RoleRequest request) {
        log.info("Creating role with name: {}", request.getName());
        Role role = roleMapper.toRole(request);

        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());

            // (Tùy chọn) Kiểm tra xem có quyền nào không tồn tại trong DB hay không
            if (permissions.size() != request.getPermissions().size()) {
                log.warn("Role creation failed: one or more requested permissions do not exist for role: {}", request.getName());
                throw new RuntimeException("One or more permissions do not exist!");
            }

            role.setPermissions(new HashSet<>(permissions));
        }

        roleRepository.save(role) ;

        log.info("Role created with name: {}", role.getName());
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        List<RoleResponse> permissions = roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
        log.info("Fetched {} roles", permissions.size());
        return permissions;
    }

    @Override
    public void delete(String name) {
        log.info("Deleting role with name: {}", name);
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn("Role not found for deletion with name: {}", name);
                    return new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND);
                });
        roleRepository.delete(role);
        log.info("Role deleted with name: {}", name);

    }
}
