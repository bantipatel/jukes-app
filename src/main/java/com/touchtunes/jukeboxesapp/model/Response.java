package com.touchtunes.jukeboxesapp.model;

import org.springframework.http.HttpStatus;

public class Response {

    private String message;
    private HttpStatus status;

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
