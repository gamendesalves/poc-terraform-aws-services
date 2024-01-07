package br.com.notification.domain.usecase;

import br.com.notification.common.helper.ConfirmationIdHelper;
import br.com.notification.domain.model.Notification;

import br.com.notification.domain.port.out.ISesGateway;
import br.com.notification.domain.port.in.INotificationUseCase;
import br.com.notification.infrastructure.repository.NotificationRepository;
import br.com.notification.infrastructure.repository.model.NotificationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NotificationUseCaseImpl implements INotificationUseCase {

    private final NotificationRepository repository;
    private final ISesGateway sesGateway;

    @Override
    @Transactional
    public void createNotification(Notification notification) {
        log.info("Creating Notification {}", notification);

        var confirmationId = ConfirmationIdHelper.generateId();

        this.sesGateway.sendEmailConfirmation(notification.getName(), notification.getEmail(), confirmationId);

        this.repository.save(notification.toNotificationDao(confirmationId));
    }

    @Override
    @Transactional
    public void updateNotification(NotificationDao dao) {
        log.info("Confirm Email Notification {}", dao);
        this.repository.save(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationDao getNotificationsByConfirmationId(String confirmationId) {

        log.info("Getting all notifications by confirmation id {}", confirmationId);

        return this.repository.findByConfirmationId(confirmationId);
    }

}
