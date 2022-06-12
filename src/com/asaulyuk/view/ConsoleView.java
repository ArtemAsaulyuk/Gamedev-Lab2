package com.asaulyuk.view;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;

public class ConsoleView {

    private QuoridorGameLogic logic;

    private boolean debug_log;

    public ConsoleView(QuoridorGameLogic logic) {
        this.logic = logic;
    }

    public void initialize() {

    }

    public void showMove(Integer x, Integer y) {
        String xCoordinate = Character.toString((char) (x + 65));
        String yCoordinate = String.valueOf(y + 1);
        showLog("bot send move: " + xCoordinate + yCoordinate);
        System.out.println("move " + xCoordinate + yCoordinate);

    }

    public void showJump(Integer x, Integer y) {
        String xCoordinate = Character.toString((char) (x + 65));
        String yCoordinate = String.valueOf(y + 1);
        showLog("bot send jump " + xCoordinate + yCoordinate);
        System.out.println("jump " + xCoordinate + yCoordinate);

    }

    public void showWall(Integer x, Integer y, Placement placement) {
        String xCoordinate = Character.toString((char) (x + 83));
        String yCoordinate = String.valueOf(y + 1);
        String p = Placement.Vertical.equals(placement) ? "v" : "h";
        showLog("bot send wall " + xCoordinate + yCoordinate + p);
        System.out.println("wall " + xCoordinate + yCoordinate + p);


    }

    public void showPlayers() {
                System.out.printf("//Black: X:%d, Y:%d, Wall:%d", logic.getBlackPlayer().getX(), logic.getBlackPlayer().getY(), logic.getBlackPlayer().getWallCountLeft());
                System.out.printf("//White: X:%d, Y:%d, Wall:%d\n", logic.getWhitePlayer().getX(), logic.getWhitePlayer().getY(), logic.getWhitePlayer().getWallCountLeft());
    }

    public void showLog(String s) {
        if (!debug_log) {
            return;
        }

        if (!s.startsWith("//")) {
            s = "// ".concat(s);
        }
        System.out.println(s);
    }

    public void setDebug_log(boolean debug_log) {
        this.debug_log = debug_log;
    }

}
