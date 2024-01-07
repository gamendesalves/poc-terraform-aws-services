package integration.rest;

import br.com.notification.NotificationApplication;
import br.com.notification.domain.model.Notification;
import br.com.notification.infrastructure.repository.NotificationRepository;
import integration.ConfigContainers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationControllerTest extends ConfigContainers {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationRepository repository;

    @Test
    void testNotification_withSuccess() throws Exception {

        var notification = mockNotification();

        var response = restTemplate.postForEntity("/v1/notification", notification, Object.class);

        var notifications = repository.findAll();

        assertEquals(201, response.getStatusCode().value());
        assertEquals(notifications.size(), 1);
        assertEquals(notifications.get(0).getIdClient(), notification.getIdClient());
    }

    private Notification mockNotification() {
        return Notification.builder()
                .name("Gabriel")
                .email("gabrielmendesalves120@gmail.com")
                .idClient("123456789")
                .build();
    }
}
