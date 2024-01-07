package br.com.notification.aplication.rest.request.notification;

import br.com.notification.domain.model.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    @JsonProperty("id_client")
    @NotBlank( message = "Id Client is mandatory")
    private String idClient;

    @NotBlank( message = "Name is mandatory")
    private String name;

    @NotBlank( message = "Email is mandatory")
    private String email;

    public Notification toNotificationUseCase() {
        return Notification.builder()
                .idClient(this.getIdClient())
                .name(this.getName())
                .email(this.getEmail())
                .build();
    }

}
