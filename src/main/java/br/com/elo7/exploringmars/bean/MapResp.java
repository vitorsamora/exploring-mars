package br.com.elo7.exploringmars.bean;

import java.io.Serializable;

public class MapResp implements Serializable {

    private Long id;
    
    private Integer maxX;

    private Integer maxY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
