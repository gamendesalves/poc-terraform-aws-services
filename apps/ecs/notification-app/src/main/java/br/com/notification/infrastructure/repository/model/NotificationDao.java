package br.com.notification.infrastructure.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import br.com.notification.domain.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "notification")
public class NotificationDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "id_client", nullable = false)
    @NotBlank(message = "Id Client is mandatory")
    public String idClient;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    public String name;

    @Column(nullable = false)
    @NotBlank(message = "Email is mandatory")
    public String email;

    @Column(nullable = false)
    public boolean confirmed;

    @Column(name = "confirmation_id", nullable = false)
    public String confirmationId;

    @Column(name = "date_created", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreated;

}
