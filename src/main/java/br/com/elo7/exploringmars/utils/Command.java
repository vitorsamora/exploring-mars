package br.com.elo7.exploringmars.utils;

public enum Command {
    TURN_RIGHT('R'),
    TURN_LEFT('L'),
    MOVE('M');

    private char symbol;

    private Command(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Command getCommandBySymbol(char symbol) {
        switch (symbol) {
            case 'R':
                return TURN_RIGHT;
            case 'L':
                return TURN_LEFT;
            case 'M':
                return MOVE;
            default:
                return null;
        }
    }
}
