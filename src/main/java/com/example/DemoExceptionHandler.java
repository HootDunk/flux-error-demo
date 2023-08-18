package com.example;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Requires(classes = {DemoException.class, HttpRequest.class})
public class DemoExceptionHandler implements ExceptionHandler<DemoException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, DemoException exception) {
        return HttpResponse.status(HttpStatus.I_AM_A_TEAPOT).body(exception.getMessage());
    }
}
