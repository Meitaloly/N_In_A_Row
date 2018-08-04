package GameUI;

import LogicEngine.*;

import java.util.*;

public class Game {
    Menu menu;
    GameBoard gameBoard;
    XmlFileUtils XFU;
    boolean activeGame = false;
    Players players;
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
        players = new Players();
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
                System.out.println();
                System.out.println("Starting a new game :)");
                if(activeGame)
                {
                    timer.cancel();
                    gameTimer.cancel();
                    timer = new Timer();
                    gameTimer = new GameTimer();
                    players.rest();
                }
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
                    Player currPlayer = players.getCurrPlayer(turnIndex);
                    System.out.println("it's " + currPlayer.getName() + " turn");
                    for(Player player: players.getPlayers().values()) {
                        System.out.println("The sign of " + player.getName() + " is: " + player.getPlayerSign());
                        System.out.println("Number of turns for " + player.getName() + " is: " + player.getTurnCounter());
                    }
                    System.out.println("Game timer: " + gameTimer.getTime());
                    menu.printMenu();
                    getUserMenuChoice();
                }
            break;
            }
            case 4:
            {
                if(activeGame) {
                    if (players.isComputerTurn(turnIndex)) {
                        players.computerPlays(gameBoard);
                        printBoard();
                        menu.printMenu();
                        getUserMenuChoice();
                    }
                    else
                    {
                        Player currPlayer = players.getCurrPlayer(turnIndex);
                        int userInput = getTurnInputFromUser(currPlayer);
                        gameBoard.setSignOnBoard(userInput,currPlayer);
                    }
                    if (gameBoard.getNumOfFreePlaces() == 0) {
                        System.out.println("No one won - the board is full!");
                        activeGame= false;
                        menu.printMenu();
                        getUserMenuChoice();
                    }
                }
                break;
            }

        }
    }

    public int getTurnInputFromUser(Player currPlayer)
    {
        int colNum;
        boolean stop = false;
        do {
            System.out.println("Please choose a col you want to enter your " + currPlayer.getPlayerSign());
            colNum = getInputFromUser(1, (int) gameBoard.getCols() - 1);
            if(colNum != 0)
            {
                if(gameBoard.checkIfAvaliable(colNum))
                {
                    System.out.println("the col " + colNum + " is full!");
                }
                else
                {
                    stop = true;
                }
            }
        }while(!stop);
        return colNum;
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
            players.setNewPlayer(i,player);
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
                        if (board[i][j] == 35) {
                            System.out.print(" # " + "|");
                        } else if (board[i][j] == 64) {
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
                System.out.println("Please enter a number between "+min +"to" + max+":");
                num = 0;
            }
        }
        catch(Exception e) {
            System.out.println("Invalid input - YOU MUST ENTER A NUMBER!");
            System.out.println("Please enter a number between "+ min +"to" + max+":");
        }
        finally{
            return num;
        }
    }
}
