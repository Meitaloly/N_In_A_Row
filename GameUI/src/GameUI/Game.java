package GameUI;

import LogicEngine.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class Game {
    private Menu menu;
    private GameBoard gameBoard;
    private XmlFileUtils XFU;
    private boolean activeGame = false;
    private Players players;
    private int turnIndex = 0;
    private GameTimer gameTimer;
    private Timer timer;
    private GameHistory history;
    private boolean savedGamedLoaded = false;
    private boolean loadedBoard = false;


    public Game() {
        menu = new Menu();
    }

    public void start() {
        gameTimer = new GameTimer();
        timer = new Timer();
        menu.printMenu();
        gameBoard = new GameBoard();
        players = new Players();
        history = new GameHistory();
        getUserMenuChoice();
        return;
    }

    public void resetTheGame()
    {
        turnIndex = 0;
        savedGamedLoaded=false;
        loadedBoard =false;
        activeGame = false;
        start();
    }

    public void getUserMenuChoice() {
        int userChoice = 0;

        do {
            userChoice = getInputFromUser(1,9);
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
                    loadedBoard = true;
                    printBoard();
                    System.out.println("The target is: " + gameBoard.getTarget());
                    System.out.println("The game is: " + (activeGame?"active":"not active"));
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
                if(loadedBoard) {
                    if (activeGame) {
                        System.out.println();
                        System.out.println("Starting the game :)");
                        timer.cancel();
                        gameTimer.cancel();
                        timer = new Timer();
                        gameTimer = new GameTimer();
                        history.resetHistory();
                        players.rest();
                        gameBoard.reset(); //TODO
                        turnIndex =0;
                    }
                    getPlayersTypeFromUser();
                    savedGamedLoaded = false;
                    activeGame = true;
                    timer.schedule(gameTimer, 0, 1000);
                }
                else
                {
                    System.out.println("Can't start without loading game first!");
                }
                menu.printMenu();
                getUserMenuChoice();
                break;
            }
            case 3: {
                if(loadedBoard) {
                    printBoard();
                    System.out.println("The target is: " + gameBoard.getTarget());
                    System.out.println("The game is: " + (activeGame ? "active" : "not active"));
                    if (activeGame) {
                        Player currPlayer = players.getCurrPlayer(turnIndex);
                        System.out.println("it's " + currPlayer.getName() + " turn");
                        for (Player player : players.getPlayers().values()) {
                            System.out.println("The sign of " + player.getName() + " is: " + player.getPlayerSign());
                            System.out.println("Number of turns for " + player.getName() + " is: " + player.getTurnCounter());
                        }
                        System.out.println("Game timer: " + gameTimer.getTime());
                    }
                }
                else
                {
                    System.out.println("No board was loaded yet!");
                }
                menu.printMenu();
                getUserMenuChoice();
                break;
            }
            case 4: {
                boolean isWinner;
                if (activeGame) {
                    if (players.isComputerTurn(turnIndex)) {
                        System.out.println(players.getCurrPlayer(turnIndex).getName() + " turn:");
                        int choosenCol = players.computerPlays(gameBoard); ////////// change 6.8.18
                        players.getCurrPlayer(turnIndex).incTurnCounter();
                        history.addToHistory(players.getCurrPlayer(turnIndex).getName(), choosenCol); ///////////  6.8.18

                        isWinner = gameBoard.checkPlayerWin(choosenCol);

                        //isWinner = gameBoard.checkPlayerWin(players.getCurrPlayer(turnIndex).getPlayerSign());

                        if (isWinner) {
                            activeGame = false;
                            printBoard();
                            System.out.println();
                            System.out.println(players.getCurrPlayer(turnIndex).getName() + " WON!!");
                            System.out.println();
                            endGame();
                        }
                    } else // not computer
                    {
                        System.out.println(players.getCurrPlayer(turnIndex).getName() + " turn:");
                        Player currPlayer = players.getCurrPlayer(turnIndex);
                        int userInput = getTurnInputFromUser(currPlayer);
                        gameBoard.setSignOnBoard(userInput, currPlayer);
                        players.getCurrPlayer(turnIndex).incTurnCounter();
                        history.addToHistory(currPlayer.getName(), userInput); ////////////////////////// 6.8.18

                        isWinner = gameBoard.checkPlayerWin(userInput);

                        // isWinner = gameBoard.checkPlayerWin(players.getCurrPlayer(turnIndex).getPlayerSign());

                        if (isWinner) {

                            activeGame = false;
                            printBoard();
                            System.out.println();
                            System.out.println(players.getCurrPlayer(turnIndex).getName() + " WON!!\n");
                            endGame();
                            getUserMenuChoice();
                        }
                    }
                    if (gameBoard.getNumOfFreePlaces() == 0) {
                        System.out.println("No one won - the board is full!");
                        System.exit(0);
                    } else {
                        changeTurn();
                        printBoard();
                        menu.printMenu();
                        getUserMenuChoice();
                    }
                } else {
                    System.out.println("Can't play player turn! Game isn't active yet!");
                    menu.printMenu();
                    getUserMenuChoice();
                }
                break;
            }
            case 5: {
                if (activeGame) {
                    printHistory();
                } else {
                    System.out.println("There is no history! Game isn't active yet!");
                }
                menu.printMenu();
                getUserMenuChoice();
                break;
            }
            case 6:{
                if (!history.isEmpty()) {
                    undo(history.getLastMove());
                    printBoard();
                    menu.printMenu();
                    getUserMenuChoice();
                }
                else {
                    System.out.println("\nNo moves yet, can't do undo");
                    menu.printMenu();
                    getUserMenuChoice();
                }
                break;
            }
            case 7: {
                if(activeGame)
                {
                    SaveGameToFile();
                    System.out.println("Game saved Successfully");
                }
                else
                {
                    System.out.println("There is no active game yet!");
                }
                menu.printMenu();
                getUserMenuChoice();
            break;
            }
            case 8: {
                if(!activeGame) {
                    if (LoadSavedGameFromFile()) {
                        activeGame = true;
                        System.out.println("Saved game loaded successfully!");
                        savedGamedLoaded = true;
                        loadedBoard = true;
                        timer.schedule(gameTimer,0,1000);
                    } else {
                        System.out.println("There is no saved game yet!");
                    }
                }
                else
                {
                    System.out.println("Can't load saved game during active game!");
                }
                menu.printMenu();
                getUserMenuChoice();
                break;
            }
            case 9: {
                boolean noValidInput = true;
                System.out.println("Are you sure you want to exit the game ?");
                System.out.println("press 'Y' to exit press 'N' to keep playing");
                Scanner s = new Scanner(System.in);
                do {
                    String input = s.nextLine().toUpperCase();
                    if (input.equals("N")) {
                        noValidInput = true;
                        menu.printMenu();
                        getUserMenuChoice();
                    } else if (input.equals("Y")) {
                        noValidInput = true;
                        System.exit(0);
                    } else {
                        System.out.println("You must press 'Y' or 'N' only!");
                        System.out.println("press 'Y' to exit press 'N' to keep playing");
                    }
                }while(noValidInput);
                break;
            }
        }
    }

    //TODO: from here to changeTurn()
    private boolean LoadSavedGameFromFile() {
        boolean res = false;
        File saveGameFile = new File("savedGame.txt");
        if (saveGameFile.exists()) {
            res=true;
            Scanner reader = null;
            try {
                reader = new Scanner(saveGameFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            gameBoard.setTarget(strToInt(reader));
            readPlayersDataFromFile(reader);
            turnIndex = strToInt(reader);
            readBoardDataFromFile(reader);
            readHistoryFromFile(reader);
        }
        return res;
    }

    private void readHistoryFromFile(Scanner reader)
    {
        history.resetHistory();
        int lenHistory = strToInt(reader);
        for(int i=0; i< lenHistory; i++)
        {
            history.getHistory().add(reader.nextLine());
        }
    }

    private void readBoardDataFromFile(Scanner reader)
    {
        int rows = strToInt(reader);
        int cols = strToInt(reader);
        String subStr = null;
        int index =0;
        gameBoard.setRows(rows);
        gameBoard.setCols(cols);
        gameBoard.setEmptyBoard();
        gameBoard.resetNumOfFreePlaces();
        for(int i=0; i<rows; i++)
        {
            String str = reader.nextLine();
            for(int j=0; j<cols; j++)
            {
                if(j!=cols-1) {
                    index = str.indexOf(",");
                    subStr = str.substring(0,index);
                }
                else
                {
                    subStr=str;
                }
                int value = Integer.parseInt(subStr);
                gameBoard.setCubeInBoard(i,j,value);
                if(j!=cols-1) {
                    str = str.substring(index + 1, str.length());
                }
            }
        }

    }

    private void readPlayersDataFromFile(Scanner reader)
    {
        players.rest();
        int numOfPlayers = strToInt(reader);
        for(int i =0 ; i<numOfPlayers ;i++)
        {
            Player player = new Player();
            player.setId(strToInt(reader));
            player.setName(reader.nextLine());
            player.setPlayerType(strToInt(reader));
            player.setPlayerSign(reader.nextLine().charAt(0));
            player.setTurnCounter(strToInt(reader));
            players.getPlayers().put(player.getId(),player);
        }
    }

    private int strToInt(Scanner reader)
    {
        String str = reader.nextLine();
        return Integer.parseInt(str);
    }

    public void changeTurn()
    {
        turnIndex = (turnIndex + 1) % 2;
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
                if(!gameBoard.checkIfAvaliable(colNum))
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
                System.out.println("Player " + (i+1) + " is Setted up!");
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
                System.out.println("Please enter a number between "+min +" to " + max+":");
                num = 0;
            }
        }
        catch(Exception e) {
            System.out.println("Invalid input - YOU MUST ENTER A NUMBER!");
            System.out.println("Please enter a number between "+ min +" to " + max+":");
        }
        finally{
            return num;
        }
    }

    public void printHistory (){
        List<String> gHistory = history.getHistory();
        System.out.println();
        if (gHistory.size() == 0){
            System.out.println("No moves yet.");
        }
        else {
            System.out.println("Moves history:");
            for (String s : gHistory) {
                System.out.println(s);
            }
        }
    }

    //TODO: from here to endGame
    public void SaveGameToFile()
    {
        String fileName = "savedGame.txt";
        File f = new File(fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(gameBoard.getTarget());
        writePlayerToFile(writer);
        writer.println(turnIndex);
        writeBoardDataToFile(writer);
        writeHistoryToFile(writer);
        writer.close();
    }

    private void writeHistoryToFile(PrintWriter writer)
    {
        int lenHistory = history.getHistory().size();
        writer.println(lenHistory);
        for(String str :history.getHistory())
        {
            writer.println(str);
        }
    }

    private void writeBoardDataToFile(PrintWriter writer)
    {
        long rows = gameBoard.getRows();
        long cols = gameBoard.getCols();
        int [][] board = gameBoard.getBoard();
        writer.println(rows);
        writer.println(cols);

        for(int i = 0; i<rows;i++)
        {
            for(int j=0; j<cols; j++)
            {
                writer.print(board[i][j]);
                if(j!=cols-1)
                {
                    writer.print(',');
                }
            }
            writer.println();
        }
    }

    private void writePlayerToFile(PrintWriter writer)
    {
        int lenOfPlayers =players.getPlayers().size();
        writer.println(lenOfPlayers);
        for(Player player : players.getPlayers().values())
        {
            writer.println(player.getId());
            writer.println(player.getName());
            writer.println(player.getPlayerType());
            writer.println(player.getPlayerSign());
            writer.println(player.getTurnCounter());
        }
    }

    public void endGame(){
        int input;
        System.out.println();
        System.out.println("THE GAME IS OVER.");
        System.out.println("for reset game press 1.");
        System.out.println("for exit and back to menu press 2.");
        do {
            input = getInputFromUser(1,2);
        }while(input == 0);

        if (input == 1){
            turnIndex = 0;
            gameBoard.resetBoard();
            history.resetHistory();
            turnIndex = 0;
            timer.cancel();
            gameTimer.cancel();
            timer = new Timer();
            gameTimer = new GameTimer();
            timer.schedule(gameTimer, 0, 1000);
            activeGame = true;
            resetPlayersNumOfTurns();
        }
        else
        {
            resetTheGame();
        }
        menu.printMenu();
        getUserMenuChoice();
    }

    public void resetPlayersNumOfTurns() {
        for(Player player : players.getPlayers().values())
        {
            player.setTurnCounter(0);
        }
    }


    public void undo(int col){
        int playerIndex = gameBoard.gameUndo(col);
        if (playerIndex == 2){
            turnIndex = 1;
        }
        else {
            turnIndex = 0;
        }
        history.eraseLastMove();
        players.getCurrPlayer(turnIndex).reduceTurnCounter();  // play the turn again
    }
}
