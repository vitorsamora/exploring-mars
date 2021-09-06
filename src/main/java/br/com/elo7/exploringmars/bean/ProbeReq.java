package br.com.elo7.exploringmars.bean;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProbeReq implements Serializable {

    @NotNull
    private Long mapId;
    
    @NotNull
    @Min(0)
    private Integer x;

    @NotNull
    @Min(0)
    private Integer y;

    @NotNull
    @Pattern(regexp = "^[NESW]$")
    private String direction;

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
}
