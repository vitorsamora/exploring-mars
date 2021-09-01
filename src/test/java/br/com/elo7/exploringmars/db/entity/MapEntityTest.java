package br.com.elo7.exploringmars.db.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapEntityTest {
    
    private static final int MAX_SIZE = 100;

    @Test
    public void givenMap_whenCoordinatesInBounds_thenNotOutOfBounds() throws Exception {
        MapEntity mapEntity = new MapEntity();
        mapEntity.setMaxX(MAX_SIZE);
        mapEntity.setMaxY(MAX_SIZE);
        assertFalse(mapEntity.isOutOfBounds(1, 1));
    }

    @Test
    public void givenMap_whenXCoordinateOutOfBounds_thenOutOfBounds() throws Exception {
        MapEntity mapEntity = new MapEntity();
        mapEntity.setMaxX(MAX_SIZE);
        mapEntity.setMaxY(MAX_SIZE);
        assertTrue(mapEntity.isOutOfBounds(MAX_SIZE + 1, 1));
    }

    @Test
    public void givenMap_whenYCoordinateOutOfBounds_thenOutOfBounds() throws Exception {
        MapEntity mapEntity = new MapEntity();
        mapEntity.setMaxX(MAX_SIZE);
        mapEntity.setMaxY(MAX_SIZE);
        assertTrue(mapEntity.isOutOfBounds(1, MAX_SIZE + 1));
    }

}
