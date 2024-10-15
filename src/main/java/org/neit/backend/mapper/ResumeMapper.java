package org.neit.backend.mapper;

import org.neit.backend.dto.response.ResumeResponse;
import org.neit.backend.entity.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapper {

    private final UserMapper userMapper;

    public ResumeMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ResumeResponse toResumeResponse(Resume resume) {
        ResumeResponse response = new ResumeResponse();
        response.setId(resume.getId());
        response.setUserCreated(userMapper.toUserResponse(resume.getUser()));
        response.setJob_name(resume.getJob().getName());
        response.setCompany(resume.getJob().getCompany().getName());
        response.setCreatedDate(resume.getCreatedDate());
        return response;
    }
}
