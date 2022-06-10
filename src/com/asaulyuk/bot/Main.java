package com.asaulyuk.bot;

import com.asaulyuk.controller.ConsoleController;
import com.asaulyuk.controller.PcPlayerController;
import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.view.ConsoleView;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("//Shutdown hook ran!");
//                System.exit(0);
            }
        });

        FileWriter fw=null;
        try {
            fw = new FileWriter("debug.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        QuoridorGameLogic gameLogic = new QuoridorGameLogic();
        ConsoleView gameView = new ConsoleView(gameLogic);

        ConsoleController controller = new ConsoleController(fw, gameLogic, gameView);
        PcPlayerController pcPlayerController = new PcPlayerController(gameLogic, gameView);

        gameLogic.initializeGame(1);
//        gameLogic.startGame("white");

        gameView.initialize();

        String input="";
        BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!input.equals("exit")) {
                input = s.readLine();
                System.out.println("//bot current player:"+gameLogic.getCurrentPlayer().name);
//                gameView.showPlayers();
                controller.parseCommand(input);
                if (gameLogic.getWinner()!=null) {
                    System.out.println("//Winner: "+gameLogic.getWinner().name);
                    System.exit(0);
                }

                if (gameLogic.getCurrentPlayer().isUserPlayer) {
                    System.out.println("//bot doing move current:" + gameLogic.getCurrentPlayer().name);
                    pcPlayerController.doNextMove();
                }
                if (gameLogic.getWinner()!=null) {
                    System.out.println("//Winner: "+gameLogic.getWinner().name);
                    System.exit(0);
                }
            }
        } finally {
            if (fw!=null) {
                try {
                    fw.close();
                    s.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

/*
        //System.out.println(gameLogic.getCurrentPlayerColor());
        //White
        Boolean wallResult = gameLogic.placeWall(3,0, Placement.Horizontal);
        gameView.showWall(3,0,Placement.Horizontal);
//        gameView.refreshInfo();
        Thread.sleep(200);

        //Black
        gameLogic.placeWall(1,5, Placement.Vertical);
        gameView.showWall(1,5,Placement.Vertical);
        gameView.refreshInfo();
        Thread.sleep(200);

        //White
        gameLogic.placeWall(2,7, Placement.Horizontal);
        gameView.showWall(2,7,Placement.Horizontal);
        gameView.refreshInfo();
        Thread.sleep(200);
//        System.out.println("Wall result:" + wallResult);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

//        System.out.println("Move result:" + gameLogic.move(4,7));
        //Black
        gameLogic.move(4,7);
        gameView.showMove(4,7);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //White
//        System.out.println("Move result:" + gameLogic.move(5,0));
        gameLogic.move(5,0);
        gameView.showMove(5,0);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //Black
//        System.out.println("Move result:" + );
        gameLogic.move(4,6);
        gameView.showMove(4,6);
//        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //White
        System.out.println("Move result:" + gameLogic.move(5,1));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //Black
        System.out.println("Move result:" + gameLogic.move(4,5));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //White
        System.out.println("Move result:" + gameLogic.move(5,2));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //Black
        System.out.println("Move result:" + gameLogic.move(5,5));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //White
        System.out.println("Move result:" + gameLogic.move(5,3));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

        //Black
        System.out.println("Move result:" + gameLogic.move(5,4));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);

//        System.out.println("Move result:" + gameLogic.move(5,4));
//        System.out.println(gameLogic.getCurrentPlayerColor());
//        gameView.refreshInfo();
//        Thread.sleep(200);

        //White
        System.out.println(gameLogic.getCurrentPlayerColor());
        System.out.println("Move result:" + gameLogic.jump(5,5));
        System.out.println(gameLogic.getCurrentPlayerColor());
        gameView.refreshInfo();
        Thread.sleep(200);
*/
    }
}
