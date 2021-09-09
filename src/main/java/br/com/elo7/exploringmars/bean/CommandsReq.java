package br.com.elo7.exploringmars.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CommandsReq implements Serializable {

    @NotNull
    private UUID resourceId;

    @NotNull
    @Pattern(regexp = "^[LRM]+$")
    private String commands;

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

}
