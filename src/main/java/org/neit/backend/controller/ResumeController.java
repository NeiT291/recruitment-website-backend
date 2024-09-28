package org.neit.backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.neit.backend.dto.ApiResponse;

import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.dto.response.ResumeResponse;
import org.neit.backend.service.ResumeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/resumes")
@SecurityRequirement(name = "bearerAuth")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @PostMapping
    public ApiResponse<ResumeResponse> create(@RequestParam String job_id,
                                              @RequestParam MultipartFile file) throws IOException {
        ApiResponse<ResumeResponse> response = new ApiResponse<>();
        response.setData(resumeService.create(job_id, file));
        return response;
    }
    @GetMapping("/my-resumes")
    private ApiResponse<ResultPaginationResponse> myResumes(@RequestParam Optional<String> page,@RequestParam Optional<String> pageSize){
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();
        response.setData(resumeService.getMyResumes(page, pageSize));
        return response;
    }
    @GetMapping("/download")
    public ResponseEntity<?> downloadResume(@RequestParam Integer id) throws IOException {

        byte[] image = resumeService.getResume(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(image);
    }
    @GetMapping
    public ApiResponse<ResultPaginationResponse> getAllResumes(@RequestParam(required = false) String company,
                                                               @RequestParam Optional<String> page,
                                                               @RequestParam Optional<String> pageSize){
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();
        if (company != null) {
            response.setData(resumeService.getAllByCompany(company, page, pageSize));
            return response;
        }
        response.setData(resumeService.getAll(page, pageSize));
        return response;
    }
    @GetMapping("/hr")
    public ApiResponse<ResultPaginationResponse> hrGetResumes(@RequestParam Optional<String> page,
                                                               @RequestParam Optional<String> pageSize){
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();

        response.setData(resumeService.hrGetResumes(page, pageSize));
        return response;
    }
}
