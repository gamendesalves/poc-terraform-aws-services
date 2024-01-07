package integration.sqs;

import br.com.notification.aplication.sqs.model.NotificationListenerDTO;
import br.com.notification.infrastructure.repository.NotificationRepository;
import integration.ConfigContainers;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class NotificationListenerTest extends ConfigContainers {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private NotificationRepository repository;

    @Value("${aws.sqs.queue.name}")
    private String queueName;

    @Test
    void testCreateNotification_withSuccess() {

        queueMessagingTemplate.convertAndSend(queueName, mockNotification());

        await()
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> {
                    var notifications = repository.findAll();
                    assertEquals(notifications.size(), 1);
                });
    }

    private NotificationListenerDTO mockNotification() {
        return NotificationListenerDTO.builder()
                .name("Gabriel")
                .email("gabrielmendesalves120@gmail.com")
                .idClient("12345679")
                .build();
    }
}
