package br.com.notification.common.helper;

import java.time.Instant;
import java.util.UUID;

public class ConfirmationIdHelper {
    private ConfirmationIdHelper() {
    }

    public static String generateId() {
        var instantTime = Instant.now().toEpochMilli();
        return new StringBuilder().append(UUID.randomUUID().toString()).append(instantTime).toString();
    }
}
