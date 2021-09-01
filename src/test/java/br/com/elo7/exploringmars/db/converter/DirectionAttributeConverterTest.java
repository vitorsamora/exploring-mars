package br.com.elo7.exploringmars.db.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.elo7.exploringmars.utils.Direction;

@SpringBootTest
public class DirectionAttributeConverterTest {
    
    @Test
    public void givenDirection_whenIsNorth_thenReturnNorthCode() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        Direction direction = Direction.NORTH;
        Integer column = converter.convertToDatabaseColumn(direction);
        assertEquals(direction.getCode(), column.intValue());
    }

    @Test
    public void givenDirection_whenIsEast_thenReturnEastCode() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        Direction direction = Direction.EAST;
        Integer column = converter.convertToDatabaseColumn(direction);
        assertEquals(direction.getCode(), column.intValue());
    }

    @Test
    public void givenDirection_whenIsSouth_thenReturnSouthCode() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        Direction direction = Direction.SOUTH;
        Integer column = converter.convertToDatabaseColumn(direction);
        assertEquals(direction.getCode(), column.intValue());
    }

    @Test
    public void givenDirection_whenIsWest_thenReturnWestCode() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        Direction direction = Direction.WEST;
        Integer column = converter.convertToDatabaseColumn(direction);
        assertEquals(direction.getCode(), column.intValue());
    }

    @Test
    public void givenDirection_whenIsNull_thenReturnNull() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        Direction direction = null;
        Integer column = converter.convertToDatabaseColumn(direction);
        assertNull(column);
    }

    @Test
    public void givenCode_whenIsNorth_thenReturnNorth() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        int code = Direction.NORTH.getCode();
        Direction direction = converter.convertToEntityAttribute(code);
        assertEquals(Direction.NORTH, direction);
    }

    @Test
    public void givenCode_whenIsEast_thenReturnEast() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        int code = Direction.EAST.getCode();
        Direction direction = converter.convertToEntityAttribute(code);
        assertEquals(Direction.EAST, direction);
    }

    @Test
    public void givenCode_whenIsSouth_thenReturnSouth() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        int code = Direction.SOUTH.getCode();
        Direction direction = converter.convertToEntityAttribute(code);
        assertEquals(Direction.SOUTH, direction);
    }

    @Test
    public void givenCode_whenIsWest_thenReturnWest() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        int code = Direction.WEST.getCode();
        Direction direction = converter.convertToEntityAttribute(code);
        assertEquals(Direction.WEST, direction);
    }

    @Test
    public void givenCode_whenIsNull_thenReturnNull() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        Integer code = null;
        Direction direction = converter.convertToEntityAttribute(code);
        assertNull(direction);
    }

    @Test
    public void givenCode_whenIsInvalid_thenReturnNull() throws Exception {
        DirectionAttributeConverter converter = new DirectionAttributeConverter();
        int code = -1;
        Direction direction = converter.convertToEntityAttribute(code);
        assertNull(direction);
    }

}
