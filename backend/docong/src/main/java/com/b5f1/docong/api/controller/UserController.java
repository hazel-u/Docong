package com.b5f1.docong.api.controller;

import com.b5f1.docong.api.dto.request.EmailCheckReqDto;
import com.b5f1.docong.api.dto.request.JoinReqDto;
import com.b5f1.docong.api.dto.request.UserInfoReqDto;
import com.b5f1.docong.api.dto.response.BaseResponseEntity;
import com.b5f1.docong.api.dto.response.EmailCheckResDto;
import com.b5f1.docong.api.dto.response.UserInfoResDto;
import com.b5f1.docong.api.resolver.Auth;
import com.b5f1.docong.api.service.MailService;
import com.b5f1.docong.api.service.UserService;
import com.b5f1.docong.config.jwt.JwtProperties;
import com.b5f1.docong.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    private final MailService mailService;

    @PostMapping("/join")
    public ResponseEntity<BaseResponseEntity> join(@RequestBody JoinReqDto joinReqDto) {
        userService.join(joinReqDto);

        return ResponseEntity.ok().body(new BaseResponseEntity(200, "Success"));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResDto> getUserInfo(@Auth User user) {
        UserInfoResDto userInfoResDto = userService.getUserInfo(user.getSeq());
        return ResponseEntity.ok().body(userInfoResDto);
    }

    @PatchMapping("/info")
    public ResponseEntity<BaseResponseEntity> setUserInfo(@Auth User user, @RequestBody UserInfoReqDto userInfoReqDto) {
        userService.setUserInfo(user, userInfoReqDto);

        return ResponseEntity.ok().body(new BaseResponseEntity(200, "Success"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponseEntity> delete(@Auth User user) {
        userService.deleteUser(user);

        return ResponseEntity.ok().body(new BaseResponseEntity(200, "Success"));
    }

    @PostMapping("/duplicate")
    public ResponseEntity<EmailCheckResDto> checkEmail(@RequestBody EmailCheckReqDto emailCheckReqDto) {
        boolean possible = userService.checkEmail(emailCheckReqDto.getEmail());

        // true??? ??????????????? ?????????, false??? ?????? ???????????? ?????????
        return ResponseEntity.ok().body(new EmailCheckResDto(possible));
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(JwtProperties.REFRESH_TOKEN_HEADER_STRING); // refresh token??? ?????? (RefreshToken)

        String newToken = userService.newToken(refreshToken);

        response.addHeader(JwtProperties.HEADER_STRING, newToken);
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> certificationEmail(@PathVariable("email") String email) {
        String number = mailService.getCertificationNumber(5);
        String subject = "Docong ?????? ?????? ??????";
        String content = "Docong ?????? ?????? : " + number + " ?????????";

        mailService.sendSimpleMail(email, subject, content);

        return ResponseEntity.ok().body(number);
    }

    @GetMapping("/password/{email}")
    public ResponseEntity<BaseResponseEntity> searchPassword(@PathVariable("email") String email) {
        String password = mailService.getRandomPassword(10);
        String subject = "Docong ???????????? ?????? ??????";
        String content = "Docong ????????? ???????????? : " + password + " ?????????";

        userService.changePassword(email, password);

        mailService.sendSimpleMail(email, subject, content);

        return ResponseEntity.status(200).body(new BaseResponseEntity(200, "Success"));
    }

}
