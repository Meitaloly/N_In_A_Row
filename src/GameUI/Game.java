package GameUI;

import java.util.Scanner;

public class Game {
    Menu menu;

    public Game() {
        menu = new Menu();
    }

    public void start() {
        System.out.println("Please choose one of the following options:");
        menu.printMenu();
        getUserMenuChoice();
    }

    public void getUserMenuChoice()
    {
        int userChoice = 0;

        do {
            userChoice = getInputFromUser();
        }while(userChoice==0);

        switch(userChoice)
        {
            case 1:
                String filePath = getXmlFileFromUser();
                // call CheckXMLFILE from logic
        }
    }

    public String getXmlFileFromUser()
    {
        Scanner input = new Scanner(System.in);
        String path = input.nextLine();
        return path;
    }


    public int getInputFromUser()
    {
        String res;
        int num = 0;
        Scanner input = new Scanner(System.in);
        res = input.nextLine();
        try {
            num = Integer.parseInt(res);
            if(num < 1 || num > 6){
                System.out.println("Invalid input - NUMBER NOT IN RANGE!)");
                System.out.println("Please enter a number between 1 to 6: ");
                num = 0;
            }
        }
        catch(Exception e) {
            System.out.println("Invalid input - YOU MUST ENTER A NUMBER!");
            System.out.println("Please enter a number between 1 to 6: ");
        }
        finally{
            return num;
        }
    }
}
