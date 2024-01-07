package br.com.notification.aplication;

import br.com.notification.domain.port.in.IConfirmationEmailUseCase;
import br.com.notification.domain.port.in.IListNotificationUseCasePort;
import br.com.notification.domain.port.in.INotificationUseCase;
import br.com.notification.domain.port.out.ISesGateway;
import br.com.notification.domain.usecase.ConfirmationEmailUseCase;
import br.com.notification.domain.usecase.ListNotificationUseCase;
import br.com.notification.domain.usecase.NotificationUseCaseImpl;
import br.com.notification.infrastructure.gateway.SesGateway;
import br.com.notification.infrastructure.repository.NotificationRepository;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
public class ContextNotification {

    @Bean
    public IListNotificationUseCasePort listNotificationUseCase(NotificationRepository repository) {
        return new ListNotificationUseCase(repository);
    }

    @Bean
    public INotificationUseCase notificationUseCase(NotificationRepository repository, ISesGateway sesGateway) {
        return new NotificationUseCaseImpl(repository, sesGateway);
    }

    @Bean
    public IConfirmationEmailUseCase confirmationEmailUseCase(INotificationUseCase notificationUseCase) {
        return new ConfirmationEmailUseCase(notificationUseCase);
    }

    @Bean
    public ISesGateway sesGateway(AmazonSimpleEmailService amazonSimpleEmailService, SpringTemplateEngine templateEngine) {
        return new SesGateway(amazonSimpleEmailService, templateEngine);
    }
}
