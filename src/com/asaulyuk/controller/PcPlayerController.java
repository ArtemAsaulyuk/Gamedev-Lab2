package com.asaulyuk.controller;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;

import java.util.Random;

public class PcPlayerController {

    public PcPlayerController(QuoridorGameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    private QuoridorGameLogic gameLogic;
    private Random random = new Random();
    public boolean doNextMove(){
        if (!gameLogic.isGameStarted()) {
            return false;
        }
        int command = random.nextInt(4);
        if ((command == 0) && (gameLogic.getCurrentPlayer().getWallCountLeft()>0)) {
            // wall
            boolean wallPlaced = false;
            while (!wallPlaced) {
                int x = random.nextInt(QuoridorGameLogic.MATRIX_SIZE_X-1);
                int y = random.nextInt(QuoridorGameLogic.MATRIX_SIZE_Y-1);
                int p = random.nextInt(2);
                wallPlaced = gameLogic.placeWall(x,y,p==0 ? Placement.Horizontal : Placement.Vertical);
                System.out.println("wall("+x+","+y+","+p+") "+wallPlaced);
            }
        } else {
            boolean moveSuccessfull = false;
            int x;
            int y;
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
                System.out.println("move("+x+","+y+") "+moveSuccessfull);
            }
        }
        return true;

    }
}
