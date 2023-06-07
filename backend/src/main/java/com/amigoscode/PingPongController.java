package com.amigoscode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    record PingPong (String submit) {}

    @GetMapping("/ping")
    public PingPong getPingPong() {
        return new PingPong("test (9)");
    }
}
