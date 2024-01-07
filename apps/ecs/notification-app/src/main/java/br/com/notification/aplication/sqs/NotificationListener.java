package br.com.notification.aplication.sqs;

import br.com.notification.aplication.sqs.model.NotificationListenerDTO;
import br.com.notification.domain.port.in.INotificationUseCase;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final INotificationUseCase useCase;

    @SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listenerNotification(@Valid @Payload NotificationListenerDTO dto, @Header("SenderId") String senderId) {
        log.info("Received message {} {}", senderId, dto);
        useCase.createNotification(dto.toNotificationUseCase());
    }

    @MessageExceptionHandler(Exception.class)
    public void handleException(Exception ex) {
        log.error("Notification listener Exception", ex);
    }
}
