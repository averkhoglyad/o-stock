package io.verkhoglyad.ostock.licensing.util;

import io.verkhoglyad.ostock.licensing.data.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageConverter {

    private final MessageSource messageSource;

    public String message(Message message) {
        return message(message, null);
    }

    public String message(Message message, Locale locale) {
        return messageSource.getMessage(message.message(), message.args(), locale);
    }

    public String message(String code) {
        return messageSource.getMessage(code, null, null, null);
    }

    public String message(String code, Locale locale) {
        return messageSource.getMessage(code, null, null, locale);
    }

    public String message(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }
}
