package com.oopsiedaisy.mail;

import com.oopsiedaisy.config.exceptions.FailedToSendEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    public void sendSimpleMessage(String to, String subject, String template, Map<String, Object> templateVariables) {
        MimeMessage message = buildMessage(to, subject, template, templateVariables);
        log.info("Sending order info to email {}", to);
        emailSender.send(message);
        log.info("Email sent successfully!");
    }

    private MimeMessage buildMessage(String to, String subject, String template, Map<String, Object> templateVariables) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
            Context context = new Context();
            context.setVariables(templateVariables);
            helper.setFrom("noreply@oopsiedaisy.lt");
            helper.setTo(to);
            helper.setSubject(subject);
            String html = templateEngine.process(template, context);
            helper.setText(html, true);
            return message;
        } catch (Exception e) {
            log.error("Failed to send email to {}, reason: {}", to, e.getMessage());
            throw new FailedToSendEmailException(e.getMessage());
        }
    }
}
