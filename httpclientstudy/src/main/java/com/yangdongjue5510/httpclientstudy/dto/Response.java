package com.yangdongjue5510.httpclientstudy.dto;

import lombok.Getter;

@Getter
public class Response {

    private final String message;

    public Response(final String message) {
        this.message = message;
    }
}
