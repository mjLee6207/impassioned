package egovframework.example.member.service.impl;

import javax.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Resource(name = "mailSender")
    private JavaMailSender mailSender;

    public void sendCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[CheForest] 이메일 인증번호 안내");
        message.setText("안녕하세요.\n요청하신 인증번호는 다음과 같습니다.\n\n인증번호: " + code + "\n\n감사합니다.");
        message.setFrom("cheforest2@gmail.com");
        mailSender.send(message);
    }
}