package com.yangdongjue5510.httpclientstudy.dto;

import lombok.Getter;

@Getter
public class RequestDto {

    private String message;

    public RequestDto() {
    }

    public RequestDto(final String message) {
        this.message = message;
    }
}
