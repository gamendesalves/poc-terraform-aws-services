package br.com.notification.aplication.rest;

import br.com.notification.aplication.rest.request.notification.NotificationDTO;
import br.com.notification.domain.port.in.INotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationUseCase useCase;

    @PostMapping
    public ResponseEntity createNotification(@Valid @RequestBody NotificationDTO dto) {
        useCase.createNotification(dto.toNotificationUseCase());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
