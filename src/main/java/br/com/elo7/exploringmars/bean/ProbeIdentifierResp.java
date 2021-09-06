package br.com.elo7.exploringmars.bean;

import java.util.UUID;

public class ProbeIdentifierResp extends ProbeResp {
    
    private UUID resourceId;

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

}
