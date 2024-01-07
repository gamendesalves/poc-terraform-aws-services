package integration.rest;

import br.com.notification.NotificationApplication;
import br.com.notification.common.helper.ConfirmationIdHelper;
import br.com.notification.domain.model.Notification;
import br.com.notification.infrastructure.repository.NotificationRepository;
import integration.ConfigContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfirmationEmailControllerTest extends ConfigContainers {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();
    }

    @Test
    void testConfirmationEmail_withSuccess() throws Exception {

        var notificationDao = mockNotification().toNotificationDao(ConfirmationIdHelper.generateId());

        repository.save(notificationDao);

        var response = restTemplate.postForEntity("/confirmation/v1/email/{confirmationId}", null, Object.class, notificationDao.getConfirmationId());

        var notifications = repository.findAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, notifications.size());
        assertTrue(notifications.get(0).isConfirmed());
    }

    private Notification mockNotification() {
        return Notification.builder()
                .name("Gabriel")
                .email("gabrielmendesalves120@gmail.com")
                .idClient("123456789")
                .build();
    }
}
