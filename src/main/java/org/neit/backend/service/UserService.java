package org.neit.backend.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.neit.backend.dto.request.UserCreateRequest;
import org.neit.backend.dto.request.UserHrCreationRequest;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.dto.response.UserResponse;
import org.neit.backend.entity.Company;
import org.neit.backend.entity.Role;
import org.neit.backend.entity.User;
import org.neit.backend.exception.AppException;
import org.neit.backend.exception.ErrorCode;
import org.neit.backend.mapper.ResultPaginationMapper;
import org.neit.backend.mapper.UserMapper;
import org.neit.backend.repository.CompanyRepository;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.repository.UserRepository;
import org.neit.backend.utils.TokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Value("${upload.path.user.avatar}")
    private String UPLOAD_PATH_USER_AVATAR;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ResultPaginationMapper resultPaginationMapper;
    private final CompanyRepository companyRepository;
    private final TokenInfo tokenInfo;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ResultPaginationMapper resultPaginationMapper, CompanyRepository companyRepository, TokenInfo tokenInfo) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.resultPaginationMapper = resultPaginationMapper;
        this.companyRepository = companyRepository;
        this.tokenInfo = tokenInfo;
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
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND)));
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("HR"));

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN') || hasRole('HR')" )
    public UserResponse update(UserHrCreationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setCompany(companyRepository.findByName(request.getCompany()).orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND)));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResultPaginationResponse getAll(Optional<String> page, Optional<String> pageSize) {
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<UserResponse> userPage = userRepository.findAll(pageable).map(userMapper::toUserResponse);

        return resultPaginationMapper.toResultPaginationResponse(userPage);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResultPaginationResponse getAllHr(String companyName, Optional<String> page, Optional<String> pageSize) {
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<UserResponse> userPage = userRepository
                .findByCompanyNameIgnoreCaseContaining(companyName, pageable)
                .map(userMapper::toUserResponse);

        return resultPaginationMapper.toResultPaginationResponse(userPage);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(user.getId());
    }
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN') || hasRole('HR')")
    public void uploadAvatar(MultipartFile file) throws IOException {
        String username = tokenInfo.getUsername();
        String uploadAvatarPath = UPLOAD_PATH_USER_AVATAR + username + "\\";

        File avatarFolder = new File(uploadAvatarPath);
        if (!avatarFolder.exists()){
            avatarFolder.mkdirs();
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(user.getAvatarPath() != null && !user.getAvatarPath().isEmpty()){
            FileUtils.cleanDirectory(avatarFolder);
        }

        File uploadFile = new File(uploadAvatarPath + file.getOriginalFilename());
        file.transferTo(uploadFile);

        if (!uploadFile.exists() || uploadFile.isDirectory()){
            throw new AppException(ErrorCode.CANNOT_UPLOAD_IMAGE);
        }

        user.setAvatarPath(username + "\\\\" +file.getOriginalFilename());
        userRepository.save(user);
    }

    public byte[] getAvatar(String username) throws IOException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        byte[] image = null;
        try {
            image = Files.readAllBytes(new File(UPLOAD_PATH_USER_AVATAR + user.getAvatarPath()).toPath());
        } catch (IOException e) {
            image = Files.readAllBytes(new File("src/main/resources/data/avatar-default.png").toPath());
        }

        return image;
    }
}
