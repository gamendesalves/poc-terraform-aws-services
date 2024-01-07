package br.com.notification.aplication.rest;

import br.com.notification.aplication.rest.response.confirmation.ConfirmationEmail;
import br.com.notification.domain.port.in.IConfirmationEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/confirmation/v1/email")
@RequiredArgsConstructor
public class ConfirmationEmailController {

    private final IConfirmationEmailUseCase useCase;

    @PostMapping("/{confirmationId}")
    public ResponseEntity confirmationEmail(@Valid @PathVariable("confirmationId") String id) {
        useCase.validateEmailConfirm(id);
        return ResponseEntity.ok(new ConfirmationEmail("Email confirmed successfully!"));
    }
}
