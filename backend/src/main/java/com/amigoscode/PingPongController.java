package com.amigoscode;

public class PingPongController {
    record PingPong (String submit) {}

    public PingPong getPingPong() {
        return new PingPong("ayolahh");
    }
}
