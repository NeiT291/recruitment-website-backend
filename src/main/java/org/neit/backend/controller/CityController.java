package org.neit.backend.controller;

import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.response.CityResponse;
import org.neit.backend.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ApiResponse<List<CityResponse>> getAllCity() {
        ApiResponse<List<CityResponse>> response = new ApiResponse<>();
        response.setData(cityService.getAll());
        return response;
    }
}
