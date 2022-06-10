package com.asaulyuk.controller;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.model.Vershina;
import com.asaulyuk.view.ConsoleView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class ConsoleController {

    private QuoridorGameLogic logic;
    private ConsoleView view;
    private FileWriter fw;

    public ConsoleController(FileWriter fw, QuoridorGameLogic logic, ConsoleView view) {

        this.logic = logic;
        this.view = view;
        this.fw=fw;
        write("Start game");
    }

    private void write(String s) {
        try {
            fw.write(Instant.now().toString().concat(": ").concat(s));
            fw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseCommand(String input) {
        String s = input.toUpperCase();
        System.out.println("//bot received: " + s);
        write("Input: " +s);
        if (s.startsWith("MOVE")) {
            Vershina v = getCoordinates(s);
            boolean res = logic.move(v.getX(), v.getY());
            System.out.println("//moving to "+v.getX().toString()+v.getY().toString()+ " result:"+res);
//            view.showMove(v.getX(), v.getY());


        } else if (s.startsWith("JUMP")) {
            Vershina v = getCoordinates(s);
            logic.jump(v.getX(), v.getY());
        } else if (s.startsWith("WALL")) {
            Vershina v = getCoordinates(s);
            Placement placement = getWallPlacement(s);
            System.out.println("//received wall: "+v.getX().toString()+v.getY().toString()+ placement.name());
            logic.placeWall(v.getX(), v.getY(), placement);
        } else if (s.startsWith("WHITE")) {
            logic.startGame("white");
//            logic.move(logic.getWhitePlayer().getX(), logic.getWhitePlayer().getY()-1);
//            view.showMove(logic.getWhitePlayer().getX(), logic.getWhitePlayer().getY());


        } else if (s.startsWith("BLACK")) {
            logic.startGame("black");

        } else if (s.startsWith("EXIT")){
            write("Exit");
        } else {
            System.out.println("//bot ERROR: command unknown:" + s);
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


        if (input.endsWith("H")) {
            return Placement.Horizontal;
        } else {
            return Placement.Vertical;
        }


    }
}
