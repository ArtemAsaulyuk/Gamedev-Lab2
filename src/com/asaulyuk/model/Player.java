package com.asaulyuk.model;

public class Player {

    public enum PlayerColor {
        BLACK,
        WHITE
    }

    public String name;
    public Integer moveCount;
    public Boolean isUserPlayer = true;
    public PlayerColor playerColor;
    Integer x;
    Integer y;
    Vershina coordinati;
    Integer wallCountLeft;

    public Player(String name, PlayerColor playerColor) {
        this.name = name;
        this.playerColor = playerColor;
        this.moveCount = 0;
        this.wallCountLeft = 10;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getWallCountLeft() {
        return wallCountLeft;
    }
}
