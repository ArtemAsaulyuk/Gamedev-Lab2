package com.asaulyuk.controller;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.model.Vershina;
import com.asaulyuk.view.ConsoleView;

public class ConsoleController {

    private QuoridorGameLogic logic;
    private ConsoleView view;

    public ConsoleController(QuoridorGameLogic logic, ConsoleView view) {

        this.logic = logic;
        this.view = view;
    }

    public void parseCommand(String input) {
        String s = input.toUpperCase();
        if (s.startsWith("MOVE")) {
            Vershina v = getCoordinates(s);
            logic.move(v.getX(), v.getY());


        } else if (s.startsWith("JUMP")) {
            Vershina v = getCoordinates(s);
            logic.jump(v.getX(), v.getY());
        } else if (s.startsWith("WALL")) {
            Vershina v = getCoordinates(s);
            Placement placement = getWallPlacement(s);
            logic.placeWall(v.getX(), v.getY(), placement);
        } else if (s.startsWith("WHITE")) {
            logic.startGame("white");
//            logic.move(logic.getWhitePlayer().getX(), logic.getWhitePlayer().getY()-1);
//            view.showMove(logic.getWhitePlayer().getX(), logic.getWhitePlayer().getY());


        } else if (s.startsWith("BLACK")) {
            logic.startGame("black");

        } else {
            System.out.println("ERROR: command unknown:" + s);
        }
    }

    private Vershina getCoordinates(String input) {
        String s = input.substring(5,7);
//        A1 -> 0,0
        char xCoordinateChar = s.charAt(0);
        Integer xCoordinate = xCoordinateChar - 65;
        if (xCoordinate> 10) {
            xCoordinate = xCoordinateChar - 83;
        }
        Integer yCoordinate = Integer.valueOf(s.substring(1))-1;
        return new Vershina(xCoordinate, yCoordinate);

    }

    private Placement getWallPlacement(String input) {
        if (input.length()!=8) {
            System.out.println("ERROR: Placement incorrect");
            return null;
        }

        if (Character.getNumericValue(input.charAt(7)) == 72) {
            return Placement.Horizontal;
        } else {
            return Placement.Vertical;
        }


    }
}
