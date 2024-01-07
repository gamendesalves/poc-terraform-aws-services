package br.com.notification.domain.usecase;

import br.com.notification.common.exception.ConfirmationEmailException;
import br.com.notification.domain.port.in.IConfirmationEmailUseCase;
import br.com.notification.domain.port.in.INotificationUseCase;
import br.com.notification.infrastructure.repository.model.NotificationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class ConfirmationEmailUseCase implements IConfirmationEmailUseCase {

    private final INotificationUseCase notificationUseCase;

    @Override
    public void validateEmailConfirm(String confirmationId) {
        log.info("Confirmation email use case");

        var notification = this.notificationUseCase.getNotificationsByConfirmationId(confirmationId);

        this.validateIsEmailAlreadyConfirmed(notification);

        this.validateConfirmationIdExpired(notification);

        this.confirmEmail(notification);
    }

    private void confirmEmail(NotificationDao notification) {
        notification.setConfirmed(true);
        notificationUseCase.updateNotification(notification);

        log.info("Updating notification with email confirmed successfully !");
    }

    private void validateIsEmailAlreadyConfirmed(NotificationDao notification) {
        if (notification.isConfirmed()) {
            log.info("Email already confirmed !");
            throw new ConfirmationEmailException("Email already confirmed");
        }
    }

    private void validateConfirmationIdExpired(NotificationDao notification) {
        if (notification.getDateCreated().plusDays(2).isBefore(LocalDateTime.now())) {
            log.info("Confirmation Id expired");
            throw new ConfirmationEmailException("Confirmation Id expired");
        }
    }
}
