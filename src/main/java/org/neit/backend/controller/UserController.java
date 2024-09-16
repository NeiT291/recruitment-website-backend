package org.neit.backend.controller;


import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.LoginRequest;
import org.neit.backend.dto.request.UserCreateRequest;
import org.neit.backend.dto.response.AuthenticationResponse;
import org.neit.backend.dto.response.UserResponse;
import org.neit.backend.entity.Role;
import org.neit.backend.entity.User;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.service.AuthenticationService;
import org.neit.backend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;
    UserService userService;

    public UserController(UserService userService, RoleRepository roleRepository, AuthenticationService authenticationService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.createUser(request));
        response.setMessage("Create user successful");
        return response;
    }
    @PostMapping("/hr")
    public ApiResponse<UserResponse> createUserHr(@RequestBody UserCreateRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.createUserHr(request));
        response.setMessage("Create user successful");
        return response;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();

        response.setData(userService.getAllUsers());
        return response;
    }

}
