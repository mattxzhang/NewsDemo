package com.kingyon.netlib.entitys;

import com.google.gson.JsonElement;

/**
 * Created by Leo on 2016/5/4
 */
public class ResultResponseEntity {

    private int status;

    private String message;

    private JsonElement content;

    private InputErrorEntity inputError;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        if (inputError != null) {
            String temp = inputError.getMessage();
            if (temp != null) {
                return temp;
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getContent() {
        return content;
    }

    public void setContent(JsonElement content) {
        this.content = content;
    }

    public InputErrorEntity getInputError() {
        return inputError;
    }

    public void setInputError(InputErrorEntity inputError) {
        this.inputError = inputError;
    }
}
