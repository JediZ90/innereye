package org.innereye.consul.exception;

import java.io.IOException;

import retrofit2.Response;

@SuppressWarnings("serial")
public class ConsulException extends RuntimeException {

    private int code;

    public ConsulException(String message){
        super(message);
    }

    public ConsulException(String message, Throwable throwable){
        super(message, throwable);
    }

    public ConsulException(Throwable throwable){
        super("Consul failed", throwable);
    }

    public ConsulException(Response<?> response){
        super(String.format("Consul request failed with status [%s]: %s", response.code(), message(response)));
        this.code = response.code();
    }

    protected static String message(Response<?> response) {
        try {
            return response.errorBody().string();
        }
        catch (IOException e) {
            return response.message();
        }
    }

    public int getCode() {
        return code;
    }
}
