package com.sidslab.siddhartha.tictactoe;

public class TicTacToe {
    enum GameStatus { IN_PROGRESS, PLAYER_ONE_WIN, PLAYER_TWO_WIN, TIE_GAME}

    private int[][] gameState;
    private int currentPlayer;
    private int numTurns;

    TicTacToe(){}

    void newGame(){
        currentPlayer = 1;
        numTurns = 0;
        gameState = new int[3][3];
        for(int x = 0; x < gameState.length; x++){
            for(int y = 0; y < gameState[x].length; y++){
                gameState[x][y] = -1;
            }
        }
    }

    boolean getCurrentGameStatus(){
        return gameState != null;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isMoveValid(int x, int y) {
        return gameState[x][y] == -1;
    }

    public GameStatus updateGameState(int x, int y){
        gameState[x][y] = currentPlayer;
        if(checkForWin(x,y)){
            return (currentPlayer == 1) ? GameStatus.PLAYER_ONE_WIN : GameStatus.PLAYER_TWO_WIN;
        }

        if(++numTurns == 9) return GameStatus.TIE_GAME;
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        return GameStatus.IN_PROGRESS;
    }

    private boolean checkForWin(int x, int y){
        return (checkHorizontal(y) || checkVertical(x) || checkDiagonalLTRB() || checkDiagonaLBRT());
    }

    private boolean checkHorizontal(int row){
        for (int[] aGameState : gameState) {
            if (aGameState[row] != currentPlayer) return false;
        }
        return true;
    }

    private boolean checkVertical(int col){
        for(int y = 0; y < gameState[0].length; y++){
            if(gameState[col][y] != currentPlayer) return false;
        }
        return true;
    }

    private boolean checkDiagonalLTRB() {
        for(int x = 0, y = 0; x < gameState.length; x++, y++){
            if(gameState[x][y] != currentPlayer) return false;
        }
        return true;
    }

    private boolean checkDiagonaLBRT() {
        for (int x = 0, y = 2; x < gameState.length; x++, y--) {
            if (gameState[x][y] != currentPlayer) return false;
        }
        return true;
    }
}