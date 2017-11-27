package org.innereye.common.exception;

@SuppressWarnings("serial")
public class BussinessException extends RuntimeException {

    private int code;

    private String message;

    public BussinessException(BizExceptionEnum bizExceptionEnum){
        this(bizExceptionEnum, bizExceptionEnum.getMessage());
    }

    public BussinessException(BizExceptionEnum bizExceptionEnum, String message){
        this.code = bizExceptionEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
