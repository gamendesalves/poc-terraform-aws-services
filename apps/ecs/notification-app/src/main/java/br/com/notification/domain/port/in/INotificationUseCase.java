package br.com.notification.domain.port.in;

import br.com.notification.domain.model.Notification;
import br.com.notification.infrastructure.repository.model.NotificationDao;

public interface INotificationUseCase {
    void createNotification(Notification notification);

    void updateNotification(NotificationDao dao);

    NotificationDao getNotificationsByConfirmationId(String confirmationId);
}
