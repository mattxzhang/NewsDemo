package com.kingyon.netlib.exception;

/**
 * Created by Leo on 2016/5/4
 */
public class ResultException extends RuntimeException {

    private int errCode = 0;

    private String message;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
