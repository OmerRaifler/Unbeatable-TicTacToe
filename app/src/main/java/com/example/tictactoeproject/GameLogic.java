package com.example.tictactoeproject;

import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {

    private int[][] gameBoard;

    private String[] playerNames = {"Player 1", "computer"};

    private Button playAgainBTN;
    private TextView playerTurn;

    private int player = 1;

    private int score = 0;
    int bestrow, bestcol;


    GameLogic() {
        gameBoard = new int[3][3];
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
    }


    public boolean checkDraw()
    {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] == 0)
                    return false;
            }
        }
        return true;
    }


    public boolean evaluateBoard(int playerNumber) {

        for (int r = 0; r < 3; r++)
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] &&
                    gameBoard[r][0] != 0) {
                if (gameBoard[r][0] == playerNumber)
                    return true;
            }
        for (int c = 0; c < 3; c++)
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c] &&
                    gameBoard[0][c] != 0) {
                if (gameBoard[0][c] == playerNumber)
                    return true;
            }
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0) {
            if (gameBoard[0][0] == playerNumber)
                return true;
        }
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0) {
            if (gameBoard[2][0] == playerNumber)
                return true;
        }
        return false;
    }

    public void bestMove(){

        int bestScore = -1000;
        bestrow = 0;
        bestcol = 0;

        for (int r = 0; r<3; r++) {
            for (int c = 0; c < 3; c++) {
                if(gameBoard[r][c] == 0) {
                    gameBoard[r][c] = 2;
                    score = miniMax(gameBoard, false);
                    gameBoard[r][c] = 0;
                    if(score > bestScore) {
                        bestScore = score;
                        bestrow = r;
                        bestcol = c;
                    }
                }
            }
        }
        setPlayer(getPlayer() - 1);
        gameBoard[bestrow][bestcol] = 2;
        winnerCheck(); //check winner after AI move
    }

    private int miniMax(int[][] gameBoard, boolean isMaximizing){
        if(evaluateBoard(1))
            return -10;
        if(evaluateBoard(2))
            return 10;
        if(checkDraw())
            return 0;

        if(isMaximizing){
            int bestScore = -1000;
            for (int r = 0; r<3; r++) {
                for (int c = 0; c < 3; c++) {
                    if(gameBoard[r][c] == 0) {
                        gameBoard[r][c] = 2;
                        score = miniMax(gameBoard, false);
                        gameBoard[r][c] = 0;
                        if(score > bestScore) {
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;
        }
        else {
            int bestScore = 800;
            for (int r = 0; r<3; r++) {
                for (int c = 0; c < 3; c++) {
                    if(gameBoard[r][c] == 0) {
                        gameBoard[r][c] = 1;
                        score = miniMax(gameBoard, true);
                        gameBoard[r][c] = 0;
                        if(score < bestScore) {
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    public boolean updateGameBoard(int row, int col){
        if(gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;

            return true;
        }
        else {
            return false;
        }
    }

    public boolean winnerCheck() {

        if (evaluateBoard(1)){
            playerTurn.setText((playerNames[0] + " Won!"));
            return true;
        }
        else if(evaluateBoard(2)){
            playerTurn.setText((playerNames[1] + " Won!"));
            return true;
        }
        else if(checkDraw()) {
            playerTurn.setText("Tie Game!");
            return true;
        }
        return false;
    }

    public void resetGame(){
        for (int r = 0; r<3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
        player = 1;
        playerTurn.setText(R.string.match_title);
    }

    public int[][] getGameBoard(){
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }
}
