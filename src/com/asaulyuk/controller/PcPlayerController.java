package com.asaulyuk.controller;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.view.ConsoleView;

import java.util.Random;

public class PcPlayerController {

    public PcPlayerController(QuoridorGameLogic gameLogic, ConsoleView view) {
        this.gameLogic = gameLogic;
        this.gameview = view;
    }

    private QuoridorGameLogic gameLogic;
    private ConsoleView gameview;
    private Random random = new Random();
    public boolean doNextMove(){
        if (!gameLogic.isGameStarted()) {
            return false;
        }
        int command = random.nextInt(4);
        if ((command == 0) && (gameLogic.getCurrentPlayer().getWallCountLeft()>0)) {
            // wall
            boolean wallPlaced = false;
            int x = 0;
            int y = 0;
            int p = 0;
            while (!wallPlaced) {
                x = random.nextInt(QuoridorGameLogic.MATRIX_SIZE_X-1);
                y = random.nextInt(QuoridorGameLogic.MATRIX_SIZE_Y-1);
                p = random.nextInt(2);
                wallPlaced = gameLogic.placeWall(x,y,p==0 ? Placement.Horizontal : Placement.Vertical);
                System.out.println("// bot wall("+x+","+y+","+p+") "+wallPlaced);
            }
            gameview.showWall(x,y,p==0 ? Placement.Horizontal : Placement.Vertical);
        } else {
            boolean moveSuccessfull = false;
            int x = 0;
            int y = 0;
            while (!moveSuccessfull) {
                int p = random.nextInt(2);
                int d = random.nextInt(3);
                if (d==2) {
                    d=-1;
                }
                if (p==0) {
                    x = gameLogic.getCurrentPlayer().getX()+d;
                    y = gameLogic.getCurrentPlayer().getY();
                } else {
                    x = gameLogic.getCurrentPlayer().getX();
                    y=gameLogic.getCurrentPlayer().getY()+d;
                }
                moveSuccessfull = gameLogic.move(x,y);
                System.out.println("//bot move("+x+","+y+") "+moveSuccessfull);
            }
            gameview.showMove(x,y);
        }
        return true;

    }
}
