package org.innereye.common.exception;

public enum BizExceptionEnum {
    DICT_EXISTED(400, "字典已经存在");

    private int code;

    private String message;

    BizExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
