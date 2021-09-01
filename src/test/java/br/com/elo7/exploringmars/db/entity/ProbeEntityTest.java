package br.com.elo7.exploringmars.db.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProbeEntityTest {
    
    private ProbeEntity createProbe(long mapId, int x, int y) {
        ProbeEntity entity = new ProbeEntity();
        entity.setMapId(mapId);
        entity.setX(x);
        entity.setY(y);
        return entity;
    }

    @Test
    public void givenProbeEntities_whenSameAttributes_thenEquals() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(1, 1, 1);
        assertTrue(entity.equals(entity2));
    }

    @Test
    public void givenProbeEntities_whenNotSameMapId_thenNotEquals() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(2, 1, 1);
        assertFalse(entity.equals(entity2));
    }

    @Test
    public void givenProbeEntities_whenNotSameXCoordinate_thenNotEquals() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(1, 2, 1);
        assertFalse(entity.equals(entity2));
    }

    @Test
    public void givenProbeEntities_whenNotSameYCoordinate_thenNotEquals() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(1, 1, 2);
        assertFalse(entity.equals(entity2));
    }

    @Test
    public void givenProbeEntities_whenSameCoordinates_thenSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(1, 1, 1);
        assertEquals(entity.hashCode(), entity2.hashCode());
    }

    @Test
    public void givenProbeEntities_whenNotSameXCoordinate_thenNotSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(1, 2, 1);
        assertNotEquals(entity.hashCode(), entity2.hashCode());
    }

    @Test
    public void givenProbeEntities_whenNotSameYCoordinate_thenNotSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(1, 1, 1);
        ProbeEntity entity2 = createProbe(1, 1, 2);
        assertNotEquals(entity.hashCode(), entity2.hashCode());
    }

}
