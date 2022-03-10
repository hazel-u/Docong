package com.b5f1.docong.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.b5f1.docong.config.jwt.JwtProperties;
import com.b5f1.docong.config.oauth.provider.GoogleUser;
import com.b5f1.docong.config.oauth.provider.OAuthUserInfo;
import com.b5f1.docong.core.domain.user.User;
import com.b5f1.docong.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GoogleUserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/google")
    public String GoogleJwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("GoogleJwtCreate 실행됨");
        System.out.println(data.get("profileObj"));

        OAuthUserInfo googleUser = new GoogleUser((Map<String, Object>) data.get("profileObj"));

        User userEntity = userRepository.findByEmail(googleUser.getEmail());

        if (userEntity == null) {
            User userRequest = User.builder()
                    .email(googleUser.getEmail())
                    .password(bCryptPasswordEncoder.encode("Docong789456"))
                    .name(googleUser.getName())
                    .birth(null)
                    .gender(null)
                    .address(null)
                    .job(null)
                    .position(null)
                    .activate(true)
                    .oauth_type(googleUser.getProvider())
                    .build();

            userEntity = userRepository.save(userRequest);
        }

        String jwtToken = JWT.create()
                .withSubject(userEntity.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userEntity.getSeq())
                .withClaim("email", userEntity.getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        System.out.println("Google JWT Token : " + jwtToken);
        return jwtToken;
    }

}