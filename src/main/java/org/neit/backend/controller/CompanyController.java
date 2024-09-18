package org.neit.backend.controller;

import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.CompanyRequest;
import org.neit.backend.dto.response.CompanyResponse;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.service.CompanyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ApiResponse<CompanyResponse> create(@RequestBody CompanyRequest request) {
        ApiResponse<CompanyResponse> response = new ApiResponse<>();
        response.setData(companyService.create(request));
        return response;
    }
    @GetMapping("/search")
    public ApiResponse<ResultPaginationResponse> getByName(@RequestParam String name,
                                                           @RequestParam("page") Optional<String> page,
                                                           @RequestParam("pageSize") Optional<String> pageSize
    ) {
        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();
        response.setData(companyService.getByName(name, page, pageSize));
        return response;
    }
    @PutMapping("/{id}")
    public ApiResponse<CompanyResponse> update(@PathVariable Integer id,@RequestBody CompanyRequest request) {
        ApiResponse<CompanyResponse> response = new ApiResponse<>();
        response.setData(companyService.update(id,request));
        return response;
    }
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Integer id) {
        ApiResponse<?> response = new ApiResponse<>();
        companyService.delete(id);
        return response;
    }
    @GetMapping("/all")
    public ApiResponse<ResultPaginationResponse> getAll(
            @RequestParam("page") Optional<String> page,
            @RequestParam("pageSize") Optional<String> pageSize
            ) {

        ApiResponse<ResultPaginationResponse> response = new ApiResponse<>();
        response.setData(companyService.getAll(page, pageSize));
        return response;
    }
}
