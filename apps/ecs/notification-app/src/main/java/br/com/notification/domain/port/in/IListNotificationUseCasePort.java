package br.com.notification.domain.port.in;

import br.com.notification.infrastructure.repository.model.NotificationDao;

import java.util.List;

public interface IListNotificationUseCasePort {
    List<NotificationDao> getNotificationsByIdClient(String idClient);
}
