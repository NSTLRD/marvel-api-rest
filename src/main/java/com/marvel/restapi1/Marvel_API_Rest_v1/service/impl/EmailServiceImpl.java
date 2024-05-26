/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.service.impl;

import com.marvel.restapi1.Marvel_API_Rest_v1.constants.EmailTemplateName;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    public void sendEmail(String to, String username, EmailTemplateName emailTemplate,
                          String ConfirmationUrl,
                          String activationCode, String subject) throws MessagingException {
        // send email
        String templateName = null;
        switch (emailTemplate == null ? EmailTemplateName.ACTIVATE_ACCOUNT : emailTemplate) {
            case ACTIVATE_ACCOUNT:
                templateName = "activate_account";
                break;
            case RESET_PASSWORD:
                templateName = "reset-password";
                break;
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        //add the parameters to the template
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", ConfirmationUrl);
        properties.put("activation_code", activationCode);

        //pass the parameters to the template
        Context context = new Context();
        context.setVariables(properties);

        //create the email body
        helper.setFrom("contact@mentorly.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);
        javaMailSender.send(mimeMessage);
    }
}
