package br.com.notification.infrastructure.repository;

import br.com.notification.infrastructure.repository.model.NotificationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationDao, Long> {

    @Query("SELECT s FROM notification s WHERE s.confirmationId =:confirmationId")
    NotificationDao findByConfirmationId(String confirmationId);
}
