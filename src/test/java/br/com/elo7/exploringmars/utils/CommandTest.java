package br.com.elo7.exploringmars.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommandTest {

    @Test
    public void givenSymbol_whenIsTurnRight_thenReturnTurnRight() throws Exception {
        char symbol = Command.TURN_RIGHT.getSymbol();
        Command command = Command.getCommandBySymbol(symbol);
        assertEquals(Command.TURN_RIGHT, command);
    }

    @Test
    public void givenSymbol_whenIsTurnLeft_thenReturnTurnLeft() throws Exception {
        char symbol = Command.TURN_LEFT.getSymbol();
        Command command = Command.getCommandBySymbol(symbol);
        assertEquals(Command.TURN_LEFT, command);
    }

    @Test
    public void givenSymbol_whenIsMove_thenReturnMove() throws Exception {
        char symbol = Command.MOVE.getSymbol();
        Command command = Command.getCommandBySymbol(symbol);
        assertEquals(Command.MOVE, command);
    }

    @Test
    public void givenSymbol_whenIsInvalid_thenReturnNull() throws Exception {
        char symbol = 'O';
        Command command = Command.getCommandBySymbol(symbol);
        assertNull(command);
    }

}
