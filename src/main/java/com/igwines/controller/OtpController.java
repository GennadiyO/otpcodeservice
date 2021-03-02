package com.igwines.controller;

import com.igwines.dto.GenerateUserDetails;
import com.igwines.dto.ValidateUserDetails;
import com.igwines.service.OtpCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/otp", produces = {APPLICATION_JSON_VALUE})
@Api
public class OtpController {
    private static final Logger logger = LoggerFactory.getLogger(OtpController.class);

    private final OtpCodeService otpCodeService;

    @PutMapping
    @ApiOperation("Generate OTP code and send by email")
    public ResponseEntity generateOtpCode(@RequestBody GenerateUserDetails generateUserDetails) {
        logger.info(String.format("Starting generate OTP code for user [%s]", generateUserDetails.getName()));
        otpCodeService.generateCodeAndSend(generateUserDetails);
        logger.info(String.format("OTP code generated for user [%s]", generateUserDetails.getName()));
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ApiOperation("Validate OTP code sent by email")
    public ResponseEntity validateOtpCode(@RequestBody ValidateUserDetails validateUserDetails) {
        otpCodeService.validateCode(validateUserDetails);
        return ResponseEntity.ok().build();
    }
}
