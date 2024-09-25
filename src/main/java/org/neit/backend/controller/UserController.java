package org.neit.backend.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.UserCreateRequest;
import org.neit.backend.dto.request.UserHrCreationRequest;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.dto.response.UserResponse;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.service.AuthenticationService;
import org.neit.backend.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
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
    public ApiResponse<UserResponse> createUserHr(@RequestBody UserHrCreationRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.createUserHr(request));
        response.setMessage("Create user successful");
        return response;
    }
    @PutMapping
    public ApiResponse<UserResponse> update(@RequestBody UserHrCreationRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.update(request));
        return response;
    }
    @GetMapping("/search")
    public ApiResponse<ResultPaginationResponse> getAll(
            @RequestParam("page") Optional<String> page,
            @RequestParam("pageSize") Optional<String> pageSize
    ) {
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();

        response.setData(userService.getAll(page, pageSize));
        return response;
    }
    @GetMapping("/search/hr")
    public ApiResponse<ResultPaginationResponse> searchHr(
            @RequestParam("companyName") String companyName,
            @RequestParam("page") Optional<String> page,
            @RequestParam("pageSize") Optional<String> pageSize
    ){
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();
        response.setData(userService.getAllHr(companyName, page, pageSize));
        return response;
    }
    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam("username") String username) {
        ApiResponse<?> response = new ApiResponse<>();
        userService.delete(username);
        response.setMessage("Delete user successful");
        return response;
    }
    @PostMapping("/avatar")
    public ApiResponse<?> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadAvatar(file);
        return new ApiResponse<>();
    }
    @GetMapping("/avatar")
    public ResponseEntity<?> downloadAvatar(@RequestParam("username") String username) throws IOException {

        byte[] image = userService.getAvatar(username);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
