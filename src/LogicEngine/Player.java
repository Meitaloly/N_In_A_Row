package LogicEngine;

public class Player {
    private Integer id;
    private String name;
    private char playerSign;
    private int playerType; // 1 = human  2 = computer
    private int turnCounter = 0;

    public Integer getId() {
        return id;
    }

    public char getPlayerSign() {
        return playerSign;
    }

    public int getPlayerType() {
        return playerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerSign(char playerSign) {
        this.playerSign = playerSign;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void setId(Integer id) {
        this.id = id;
        this.name = "player " + (id+1);
        if (id == 0) {
            setPlayerSign('@');
        } else {
            setPlayerSign('#');
        }
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void restNumOfTurns()
    {
        turnCounter = 0;
    }
}