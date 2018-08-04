package LogicEngine;

import java.util.Arrays;

public class GameBoard {
    long rows;
    long cols;
    long target;
    int[][] board;

    public GameBoard(){}

    public void setGameBoard(long i_rows, long i_cols, long i_target)
    {
        rows = i_rows+1;
        cols = i_cols+1;
        target = i_target;
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
}
