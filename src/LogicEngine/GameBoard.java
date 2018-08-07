package LogicEngine;

import java.util.Arrays;

public class GameBoard {
    long rows;
    long cols;
    long target;
    int[][] board;
    int numOfFreePlaces;

    public void setGameBoard(long i_rows, long i_cols, long i_target)
    {
        rows = i_rows+1;
        cols = i_cols+1;
        target = i_target;
        numOfFreePlaces = (int)rows * (int)cols;
        board = new int[Math.toIntExact(rows)][Math.toIntExact(cols)];
        buildTheBoard();
    }

    public void buildTheBoard() {

        for (int i = 0; i < cols; i++) {
            board[0][i] = i;
        }

        for (int i = 1; i < rows; i++) {
            Arrays.fill(board[i], 0);
            board[i][0] = i;
        }
    }

    public long getCols() {
        return cols;
    }

    public int[][] getBoard() {
        return board;
    }

    public long getRows() {
        return rows;
    }

    public long getTarget() {
        return target;
    }

    public void setSignOnBoard(int columToPutIn , Player player)
    {
        for(int i = (int)rows -1; i>0;i--)
        {
            if(board[i][columToPutIn] == 0)
            {
                board[i][columToPutIn] = player.getPlayerSign();
                numOfFreePlaces--;
                return;
            }
        }
    }

    public int getNumOfFreePlaces() {
        return numOfFreePlaces;
    }

    public boolean checkIfAvaliable(int col)
    {
        boolean res= false;
        if(board[1][col] == 0)
        {
            res = true;
        }
        return res;
    }

    public boolean checkPlayerWin(int col )
    {
        boolean res = false;
        int i = 1 ;
        while (board[i][col] == 0){
            i++;
        }
        res = isDiagonal(i,col) || isHorizontal(i,col) || isverticalertical(i,col);
        // check if won
        return res;
    }

    public boolean isDiagonal(int row,int col){
        boolean res = false;

        return res;
    }

    public boolean isHorizontal(int row,int col){
        boolean res = false;
        long  len = target;
        int mySign = board[row][col];
        int newCol = col +1;

        while (newCol <= cols && !res && board[row][newCol] == mySign  ){
            len--;
            if (len > 0 && board[row][newCol] == mySign ){
                newCol++;
            }
            else if (len == 0){
                res = true;
            }
        }

        if (!res && len > 0 ){
            newCol = col - 1;
            while (newCol>=1 && !res){
                if (len > 0 && board[row][newCol] == mySign ){
                    len--;
                    newCol--;
                }
                else if (len ==0){
                    res = true;
                }
            }
        }
        return res;
    }

    public boolean isverticalertical(int row,int col){
        boolean res = false;
        return res;
    }
}
