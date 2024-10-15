package org.neit.backend.controller;

import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.ProfessionRequest;
import org.neit.backend.dto.response.ProfessionResponse;
import org.neit.backend.service.ProfessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professions")
public class ProfessionController {

    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @PostMapping
    public ApiResponse<ProfessionResponse> create(@RequestBody ProfessionRequest request) {
        ApiResponse<ProfessionResponse> response = new ApiResponse<>();
        response.setData(professionService.create(request));
        return response;
    }
    @GetMapping
    public ApiResponse<List<ProfessionResponse>> getAll(@RequestParam(required = false) String name) {
        ApiResponse<List<ProfessionResponse>> response = new ApiResponse<>();
        response.setData(professionService.getAll(name));
        return response;
    }
}
