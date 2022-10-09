package com.yangdongjue5510.httpclientstudy.dto;

import lombok.Getter;

@Getter
public class ResponseDto {

    private String message;

    public ResponseDto() {
    }

    public ResponseDto(final String message) {
        this.message = message;
    }
}
