package org.neit.backend.controller;


import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.UserCreateRequest;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.dto.response.UserResponse;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.service.AuthenticationService;
import org.neit.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

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
    @PutMapping
    public ApiResponse<UserResponse> update(@RequestBody UserCreateRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.update(request));
        return response;
    }
    @GetMapping
    public ApiResponse<ResultPaginationResponse> getAll(
            @RequestParam("page") Optional<String> page,
            @RequestParam("pageSize") Optional<String> pageSize
    ) {
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();

        response.setData(userService.getAll(page, pageSize));
        return response;
    }

}
