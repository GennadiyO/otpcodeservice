package com.igwines.service;

import com.igwines.dto.GenerateUserDetails;
import com.igwines.dto.ValidateUserDetails;

public interface OtpCodeService {
    void generateCodeAndSend(GenerateUserDetails generateUserDetails);

    void validateCode(ValidateUserDetails validateUserDetails);
}
