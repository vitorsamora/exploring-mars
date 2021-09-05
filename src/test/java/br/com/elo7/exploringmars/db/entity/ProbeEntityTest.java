package br.com.elo7.exploringmars.db.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.elo7.exploringmars.utils.Direction;

@SpringBootTest
public class ProbeEntityTest {
    
    private ProbeEntity createProbe(UUID resourceId, long mapId, int x, int y, Direction direction) {
        if (resourceId == null) {
            resourceId = UUID.randomUUID();
        }
        ProbeEntity entity = new ProbeEntity();
        entity.setResourceId(resourceId);
        entity.setMapId(mapId);
        entity.setX(x);
        entity.setY(y);
        entity.setDirection(direction);
        return entity;
    }

    @Test
    public void givenProbes_whenSameAttributes_thenEquals() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 1, 1, Direction.NORTH);
        assertTrue(entity.equals(entity2));
    }

    @Test
    public void givenProbes_whenNotSameMapId_thenNotEquals() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 2, 1, 1, Direction.NORTH);
        assertFalse(entity.equals(entity2));
    }

    @Test
    public void givenProbes_whenNotSameXCoordinate_thenNotEquals() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 2, 1, Direction.NORTH);
        assertFalse(entity.equals(entity2));
    }

    @Test
    public void givenProbes_whenNotSameYCoordinate_thenNotEquals() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 1, 2, Direction.NORTH);
        assertFalse(entity.equals(entity2));
    }

    @Test
    public void givenProbes_whenSameCoordinates_thenSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 1, 1, Direction.NORTH);
        assertEquals(entity.hashCode(), entity2.hashCode());
    }

    @Test
    public void givenProbes_whenSameCoordinatesAndDifferentDirections_thenSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 1, 1, Direction.EAST);
        assertEquals(entity.hashCode(), entity2.hashCode());
    }

    @Test
    public void givenProbes_whenNotSameXCoordinate_thenNotSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 2, 1, Direction.NORTH);
        assertNotEquals(entity.hashCode(), entity2.hashCode());
    }

    @Test
    public void givenProbes_whenNotSameYCoordinate_thenNotSameHashCode() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        ProbeEntity entity2 = createProbe(null, 1, 1, 2, Direction.NORTH);
        assertNotEquals(entity.hashCode(), entity2.hashCode());
    }

    @Test
    public void givenProbe_whenSameResourceId_thenSameResourceId() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        assertTrue(entity.checkResourceId(entity.getResourceId()));
    }

    @Test
    public void givenProbe_whenNotSameResourceId_thenNotSameResourceId() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        UUID anotherResourceId = UUID.randomUUID();
        assertFalse(entity.checkResourceId(anotherResourceId));
    }

    @Test
    public void givenProbe_whenNullResourceId_thenNotSameResourceId() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        assertFalse(entity.checkResourceId(null));
    }

    @Test
    public void givenProbeFacingNorth_whenTurnRight_thenFaceEast() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        entity.turnRight();
        assertEquals(Direction.EAST, entity.getDirection());
    }

    @Test
    public void givenProbeFacingNorth_whenTurnLeft_thenFaceWest() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        entity.turnLeft();
        assertEquals(Direction.WEST, entity.getDirection());
    }

    @Test
    public void givenProbeFacingNorth_whenMove_thenIncrementY() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        entity.move();
        assertEquals(2, entity.getY());
    }

    @Test
    public void givenProbeFacingNorth_whenMoveBack_thenDecrementY() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.NORTH);
        entity.moveBack();
        assertEquals(0, entity.getY());
    }

    @Test
    public void givenProbeFacingEast_whenTurnRight_thenFaceSouth() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.EAST);
        entity.turnRight();
        assertEquals(Direction.SOUTH, entity.getDirection());
    }

    @Test
    public void givenProbeFacingEast_whenTurnLeft_thenFaceNorth() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.EAST);
        entity.turnLeft();
        assertEquals(Direction.NORTH, entity.getDirection());
    }

    @Test
    public void givenProbeFacingEast_whenMove_thenIncrementX() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.EAST);
        entity.move();
        assertEquals(2, entity.getX());
    }

    @Test
    public void givenProbeFacingEast_whenMoveBack_thenDecrementX() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.EAST);
        entity.moveBack();
        assertEquals(0, entity.getX());
    }

    @Test
    public void givenProbeFacingSouth_whenTurnRight_thenFaceWest() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.SOUTH);
        entity.turnRight();
        assertEquals(Direction.WEST, entity.getDirection());
    }

    @Test
    public void givenProbeFacingSouth_whenTurnLeft_thenFaceEast() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.SOUTH);
        entity.turnLeft();
        assertEquals(Direction.EAST, entity.getDirection());
    }

    @Test
    public void givenProbeFacingSouth_whenMove_thenDecrementY() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.SOUTH);
        entity.move();
        assertEquals(0, entity.getY());
    }

    @Test
    public void givenProbeFacingSouth_whenMoveBack_thenIncrementY() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.SOUTH);
        entity.moveBack();
        assertEquals(2, entity.getY());
    }

    @Test
    public void givenProbeFacingWest_whenTurnRight_thenFaceNorth() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.WEST);
        entity.turnRight();
        assertEquals(Direction.NORTH, entity.getDirection());
    }

    @Test
    public void givenProbeFacingWest_whenTurnLeft_thenFaceSouth() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.WEST);
        entity.turnLeft();
        assertEquals(Direction.SOUTH, entity.getDirection());
    }

    @Test
    public void givenProbeFacingWest_whenMove_thenDecrementX() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.WEST);
        entity.move();
        assertEquals(0, entity.getX());
    }

    @Test
    public void givenProbeFacingWest_whenMoveBack_thenIncrementX() throws Exception{
        ProbeEntity entity = createProbe(null, 1, 1, 1, Direction.WEST);
        entity.moveBack();
        assertEquals(2, entity.getX());
    }

}
