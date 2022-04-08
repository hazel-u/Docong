package com.b5f1.docong.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;

import static java.lang.Math.*;

@Slf4j
@Service
@RequiredArgsConstructor
// 메일 전송 서비스
public class MailService {
    private final JavaMailSender mailSender;


    // application yml 설정파일에 설정한 값 사용
    @Value("${spring.mail.username}")
    private String senderEmailAddr;

    // application yml 설정파일에 설정한 값 사용
    @Value("${spring.mail.to-name}")
    private String toEmailAddr;


    /**
     * Email 전송 (받는 주소, 제목, 내용)
     * 보내는 주소는 설정값으로 고정
     *
     * @param to
     * @param subject
     * @param content
     */
    @Async
    public void sendSimpleMail(String to, String subject, String content) {
        if (to.indexOf("@test.com") >= 0) return;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmailAddr);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            log.info("sendSimpleMail done!");
        } catch (Exception e) {
            log.error("sendSimpleMail exception！", e);
        }
    }


    /**
     * Email 전송 (보내는 주소, 제목, 내용)
     * 받는 주소는 설정값으로 고정
     *
     * @param from
     * @param subject
     * @param content
     * @return
     */
    @Async
    public String sendSimpleMailFrom(String from, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmailAddr);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            log.info("sendSimpleMail done!");
            return "success";
        } catch (Exception e) {
            log.error("sendSimpleMail exception！", e);
            return "failed";
        }
    }

    /**
     * Email HTML 전송
     *
     * @param to
     * @param subject
     * @param content
     */
    @Async
    public void sendHtmlMail(String to, String subject, String content) {
        if (to.indexOf("@test.com") >= 0) return;
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmailAddr);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("sendHtmlMail done!");
        } catch (MessagingException e) {
            log.error("sendHtmlMail exception！", e);
        }
    }

    /**
     * Email 첨부파일 추가 전송
     *
     * @param to
     * @param subject
     * @param content
     * @param filename
     */
    @Async
    public void sendAttachmentsMail(String to, String subject, String content, String filename) {
        if (to.indexOf("@test.com") >= 0) return;
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmailAddr);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(filename);
            FileSystemResource file = new FileSystemResource(new File(url.getFile()));
            helper.addAttachment(filename, file);

            mailSender.send(message);
            log.info("sendAttachmentsMail done!");
        } catch (MessagingException e) {
            log.error("sendAttachmentsMail exception！", e);
        }
    }

    /**
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Async
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        if (to.indexOf("@test.com") >= 0) return;
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmailAddr);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            log.info("sendInlineResourceMail done!");
        } catch (MessagingException e) {
            log.error("sendInlineResourceMail exception！", e);
        }
    }

    /**
     * @param len Ramdom 길이
     * @return
     */
    public String getRandomPassword(int len) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@', '#'};
        int idx = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            idx = (int) (charSet.length * random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }

    public String getCertificationNumber(int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append((int) (random() * 10));
        }
        return sb.toString();
    }
}