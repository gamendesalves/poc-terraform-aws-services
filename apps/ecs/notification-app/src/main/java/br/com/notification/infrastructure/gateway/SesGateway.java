package br.com.notification.infrastructure.gateway;

import br.com.notification.domain.port.out.ISesGateway;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SesGateway implements ISesGateway {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    private final SpringTemplateEngine templateEngine;

    @Value("${aws.ses.email.sender}")
    private String FROM;

    @Value("${aws.ses.email.path_confirmation_email}")
    private String BASE_PATH_CONFIRMATION_EMAIL;

    private static final String CHARSET_UTF_8 = "UTF-8";
    private static final String NAME_TEMPLATE_EMAIL = "email-template";

    @Override
    public void sendEmailConfirmation(String name, String email, String confirmationId) {

        log.info("Creating html email");

        var context = new Context();
        context.setVariables(this.getVariables(name, confirmationId));
        String html = templateEngine.process(NAME_TEMPLATE_EMAIL, context);

        log.info("Sending email");
        var result = amazonSimpleEmailService.sendEmail(this.createEmailRequest(email, html));

        log.info("Confirmation email sent {}", result);
    }

    private SendEmailRequest createEmailRequest(String email, String html) {
        return new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(email))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset(CHARSET_UTF_8).withData(html))
                                .withText(new Content()
                                        .withCharset(CHARSET_UTF_8).withData("This email was sent through Amazon SES"))
                        )
                        .withSubject(new Content()
                                .withCharset(CHARSET_UTF_8).withData("Please confirm your email for your POC Project !")))
                .withSource(FROM);
    }

    private Map getVariables(String name, String confirmationId) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("url_confirmation", new StringBuilder().append(BASE_PATH_CONFIRMATION_EMAIL).append(confirmationId).toString());
        return variables;
    }
}
