package br.com.notification.domain.port.in;

public interface IConfirmationEmailUseCase {

    void validateEmailConfirm(String confirmationId);
}
