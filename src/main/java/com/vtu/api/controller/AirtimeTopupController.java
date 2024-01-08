package com.vtu.api.controller;

import com.vtu.api.dto.request.AirtimeTopupRequest;
import com.vtu.api.dto.response.AirtimeTopupResponse;
import com.vtu.api.dto.response.ApiResponse;
import com.vtu.api.service.AirtimeTopupService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airtime")
@RequiredArgsConstructor
@Validated
public class AirtimeTopupController {

    private final AirtimeTopupService airtimeTopupService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Top up airtime")
    public ResponseEntity<ApiResponse<AirtimeTopupResponse>> register(@RequestBody @Valid
                                                                      AirtimeTopupRequest request) {
        return ResponseEntity.ok(airtimeTopupService.topUpAirtime(request));
    }

}
