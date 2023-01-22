package io.averkhoglyad.ostock.licensing.controller;

import io.averkhoglyad.ostock.licensing.util.MessageConverter;
import io.averkhoglyad.ostock.licensing.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MessageConverter messageConverter;

    @ExceptionHandler
    public String exceptionHandle(AppException exception, @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        logger.debug("AppException:", exception);
        return messageConverter.message(exception.message(), locale);
    }

    @ExceptionHandler
    public String exceptionHandle(Exception exception, @RequestHeader(name = "Accept-Language", required = false)  Locale locale) {
        logger.error("Unhandled Exception:", exception);
        return messageConverter.message("common.unhandled.exception", locale);
    }
}
