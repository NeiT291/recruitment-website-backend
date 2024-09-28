package org.neit.backend.service;

import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.dto.response.ResumeResponse;
import org.neit.backend.entity.*;
import org.neit.backend.exception.AppException;
import org.neit.backend.exception.ErrorCode;
import org.neit.backend.mapper.ResultPaginationMapper;
import org.neit.backend.mapper.ResumeMapper;
import org.neit.backend.repository.*;
import org.neit.backend.utils.FileUtils;
import org.neit.backend.utils.TokenInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final ResumeMapper resumeMapper;
    private  final TokenInfo tokenInfo;
    private final JobRepository jobRepository;
    private final FileUtils fileUtils;
    private final ResultPaginationMapper resultPaginationMapper;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    @Value("${upload.path.user.resume}")
    private String UPLOAD_RESUME_PATH;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository, ResumeMapper resumeMapper, TokenInfo tokenInfo, JobRepository jobRepository, FileUtils fileUtils, ResultPaginationMapper resultPaginationMapper, RoleRepository roleRepository, CompanyRepository companyRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.resumeMapper = resumeMapper;
        this.tokenInfo = tokenInfo;
        this.jobRepository = jobRepository;
        this.fileUtils = fileUtils;
        this.resultPaginationMapper = resultPaginationMapper;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }

    public ResumeResponse create(String job_id, MultipartFile file) throws IOException {
        Resume resume = new Resume();
        Job job = jobRepository.findById(Integer.parseInt(job_id)).orElseThrow(() ->
                new AppException(ErrorCode.JOB_NOT_FOUND));
        if(job.getDeadline().isAfter(resume.getCreatedDate())){
            throw new AppException(ErrorCode.DEADLINE_EXPIRED);
        }
        User user = userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_FOUND));

        resume.setUser(user);
        resume.setJob(job);

        String resumePath = resume.getUser().getUsername() + "\\\\" + resume.getJob().getId() + "\\\\";
        fileUtils.uploadFile(file, UPLOAD_RESUME_PATH + resumePath);

        resume.setResumePath(resumePath + file.getOriginalFilename());

        return resumeMapper.toResumeResponse(resumeRepository.save(resume));
    }
    public ResultPaginationResponse getMyResumes(Optional<String> page, Optional<String> pageSize) {
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<ResumeResponse> userPage = resumeRepository
                .findAllByUser_Username(tokenInfo.getUsername(), pageable)
                .map(resumeMapper::toResumeResponse);
        return resultPaginationMapper.toResultPaginationResponse(userPage);
    }
    @PreAuthorize("hasRole('HR')")
    public ResultPaginationResponse hrGetResumes(Optional<String> page, Optional<String> pageSize) {
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        User user = userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_FOUND));
        Page<Resume> resumePage = resumeRepository.findAllByJob_Company(user.getCompany(),pageable);
        return resultPaginationMapper.toResultPaginationResponse(resumePage.map(resumeMapper::toResumeResponse));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResultPaginationResponse getAll(Optional<String> page, Optional<String> pageSize) {
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<Resume> resumePage = resumeRepository.findAll(pageable);
        return resultPaginationMapper.toResultPaginationResponse(resumePage.map(resumeMapper::toResumeResponse));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ResultPaginationResponse getAllByCompany(String company, Optional<String> page, Optional<String> pageSize) {

        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<Resume> resumePage = resumeRepository.findAllByJob_Company(companyRepository.findByName(company)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_FOUND)), pageable);
        return resultPaginationMapper.toResultPaginationResponse(resumePage.map(resumeMapper::toResumeResponse));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'HR','USER')")
    public byte[] getResume(Integer id) {

        Resume resume = resumeRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.RESUME_NOT_FOUND));
        User user = userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_FOUND));

        Role hrRole = roleRepository.findByName("HR");
        Role userRole = roleRepository.findByName("USER");

        if(user.getRoles().contains(hrRole)){
            if(resume.getJob().getCompany() != user.getCompany()){
                throw new AppException(ErrorCode.ACCESS_DENIED);
            }
        }
        if(user.getRoles().contains(userRole)){
            if(resume.getUser() != user){
                throw new AppException(ErrorCode.ACCESS_DENIED);
            }
        }
        byte[] resumeFile = null;
        try {
            resumeFile = Files.readAllBytes(new File(UPLOAD_RESUME_PATH + resume.getResumePath()).toPath());
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }
        return resumeFile;
    }

}
