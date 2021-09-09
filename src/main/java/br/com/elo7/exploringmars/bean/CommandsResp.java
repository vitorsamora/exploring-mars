package br.com.elo7.exploringmars.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CommandsResp extends ProbeResp {
    
    private boolean completed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
