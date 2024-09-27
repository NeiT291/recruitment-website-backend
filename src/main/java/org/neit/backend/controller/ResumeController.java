package org.neit.backend.controller;

import org.neit.backend.dto.ApiResponse;

import org.neit.backend.dto.request.ResumeRequest;
import org.neit.backend.dto.response.JobResponse;
import org.neit.backend.entity.Resume;
import org.neit.backend.service.ResumeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @PostMapping
    public ApiResponse<Resume> create(@RequestBody ResumeRequest request) {
        ApiResponse<Resume> response = new ApiResponse<>();
        response.setData(resumeService.create(request));
        return response;
    }
}
