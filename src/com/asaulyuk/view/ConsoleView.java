package com.asaulyuk.view;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;

public class ConsoleView {

    private QuoridorGameLogic logic;

    public ConsoleView(QuoridorGameLogic logic) {
        this.logic = logic;
    }

    public void initialize() {

    }

    public void showMove(Integer x, Integer y) {
        String xCoordinate = Character.toString((char) (x+65));
        String yCoordinate = String.valueOf(y+1);
        System.out.println("move "+xCoordinate+yCoordinate);

    }
    public void showJump(Integer x, Integer y) {
        String xCoordinate = Character.toString((char) (x+65));
        String yCoordinate = String.valueOf(y+1);
        System.out.println("jump "+xCoordinate+yCoordinate);

    }
    public void showWall(Integer x, Integer y, Placement placement) {
        String xCoordinate = Character.toString((char) (x+83));
        String yCoordinate = String.valueOf(y+1);
        String p = Placement.Vertical.equals(placement) ? "v" : "h";
        System.out.println("wall "+xCoordinate+yCoordinate+p);


    }

    public void showPlayers() {
        System.out.printf("Black: X:%d, Y:%d, Wall:%d", logic.getBlackPlayer().getX(), logic.getBlackPlayer().getY(), logic.getBlackPlayer().getWallCountLeft());
        System.out.printf(" White: X:%d, Y:%d, Wall:%d\n", logic.getWhitePlayer().getX(), logic.getWhitePlayer().getY(), logic.getWhitePlayer().getWallCountLeft());
    }

    public void refreshInfo() {
        //
    }
}
