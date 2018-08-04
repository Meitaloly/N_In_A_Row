package GameUI;

import LogicEngine.GameBoard;
import LogicEngine.GameTimer;
import LogicEngine.XmlFileUtils;

import java.util.*;

public class Game {
    Menu menu;
    GameBoard gameBoard;
    XmlFileUtils XFU;
    boolean activeGame = false;
    Map<Integer, Player> players;
    int turnIndex = 0;
    GameTimer gameTimer;
    Timer timer;

    public Game() {
        menu = new Menu();
    }

    public void start() {
        gameTimer = new GameTimer();
        timer = new Timer();
        menu.printMenu();
        gameBoard = new GameBoard();
        players = new HashMap<>();
        getUserMenuChoice();
    }

    public void getUserMenuChoice() {
        int userChoice = 0;

        do {
            userChoice = getInputFromUser(1,6);
        } while (userChoice == 0);

        switch (userChoice) {
            case 1: {
                if (!activeGame) {
                    System.out.println("Please enter a full path");
                    String filePath = getXmlFileFromUser();
                    XFU = new XmlFileUtils(filePath);
                    int validationNumber = XFU.checkXmlFileValidation(gameBoard);
                    while (validationNumber != -1) // XML FILE IS NOT OK
                    {
                        PrintErrorXMLFILE(validationNumber);
                        System.out.println("Please Enter new Path:");
                        filePath = getXmlFileFromUser();
                        XFU.setFilePath(filePath);
                        validationNumber = XFU.checkXmlFileValidation(gameBoard);
                    }

                    System.out.println("File was loaded!");
                    printBoard();
                    menu.printMenu();
                    getUserMenuChoice();
                } else {
                    System.out.println("Can't load new file during active game!");
                    menu.printMenu();
                    getUserMenuChoice();
                }
                break;
            }
            case 2: {
                activeGame = true;
                timer.schedule(gameTimer, 0, 1000);
                getPlayersTypeFromUser();
                menu.printMenu();
                getUserMenuChoice();
                break;
            }
            case 3:
            {
                printBoard();
                if(activeGame)
                {
                    Player currPlayer = getNameOfCurrPlayer();
                    System.out.println("it's " + currPlayer.getName() + " turn");
                    for(Player player: players.values()) {
                        System.out.println("The sign of " + player.getName() + " is: " + player.getPlayerSign());
                        System.out.println("Number of turns for " + player.getName() + " is: " + player.getTurnCounter());
                    }
                    System.out.println("Game timer: " + gameTimer.getTime());
                    menu.printMenu();
                    getUserMenuChoice();
                }
            }
        }
    }

    public Player getNameOfCurrPlayer()
    {
        return players.get(turnIndex);
    }

    public void getPlayersTypeFromUser(){
        boolean choseComputer = false;
        Scanner scan = new Scanner(System.in);
        String userInput;
        int userChoice;


        for(int i=0; i<2; i++)
        {
            Player player = new Player();
            player.setId(i);
            System.out.println("Setting up player " + (i+1) + ":");
            if(!choseComputer) {
                System.out.println("Choose player " + (i+1) + " type:");
                System.out.println("1) Human");
                System.out.println("2) Computer");

                do {
                    userChoice = getInputFromUser(1, 2);
                } while (userChoice == 0);

                if (userChoice == 2) {
                    choseComputer = true;
                }
                player.setPlayerType(userChoice);
            }
            else {
                System.out.println("Player" + (i+1) + " is Setted up!");
                player.setPlayerType(1); // player is human
            }
            players.put(i,player);
        }

    }

    public void printBoard() {
        int[][] board = gameBoard.getBoard();
        long rows = gameBoard.getRows();
        long cols = gameBoard.getCols();
        long target = gameBoard.getTarget();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!(i == 0 && j == 0)) {
                    if (board[i][j] == 0) {
                        System.out.print("   " + "|");
                    } else {
                        if (board[i][j] == 100) {
                            System.out.print(" # " + "|");
                        } else if (board[i][j] == 200) {
                            System.out.print(" @ " + "|");
                        } else {
                            if (i > 9 || j > 9) {
                                System.out.print(board[i][j] + " |");
                            } else {
                                System.out.print(board[i][j] + "  |");

                            }
                        }
                    }
                } else {
                    System.out.print("   " + "|");
                }
            }
            System.out.println();

            for (int j = 0; j < cols; j++) {
                System.out.print("---|");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("The target is: " + target);
        System.out.println("The game is: " + (activeGame?"active":"not active"));
    }

    private void PrintErrorXMLFILE(int num)
    {
        switch (num)
        {
            case 0:
                System.out.println("File not Exist");
                break;
            case 1:
                System.out.println("Num of rows not in range");
                break;
            case 2:
                System.out.println("Num of cols not in range");
                break;
            case 3:
                System.out.println("target bigger then rows or cols");
                break;
            case 4:
                System.out.println("target is 0");
                break;
            case 5:
                System.out.println("not XML file");
                break;
        }
    }


    public String getXmlFileFromUser()
    {
        Scanner input = new Scanner(System.in);
        String path = input.nextLine();
        return path;
    }


    public int getInputFromUser(int min, int max)
    {
        String res;
        int num = 0;
        Scanner input = new Scanner(System.in);
        res = input.nextLine();
        try {
            num = Integer.parseInt(res);
            if(num < min || num > max){
                System.out.println("Invalid input - NUMBER NOT IN RANGE!)");
                System.out.println("Please enter a number between 1 to 6: ");
                num = 0;
            }
        }
        catch(Exception e) {
            System.out.println("Invalid input - YOU MUST ENTER A NUMBER!");
            System.out.println("Please enter a number between"+ min +"to" + max+":");
        }
        finally{
            return num;
        }
    }
}
