package com.igwines.service;

import com.igwines.dao.UserRepository;
import com.igwines.dto.GenerateUserDetails;
import com.igwines.dto.ValidateUserDetails;
import com.igwines.exceptions.CodeNotValidException;
import com.igwines.exceptions.NotValidUserDetailsException;
import com.igwines.exceptions.UserNotFoundException;
import com.igwines.helper.EmailSender;
import com.igwines.helper.OtpCodeGenerator;
import com.igwines.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class OtpCodeServiceImpl implements OtpCodeService {
    private static final Logger logger = LoggerFactory.getLogger(OtpCodeServiceImpl.class);

    private final EmailSender emailSender;
    private final OtpCodeGenerator otpCodeGenerator;
    private final UserRepository userRepository;

    @Value("${code.length}")
    private Integer codeLength;
    @Value("${code.life.time}")
    private Long codeLifeTime;

    @Override
    public void generateCodeAndSend(GenerateUserDetails generateUserDetails) {
        User user = userRepository.findUserByName(generateUserDetails.getName())
                .orElseThrow(() -> new UserNotFoundException(generateUserDetails.getName()));
        if (!user.getEmail().equals(generateUserDetails.getEmail())) {
            logger.error(String.format("User details with username [%s] not valid", generateUserDetails.getName()));
            throw new NotValidUserDetailsException();
        }
        String code = otpCodeGenerator.generateCode(codeLength);
        logger.info("OTP code generated");
        emailSender.sendEmail(generateUserDetails, code);
        logger.info(String.format("Email sent user [%s] with email [%s]", generateUserDetails.getName(), generateUserDetails.getEmail()));
        user.setCode(code);
        user.setCodeGenerationDate(Date.from(Instant.now()));
        userRepository.save(user);
        logger.info(String.format("User [%s] updated and saved to repository", generateUserDetails.getName()));
    }

    @Override
    public void validateCode(ValidateUserDetails validateUserDetails) {
        User user = userRepository.findUserByName(validateUserDetails.getName())
                .orElseThrow(() -> new UserNotFoundException(validateUserDetails.getName()));

        if (!(user.getCode() != null
                && user.getCodeGenerationDate() != null
                && user.getCode().equals(validateUserDetails.getCode())
                && Date.from(Instant.now())
                .before(Date.from(user.getCodeGenerationDate().toInstant().plusSeconds(codeLifeTime))))) {
            logger.error(String.format("Code not valid or expired for user [%s] not valid", validateUserDetails.getName()));
            throw new CodeNotValidException();
        }
    }
}
