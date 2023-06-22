package com.amigoscode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    private static int COUNTER = 0;
    record PingPong (String submit) {}

    @GetMapping("/ping")
    public PingPong getPingPong() {
        return new PingPong("Ping test 4: %s".formatted(++COUNTER));
    }
}
