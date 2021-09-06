package br.com.elo7.exploringmars.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

public class ProbeIdentifierReq implements Serializable {
    
    @NotNull
    private UUID resourceId;

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

}
