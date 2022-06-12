package com.asaulyuk.bot;

import com.asaulyuk.controller.ConsoleController;
import com.asaulyuk.controller.PcPlayerControllerV2;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.view.ConsoleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.lang.System.exit;

public class Main {

    private static void checkWinner(QuoridorGameLogic gameLogic, ConsoleView gameView) throws InterruptedException {
        if (gameLogic.getWinner() != null) {
            gameView.showLog("Winner: " + gameLogic.getWinner().name);
            Thread.sleep(500);
            exit(0);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        QuoridorGameLogic gameLogic = new QuoridorGameLogic();
        ConsoleView gameView = new ConsoleView(gameLogic);
        if (Arrays.asList(args).contains("debug")) {
            gameView.setDebug_log(true);
        }
        ConsoleController controller = new ConsoleController(gameLogic, gameView);
        PcPlayerControllerV2 pcPlayerControllerV2 = new PcPlayerControllerV2(gameLogic, gameView);

        gameLogic.initializeGame(1);

        gameView.initialize();

        String input = "";
        BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
        while (!input.equals("exit")) {
            input = s.readLine();
            gameView.showLog("bot current player:" + gameLogic.getCurrentPlayer().name);
            controller.parseCommand(input);
            checkWinner(gameLogic, gameView);

            if (gameLogic.getCurrentPlayer().isUserPlayer) {
                gameView.showLog("bot doing move current:" + gameLogic.getCurrentPlayer().name);
                pcPlayerControllerV2.doNextMove();
                checkWinner(gameLogic, gameView);
            }
        }

    }
}
