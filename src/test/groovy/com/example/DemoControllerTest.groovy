package com.example

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class DemoControllerTest extends Specification {

    @Client("/demo")
    @Inject
    HttpClient httpClient

    def "test mono error"() {
        when:
        var res = httpClient.toBlocking().exchange(HttpRequest.GET("/mono"), Integer)

        then:
        def ex = thrown(HttpClientResponseException)
        ex.getStatus() == HttpStatus.I_AM_A_TEAPOT
    }

    // Fails currently
    def "test flux error"() {
        when:
        var res = httpClient.toBlocking().exchange(HttpRequest.GET("/flux"), List<Integer>)

        then:
        def ex = thrown(HttpClientResponseException)
        ex.getStatus() == HttpStatus.I_AM_A_TEAPOT
    }





    static class StatusTrap<T> {
        HttpStatus status
        Optional<T> data
        Optional<Throwable> ex

        StatusTrap(T data, HttpStatus status, Throwable ex) {
            this.status = status
            this.data = Optional.ofNullable(data)
            this.ex = Optional.ofNullable(ex)
        }
    }

    private <I, O> StatusTrap<O> statusTrap(HttpRequest<I> request, Class<O> bodyType) {
        try {
            def response = httpClient.toBlocking()
                    .exchange(request, bodyType)
            return new StatusTrap<O>(response.body(), response.status(), null)
        } catch (HttpClientResponseException ex) {
            return new StatusTrap<O>(null, ex.response.status(), ex)
        }
    }

}
