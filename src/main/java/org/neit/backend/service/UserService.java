package org.neit.backend.service;

import org.neit.backend.dto.request.UserCreateRequest;
import org.neit.backend.dto.request.UserHrCreationRequest;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.dto.response.UserResponse;
import org.neit.backend.entity.Company;
import org.neit.backend.entity.Role;
import org.neit.backend.entity.User;
import org.neit.backend.mapper.ResultPaginationMapper;
import org.neit.backend.mapper.UserMapper;
import org.neit.backend.repository.CompanyRepository;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ResultPaginationMapper resultPaginationMapper;
    private final CompanyRepository companyRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ResultPaginationMapper resultPaginationMapper, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.resultPaginationMapper = resultPaginationMapper;
        this.companyRepository = companyRepository;
    }

    public UserResponse createUser(UserCreateRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public UserResponse createUserHr(UserHrCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setCompany(companyRepository.findByName(request.getCompany())
                .orElseThrow());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("HR"));

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN') || hasRole('HR')" )
    public UserResponse update(UserHrCreationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        user.setCompany(companyRepository.findByName(request.getCompany()).orElseThrow());
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResultPaginationResponse getAll(Optional<String> page, Optional<String> pageSize) {
        String sPage = page.orElse("");
        String sPageSize = pageSize.orElse("");

        Pageable pageable = PageRequest.of(Integer.parseInt(sPage) - 1, Integer.parseInt(sPageSize));
        Page<UserResponse> userPage = userRepository.findAll(pageable).map(userMapper::toUserResponse);

        return resultPaginationMapper.toResultPaginationResponse(userPage);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResultPaginationResponse getAllHr(String companyName, Optional<String> page, Optional<String> pageSize) {
        String sPage = page.orElse("");
        String sPageSize = pageSize.orElse("");

        Pageable pageable = PageRequest.of(Integer.parseInt(sPage) - 1, Integer.parseInt(sPageSize));
        Page<UserResponse> userPage = userRepository.findByCompanyNameIgnoreCaseContaining(companyName, pageable).map(userMapper::toUserResponse);
        return resultPaginationMapper.toResultPaginationResponse(userPage);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        userRepository.deleteById(user.getId());
    }
}
