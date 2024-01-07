package br.com.notification.domain.usecase;

import br.com.notification.domain.port.in.IListNotificationUseCasePort;
import br.com.notification.infrastructure.repository.NotificationRepository;
import br.com.notification.infrastructure.repository.model.NotificationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ListNotificationUseCase implements IListNotificationUseCasePort {

    private final NotificationRepository repository;

    // Read data from read replicas
    @Override
    @Transactional(readOnly = true)
    public List<NotificationDao> getNotificationsByIdClient(String idClient) {

        log.info("Getting all notifications by id client {}", idClient);

        return this.repository.findAll();
    }

    // Read data from read replicas
    @Transactional(readOnly = true)
    public List<NotificationDao> getNotificationsByPeriod(String init, String end) {

        //log.info("Getting all notifications by id client {}", idClient);

        return this.repository.findAll();
    }
}
