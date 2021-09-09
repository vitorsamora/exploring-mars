package br.com.elo7.exploringmars.concurrency;

import br.com.elo7.exploringmars.db.entity.MapEntity;

public class MapCacheItem {
    
    private MapEntity map;

    private int counter;

    public MapCacheItem(MapEntity map) {
        this.map = map;
        this.counter = 1;
    }

    public MapEntity getMap() {
        return map;
    }

    public void setMap(MapEntity map) {
        this.map = map;
    }

    public void incrementCounter() {
        counter++;
    }

    public void decrementCounter() {
        counter--;
    }

    public boolean isCounterZero() {
        return counter == 0;
    }

}
