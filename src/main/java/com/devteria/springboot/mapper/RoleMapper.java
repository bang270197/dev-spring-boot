package com.devteria.springboot.mapper;

import com.devteria.springboot.dto.request.RoleRequest;
import com.devteria.springboot.dto.response.RoleResponse;
import com.devteria.springboot.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
