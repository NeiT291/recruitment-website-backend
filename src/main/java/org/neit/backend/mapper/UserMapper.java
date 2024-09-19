package org.neit.backend.mapper;

import org.neit.backend.dto.request.UserCreateRequest;
import org.neit.backend.dto.request.UserHrCreationRequest;
import org.neit.backend.dto.response.UserResponse;
import org.neit.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserCreateRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullname(request.getFullname());
        user.setDob(request.getDob());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        return user;
    }
    public User toUser(UserHrCreationRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullname(request.getFullname());
        user.setDob(request.getDob());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        return user;
    }
    public UserResponse toUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setFullname(user.getFullname());
        response.setDob(user.getDob());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setRoles(user.getRoles());
        response.setCompanny(user.getCompany());
        return response;
    }
    public void updateUser(User user, UserHrCreationRequest request){
        user.setFullname(request.getFullname());
        user.setDob(request.getDob());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
    }
}
