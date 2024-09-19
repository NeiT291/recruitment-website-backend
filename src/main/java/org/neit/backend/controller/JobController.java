package org.neit.backend.controller;

import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.JobCreateRequest;
import org.neit.backend.dto.response.JobResponse;
import org.neit.backend.service.JobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ApiResponse<JobResponse> create(@RequestBody JobCreateRequest jobRequest) {
        ApiResponse<JobResponse> response = new ApiResponse<>();
        response.setData(jobService.create(jobRequest));
        return response;
    }
}
