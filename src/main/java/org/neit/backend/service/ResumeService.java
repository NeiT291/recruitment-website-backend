package org.neit.backend.service;

import org.neit.backend.dto.request.ResumeRequest;
import org.neit.backend.dto.response.ResumeResponse;
import org.neit.backend.entity.Resume;
import org.neit.backend.entity.User;
import org.neit.backend.repository.CompanyRepository;
import org.neit.backend.repository.ResumeRepository;
import org.neit.backend.repository.UserRepository;
import org.neit.backend.utils.TokenInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private  final TokenInfo tokenInfo;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository, CompanyRepository companyRepository, TokenInfo tokenInfo) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.tokenInfo = tokenInfo;
    }

    public Resume create (ResumeRequest request){
        Resume resume = new Resume();
        Set<User> users = new HashSet<>();
        users.add(userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow());
        resume.setUser(users);
        resume.setCompany(companyRepository.findByName(request.getCompany()).orElseThrow());
        resume.setCreatedDate(LocalDate.now());

        return resumeRepository.save(resume);
    }
}
