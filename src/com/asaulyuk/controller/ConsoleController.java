package com.asaulyuk.controller;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.model.Vershina;
import com.asaulyuk.view.ConsoleView;

public class ConsoleController {

    private QuoridorGameLogic logic;
    private ConsoleView gameView;

    public ConsoleController(QuoridorGameLogic logic, ConsoleView gameView) {
        this.logic = logic;
        this.gameView = gameView;
    }

    public void parseCommand(String input) {
        String s = input.toUpperCase();
        gameView.showLog("bot received: " + s);
        if (s.startsWith("MOVE")) {
            Vershina v = getCoordinates(s);
            boolean res = logic.move(v.getX(), v.getY());
            gameView.showLog("moving to " + v.getX().toString() + v.getY().toString() + " result:" + res);
        } else if (s.startsWith("JUMP")) {
            Vershina v = getCoordinates(s);
            logic.jump(v.getX(), v.getY());
        } else if (s.startsWith("WALL")) {
            Vershina v = getCoordinates(s);
            Placement placement = getWallPlacement(s);
            gameView.showLog("received wall: " + v.getX().toString() + v.getY().toString() + placement.name());
            logic.placeWall(v.getX(), v.getY(), placement);
        } else if (s.startsWith("WHITE")) {
            logic.startGame("white");
        } else if (s.startsWith("BLACK")) {
            logic.startGame("black");
        } else if (s.startsWith("EXIT")) {
            System.exit(0);
        } else {
            gameView.showLog("bot ERROR: command unknown:" + s);
        }
    }

    private Vershina getCoordinates(String input) {
        String s = input.substring(5, 7);
        //        A1 -> 0,0
        char xCoordinateChar = s.charAt(0);
        Integer xCoordinate = xCoordinateChar - 65;
        if (xCoordinate > 10) {
            xCoordinate = xCoordinateChar - 83;
        }
        Integer yCoordinate = Integer.valueOf(s.substring(1)) - 1;
        return new Vershina(xCoordinate, yCoordinate);

    }

    private Placement getWallPlacement(String input) {
        if (input.length() != 8) {
            gameView.showLog("ERROR: Placement incorrect");
            return null;
        }

        if (input.endsWith("H")) {
            return Placement.Horizontal;
        } else {
            return Placement.Vertical;
        }
    }
}
