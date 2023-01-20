package io.verkhoglyad.ostock.licensing.controller;

import io.verkhoglyad.ostock.licensing.exception.AppException;
import io.verkhoglyad.ostock.licensing.util.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final MessageConverter messageConverter;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    public String exceptionHandle(AppException exception, Locale locale) {
        logger.debug("AppException: ", exception);
        return messageConverter.message(exception.message(), locale);
    }

    @ExceptionHandler
    public String exceptionHandle(Exception exception, Locale locale) {
        logger.error("Unhandled Exception: ", exception);
        return messageConverter.message("common.unhandled.exception", locale);
    }
}
