package br.com.notification.domain.model;

import br.com.notification.infrastructure.repository.model.NotificationDao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @JsonProperty("id_client")
    private String idClient;

    private String name;

    private String email;

    public NotificationDao toNotificationDao(String confirmationId) {
        return NotificationDao.builder()
                .idClient(this.idClient)
                .name(this.name)
                .email(this.email)
                .confirmed(false)
                .confirmationId(confirmationId)
                .dateCreated(LocalDateTime.now())
                .build();
    }
}
