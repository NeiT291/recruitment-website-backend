package org.neit.backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.JobRequest;
import org.neit.backend.dto.response.JobResponse;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/jobs")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ApiResponse<JobResponse> create(@RequestBody JobRequest jobRequest) {
        ApiResponse<JobResponse> response = new ApiResponse<>();
        response.setData(jobService.create(jobRequest));
        return response;
    }
    @PutMapping("/{id}")
    public ApiResponse<JobResponse> update(@RequestParam Integer id,@RequestBody JobRequest request) {
        ApiResponse<JobResponse> response = new ApiResponse<>();
        response.setData(jobService.update(id, request));
        return response;
    }
    @DeleteMapping
    public ApiResponse<?> delete(@RequestParam Integer id) {
        ApiResponse<?> response = new ApiResponse<>();
        jobService.delete(id);
        return response;
    }
    @GetMapping("/search/my-job")
    public ApiResponse<ResultPaginationResponse> getAllByUser(@RequestParam  Optional<String> page,@RequestParam Optional<String> pageSize){
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();
        response.setData(jobService.getAllByUser(page,pageSize));
        return response;
    }
    @GetMapping("/search")
    public ApiResponse<ResultPaginationResponse> getByName(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String city,
                                                           @RequestParam(required = false) String company,
                                                           @RequestParam(required = false) Integer min_wage,
                                                           @RequestParam(required = false) Integer max_wage,
                                                           @RequestParam(required = false) Integer wage,
                                                           @RequestParam Optional<String> page,
                                                           @RequestParam Optional<String> pageSize){
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();

        response.setData(jobService.getAll(name, city, company, min_wage, max_wage, wage, page, pageSize));

        return response;
    }
}
