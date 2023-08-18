package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/demo")
public class DemoController {
    private static final DemoException ex = new DemoException("Something went wrong");

    @Get("/mono")
    public Mono<Integer> monoExample(){
        return Mono.error(ex);
    }

    @Get("/flux")
    public Flux<Integer> fluxExample(){
        return Flux.error(ex);
    }
}
