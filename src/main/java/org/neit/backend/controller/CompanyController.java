package org.neit.backend.controller;

import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.CompanyRequest;
import org.neit.backend.dto.response.CompanyResponse;
import org.neit.backend.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{name}")
    public ApiResponse<List<CompanyResponse>> getByName(@PathVariable String name) {
        ApiResponse<List<CompanyResponse>> response = new ApiResponse<>();
        response.setData(companyService.getByName(name));
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
    @GetMapping
    public ApiResponse<List<CompanyResponse>> getAll() {
        ApiResponse<List<CompanyResponse>> response = new ApiResponse<>();
        response.setData(companyService.getAll());
        return response;
    }
}
