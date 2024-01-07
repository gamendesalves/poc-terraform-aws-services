package br.com.notification.domain.port.out;

public interface ISesGateway {
    void sendEmailConfirmation(String name, String email, String confirmationId);
}
