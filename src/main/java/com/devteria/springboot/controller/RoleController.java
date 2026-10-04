package com.devteria.springboot.controller;

import com.devteria.springboot.common.BaseController;
import com.devteria.springboot.dto.request.PermissionRequest;
import com.devteria.springboot.dto.request.RoleRequest;
import com.devteria.springboot.dto.response.ApiResponse;
import com.devteria.springboot.dto.response.PermissionResponse;
import com.devteria.springboot.dto.response.RoleResponse;
import com.devteria.springboot.service.IPermissionService;
import com.devteria.springboot.service.IRoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Slf4j
public class RoleController extends BaseController {
    IRoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>>
    createRole(@Valid @RequestBody RoleRequest request) {
        return created(roleService.create(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();

        String authorities = authentication.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        log.info("==> User đang thao tác: {}", userName);
        log.info("==> Các quyền (Roles) được cấp: [{}]", authorities);

        return success(roleService.getAll());
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String roleName) {
        roleService.delete(roleName);
        return noContent();
    }
}
