package org.neit.backend.controller;

import com.nimbusds.jose.JOSEException;
import org.neit.backend.dto.ApiResponse;
import org.neit.backend.dto.request.IntrospectRequest;
import org.neit.backend.dto.request.LoginRequest;
import org.neit.backend.dto.response.AuthenticationResponse;
import org.neit.backend.dto.response.IntrospectResponse;
import org.neit.backend.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return new ApiResponse<>( authenticationService.authenticate(request));
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return new ApiResponse<>(authenticationService.introspect(request));
    }
}
