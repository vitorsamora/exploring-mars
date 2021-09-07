package br.com.elo7.exploringmars.bean;

import java.io.Serializable;

public class ErrorResp implements Serializable {
    
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
