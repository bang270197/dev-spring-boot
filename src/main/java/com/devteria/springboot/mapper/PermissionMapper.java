package com.devteria.springboot.mapper;

import com.devteria.springboot.dto.request.PermissionRequest;
import com.devteria.springboot.dto.request.UserCreateRequest;
import com.devteria.springboot.dto.response.PermissionResponse;
import com.devteria.springboot.entity.Permission;
import com.devteria.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
