package com.asaulyuk.controller;

import com.asaulyuk.model.Placement;
import com.asaulyuk.model.QuoridorGameLogic;
import com.asaulyuk.model.Rebro;
import com.asaulyuk.model.Vershina;
import com.asaulyuk.view.ConsoleView;
import org.apache.commons.graph.MutableGraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class PcPlayerControllerV2 implements PlayerController {
    private QuoridorGameLogic gameLogic;
    private ConsoleView gameView;
    private Random random = new Random();

    public PcPlayerControllerV2(QuoridorGameLogic gameLogic, ConsoleView gameView) {
        this.gameLogic = gameLogic;
        this.gameView = gameView;
    }

    @Override
    public boolean doNextMove() {
        if (!gameLogic.isGameStarted()) {
            return false;
        }

        int command = random.nextInt(2);
        gameView.showLog("command:" + command);
        if ((command == 1) && (gameLogic.getCurrentPlayer().getWallCountLeft() > 0) && (gameLogic.getBlackPlayer().getY() > 1) && (gameLogic.getWhitePlayer().getY() < 8)) {
            boolean wallPlaced = false;
            int x = 0;
            int y = 0;
            int p = 0;
            if (gameLogic.getCurrentPlayer().equals(gameLogic.getBlackPlayer())) {
                y = 0;
            } else {
                y = 7;
            }
            int attempts = 0;
            x = 0;
            while (!wallPlaced && attempts < 4) {
                attempts++;

                x = random.nextInt(3);
                if (gameLogic.getOtherPlayer().getX() > 1) {
                    x = gameLogic.getOtherPlayer().getX() - 2 + x;
                }
                x = attempts * 2;
                p = 0;
                if ((x == 6) && gameLogic.getOtherPlayer().getX() < 4) {
                    x = 0;
                }

                wallPlaced = gameLogic.placeWall(x, y, p == 0 ? Placement.Horizontal : Placement.Vertical);
                gameView.showLog("bot wall(" + x + "," + y + "," + p + ") " + wallPlaced);
            }
            if (wallPlaced) {
                gameView.showWall(x, y, p == 0 ? Placement.Horizontal : Placement.Vertical);
                return true;
            }
        }
        Vershina playerCoord = gameLogic.getMatrixVershin()[gameLogic.getCurrentPlayer().getX()][gameLogic.getCurrentPlayer().getY()];
        Vershina moveTo;
        if (gameLogic.getCurrentPlayer().equals(gameLogic.getWhitePlayer())) {
            Vershina topV = new Vershina(-1, -1);
            MutableGraph tempGraph = gameLogic.buildNewGraphForChecks(gameLogic.getGraph(), 0, topV);
            moveTo = getPath2(tempGraph, topV, playerCoord, -1).stream()
                    .filter(vershina -> !vershina.equals(playerCoord))
                    .findFirst().get();
            Vershina finalMoveTo = moveTo;
            tempGraph = gameLogic.buildNewGraphForChecks(gameLogic.getGraph(), 0, topV);
            Vershina moveTo2 = getPath2(tempGraph, topV, moveTo, -1).stream()
                    .filter(vershina -> !vershina.equals(finalMoveTo))
                    .findFirst().get();
            if (moveTo2.equals(playerCoord)) {
                gameView.showLog("detected cycle white");
                tempGraph = gameLogic.buildNewGraphForChecks(gameLogic.getGraph(), 0, topV);
                moveTo = getPath2(tempGraph, topV, playerCoord, 3).stream()
                        .filter(vershina -> !vershina.equals(playerCoord))
                        .findFirst().get();

            }


        } else {
            Vershina bottomV = new Vershina(100, 100);
            MutableGraph tempGraph = gameLogic.buildNewGraphForChecks(gameLogic.getGraph(), QuoridorGameLogic.MATRIX_SIZE_Y - 1, bottomV);
            moveTo = getPath2(tempGraph, bottomV, playerCoord, 1).stream()
                    .filter(vershina -> !vershina.equals(playerCoord))
                    .findFirst().get();
            Vershina finalMoveTo1 = moveTo;
            tempGraph = gameLogic.buildNewGraphForChecks(gameLogic.getGraph(), QuoridorGameLogic.MATRIX_SIZE_Y - 1, bottomV);
            Vershina moveTo2 = getPath2(tempGraph, bottomV, moveTo, 1).stream()
                    .filter(vershina -> !vershina.equals(finalMoveTo1))
                    .findFirst().get();
            if (moveTo2.equals(playerCoord)) {
                gameView.showLog("detected cycle black");
                tempGraph = gameLogic.buildNewGraphForChecks(gameLogic.getGraph(), QuoridorGameLogic.MATRIX_SIZE_Y - 1, bottomV);
                moveTo = getPath2(tempGraph, bottomV, playerCoord, 3).stream()
                        .filter(vershina -> !vershina.equals(playerCoord))
                        .findFirst().get();

            }

        }
        if ((gameLogic.getOtherPlayer().getX().equals(moveTo.getX())) && (gameLogic.getOtherPlayer().getY().equals(moveTo.getY()))) {
            //Jump
            int deltaY = gameLogic.getCurrentPlayer().getY() - moveTo.getY();
            int deltaX = gameLogic.getCurrentPlayer().getX() - moveTo.getX();
            if (deltaX == 0) {
                if (gameLogic.jump(moveTo.getX(), moveTo.getY() - deltaY)) {
                    gameView.showJump(moveTo.getX(), moveTo.getY() - deltaY);
                } else if (gameLogic.jump(moveTo.getX() + 1, moveTo.getY())) {
                    gameView.showJump(moveTo.getX() + 1, moveTo.getY());
                } else if (gameLogic.jump(moveTo.getX() - 1, moveTo.getY())) {
                    gameView.showJump(moveTo.getX() - 1, moveTo.getY());
                } else {
                    gameView.showLog("Jump unsuccessfull " + (moveTo.getX()) + (moveTo.getY()));
                    gameLogic.move(moveTo.getX(), gameLogic.getCurrentPlayer().getY() + deltaY);
                    gameView.showMove(moveTo.getX(), gameLogic.getCurrentPlayer().getY() + deltaY);
                }
            } else {
                if (gameLogic.jump(moveTo.getX() - deltaX, moveTo.getY())) {
                    gameView.showJump(moveTo.getX() - deltaX, moveTo.getY());
                } else if (gameLogic.jump(moveTo.getX(), moveTo.getY() + 1)) {
                    gameView.showJump(moveTo.getX(), moveTo.getY() + 1);
                } else if (gameLogic.jump(moveTo.getX(), moveTo.getY() - 1)) {
                    gameView.showJump(moveTo.getX(), moveTo.getY() - 1);
                } else {
                    gameView.showLog("Jump unsuccessfull " + (moveTo.getX()) + (moveTo.getY()));
                    gameLogic.move(gameLogic.getCurrentPlayer().getX() + deltaX, moveTo.getY());
                    gameView.showMove(gameLogic.getCurrentPlayer().getX() + deltaX, moveTo.getY());

                }
            }

        } else {
            gameLogic.move(moveTo.getX(), moveTo.getY());
            gameView.showMove(moveTo.getX(), moveTo.getY());
        }
        return true;
    }


    public Set<Vershina> getPath2(MutableGraph localGraph, Vershina target, Vershina start, int direction) {
        Set<Vershina> visited = new LinkedHashSet<Vershina>();
        Stack<Vershina> stack = new Stack<Vershina>();
        stack.push(start);
        while (!stack.isEmpty()) {
            Vershina vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                stack.addAll(selectNeighborsByDirection(localGraph, vertex, direction));
            }
            if (vertex.equals(target)) {
                break;
            }
        }

        return visited;


    }

    private List<Vershina> selectNeighborsByDirection(MutableGraph localGraph, Vershina v, int direction) {
        Set<Vershina> vershini = new HashSet<>();
        for (Object r : localGraph.getEdges(v)) {
            Rebro rebro = (Rebro) r;
            if (rebro.getValid()) {
                vershini.addAll(localGraph.getVertices(rebro));
            }
        }
        vershini.remove(v);
        Vershina[] sorted = vershini.toArray(new Vershina[0]);
        for (int i = 0; i < sorted.length - 1; i++) {
            Vershina current = sorted[i];
            Vershina next = sorted[i + 1];
            if (direction == 1) {
                boolean sw = current.getY() > next.getY();
                sw = sw ? sw : (current.getY().equals(next.getY()) && (current.getX() > next.getX()));
                if (sw) {
                    sorted[i] = next;
                    sorted[i + 1] = current;
                }
            } else if (direction == 3) {
                if (current.getX() < next.getX()) {
                    sorted[i] = next;
                    sorted[i + 1] = current;
                }
            } else {
                boolean sw = current.getY() < next.getY();
                sw = sw ? sw : (current.getY().equals(next.getY()) && (current.getX() > next.getX()));
                if (sw) {
                    sorted[i] = next;
                    sorted[i + 1] = current;
                }
            }
        }
        return Arrays.asList(sorted);
    }
}
