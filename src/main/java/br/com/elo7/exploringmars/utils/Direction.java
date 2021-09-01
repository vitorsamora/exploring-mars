package br.com.elo7.exploringmars.utils;

public enum Direction {
    NORTH(0, "N"),
    EAST(1, "E"),
    SOUTH(2, "S"),
    WEST(3, "W");

    private int code;
    private String symbol;

    private Direction(int code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public int getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Direction getDirectionByCode(int code) {
        switch (code) {
            case 0:
                return NORTH;
            case 1:
                return EAST;
            case 2:
                return SOUTH;
            case 3:
                return WEST;
            default:
                return null;
        }
	}

    public static Direction getDirectionBySymbol(String symbol) {
        if (symbol == null) {
            return null;
        }

        switch (symbol) {
            case "N":
                return NORTH;
            case "E":
                return EAST;
            case "S":
                return SOUTH;
            case "W":
                return WEST;
            default:
                return null;
        }
	}
}
