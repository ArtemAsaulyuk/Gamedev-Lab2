package com.asaulyuk.bot;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.view.ConsoleView;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        QuoridorGameLogic gameLogic = new QuoridorGameLogic();
        ConsoleView gameView = new ConsoleView(gameLogic);


        gameLogic.initializeGame(2);
        gameLogic.startGame("white");

        gameView.initialize();

        //System.out.println(gameLogic.getCurrentPlayerColor());
        Boolean wallResult = gameLogic.placeWall(3,0, Placement.Horizontal);
        gameView.showWall(3,0,Placement.Horizontal);
//        gameView.refreshInfo();
        Thread.sleep(200);

        gameLogic.placeWall(1,5, Placement.Vertical);
        gameView.showWall(1,5,Placement.Vertical);
        gameView.refreshInfo();
        Thread.sleep(200);

        gameLogic.placeWall(2,7, Placement.Horizontal);
        gameView.showWall(2,7,Placement.Horizontal);
        gameView.refreshInfo();
        Thread.sleep(200);
//        System.out.println("Wall result:" + wallResult);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

//        System.out.println("Move result:" + gameLogic.move(4,7));
        gameLogic.move(4,7);
        gameView.showMove(4,7);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

//        System.out.println("Move result:" + gameLogic.move(5,0));
        gameLogic.move(5,0);
        gameView.showMove(5,0);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

//        System.out.println("Move result:" + );
        gameLogic.move(4,6);
        gameView.showMove(4,6);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(5,1));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(4,5));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(5,2));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(5,5));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(5,3));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(5,4));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.move(5,4));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        System.out.println("Move result:" + gameLogic.jump(5,5));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);
    }
}
