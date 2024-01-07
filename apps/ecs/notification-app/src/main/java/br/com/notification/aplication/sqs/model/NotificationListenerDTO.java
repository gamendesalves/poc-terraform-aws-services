package br.com.notification.aplication.sqs.model;

import br.com.notification.domain.model.Notification;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationListenerDTO {

    @JsonProperty("id_client")
    @NotBlank(message = "Id Client is mandatory")
    private String idClient;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    private String email;

    public Notification toNotificationUseCase() {
        return Notification.builder()
                .idClient(this.getIdClient())
                .name(this.getName())
                .email(this.getEmail())
                .build();
    }
}
