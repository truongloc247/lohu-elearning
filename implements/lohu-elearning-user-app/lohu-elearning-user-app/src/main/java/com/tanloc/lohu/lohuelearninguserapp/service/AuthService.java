package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.dto.EmailVerificationRequest;
import com.tanloc.lohu.lohuelearninguserapp.dto.TokenVerificationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.Token;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.exception.EmailAlreadyExistsException;
import com.tanloc.lohu.lohuelearninguserapp.exception.InvalidTokenException;
import com.tanloc.lohu.lohuelearninguserapp.infrastructure.EmailSender;
import com.tanloc.lohu.lohuelearninguserapp.infrastructure.TokenGenerator;
import com.tanloc.lohu.lohuelearninguserapp.repository.TokenRepository;
import com.tanloc.lohu.lohuelearninguserapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    UserRepository userRepository;
    TokenRepository tokenRepository;
    TokenGenerator tokenGenerator;
    EmailSender emailSender;

    @Transactional(rollbackFor = Exception.class)
    public void verifyEmail(EmailVerificationRequest emailVerificationRequest) throws MessagingException {
        User user = userRepository.findByEmail(emailVerificationRequest.getEmail()).orElse(null);
        if (user != null) {
            throw new EmailAlreadyExistsException("Email đã tồn tại");
        }

        Token token = Token.builder()
                .token(tokenGenerator.generateToken())
                .email(emailVerificationRequest.getEmail())
                .build();
        Token savedToken = tokenRepository.save(token);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("tokenId", savedToken.getId());
        tokenData.put("token", savedToken.getToken());
        emailSender.sendEmail(emailVerificationRequest.getEmail(), "Xác nhận đăng ký tài khoản LOHU", "email", tokenData);
    }

    public Token verifyToken(TokenVerificationRequest tokenVerificationRequest) {
        Token token = tokenRepository.findByIdAndTokenAndExpirationTimeGreaterThanEqualAndIsVerifiedFalse(tokenVerificationRequest.getTokenId(), tokenVerificationRequest.getToken(), LocalDateTime.now())
                .orElseThrow(() -> new InvalidTokenException("Xác thực token không thành công"));

        Token lastestToken = tokenRepository.findTopByEmailOrderByIdDesc(token.getEmail())
                .orElseThrow(() -> new InvalidTokenException("Xác thực token không thành công"));

        if (token.getId() != lastestToken.getId()) {
            throw new InvalidTokenException("Xác thực token không thành công");
        }
        token.setIsVerified(true);
        return tokenRepository.save(token);
    }
}
