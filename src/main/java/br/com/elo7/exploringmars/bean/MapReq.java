package br.com.elo7.exploringmars.bean;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MapReq implements Serializable {
   
    @NotNull
    @Min(0)
    private Integer maxX;

    @NotNull
    @Min(0)
    private Integer maxY;

    public Integer getMaxX() {
        return maxX;
    }

    public void setMaxX(Integer maxX) {
        this.maxX = maxX;
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }
    
}
