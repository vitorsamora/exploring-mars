package br.com.elo7.exploringmars.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DirectionTest {
    
    @Test
    public void givenCode_whenIsNorth_thenReturnNorth() throws Exception {
        int code = Direction.NORTH.getCode();
        Direction direction = Direction.getDirectionByCode(code);
        assertEquals(Direction.NORTH, direction);
    }

    @Test
    public void givenCode_whenIsEast_thenReturnEast() throws Exception {
        int code = Direction.EAST.getCode();
        Direction direction = Direction.getDirectionByCode(code);
        assertEquals(Direction.EAST, direction);
    }

    @Test
    public void givenCode_whenIsSouth_thenReturnSouth() throws Exception {
        int code = Direction.SOUTH.getCode();
        Direction direction = Direction.getDirectionByCode(code);
        assertEquals(Direction.SOUTH, direction);
    }

    @Test
    public void givenCode_whenIsWest_thenReturnWest() throws Exception {
        int code = Direction.WEST.getCode();
        Direction direction = Direction.getDirectionByCode(code);
        assertEquals(Direction.WEST, direction);
    }

    @Test
    public void givenCode_whenIsInvalid_thenReturnNull() throws Exception {
        int code = -1;
        Direction direction = Direction.getDirectionByCode(code);
        assertNull(direction);
    }

    @Test
    public void givenSymbol_whenIsNorth_thenReturnNorth() throws Exception {
        String symbol = Direction.NORTH.getSymbol();
        Direction direction = Direction.getDirectionBySymbol(symbol);
        assertEquals(Direction.NORTH, direction);
    }

    @Test
    public void givenSymbol_whenIsEast_thenReturnEast() throws Exception {
        String symbol = Direction.EAST.getSymbol();
        Direction direction = Direction.getDirectionBySymbol(symbol);
        assertEquals(Direction.EAST, direction);
    }

    @Test
    public void givenSymbol_whenIsSouth_thenReturnSouth() throws Exception {
        String symbol = Direction.SOUTH.getSymbol();
        Direction direction = Direction.getDirectionBySymbol(symbol);
        assertEquals(Direction.SOUTH, direction);
    }

    @Test
    public void givenSymbol_whenIsWest_thenReturnWest() throws Exception {
        String symbol = Direction.WEST.getSymbol();
        Direction direction = Direction.getDirectionBySymbol(symbol);
        assertEquals(Direction.WEST, direction);
    }

    @Test
    public void givenSymbol_whenIsNull_thenReturnNull() throws Exception {
        String symbol = null;
        Direction direction = Direction.getDirectionBySymbol(symbol);
        assertNull(direction);
    }

    @Test
    public void givenSymbol_whenIsInvalid_thenReturnNull() throws Exception {
        String symbol = "NORTH";
        Direction direction = Direction.getDirectionBySymbol(symbol);
        assertNull(direction);
    }

}
