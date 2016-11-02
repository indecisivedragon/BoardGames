package lili.boardgames.gameMode;

import java.util.ArrayList;

/**
 * Created by Oscar on 24/08/2016.
 */
public interface Game {

    ArrayList<Player> players = new ArrayList<>();
    int currentPlayer = -1;

    int numPlayers = 2;

    int boardSize = 0;
    int[][] board = new int[0][0];

    //gets the board
    int[][] getBoard();
    //returns the game piece on circle c
    int getPiece(int c);

    //human move
    void makeMove(int c);
    //AI move
    void makeAIMove();

    String getGameMessage();
}
