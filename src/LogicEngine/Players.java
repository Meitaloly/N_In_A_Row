package LogicEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Players {
    private Map<Integer, Player> players;

    public Players()
    {
        players = new HashMap<>();
    }

    public void rest()
    {
        players.clear();
    }

    public Player getCurrPlayer(int turnIndex)
    {
        return players.get(turnIndex);
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public void setNewPlayer(int id, Player player)
    {
        players.put(id,player);
    }

    public boolean isComputerTurn(int turnIndex)
    {
        boolean res = false;
        for(Player player: players.values())
        {
            if(player.getPlayerType() == 2) // computer
            {
                res = true;
            }
        }
        return res;
    }

    public void computerPlays(GameBoard gameBoard)
    {
        Random rand = new Random();
        int numOfCols = (int) gameBoard.getCols()-1;
        int choosenCol = rand.nextInt(numOfCols) + 1;
        int placeInBoard = gameBoard.getBoard()[1][choosenCol];
        while(placeInBoard != 0)
        {
            choosenCol = rand.nextInt(numOfCols) + 1;
            placeInBoard = gameBoard.getBoard()[1][choosenCol];

        }

        for(Player player: players.values())
        {
            if(player.getPlayerType() == 2) // computer
            {
                gameBoard.setSignOnBoard(choosenCol,player);
            }
        }
    }
}
