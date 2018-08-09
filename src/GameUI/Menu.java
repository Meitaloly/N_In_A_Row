package GameUI;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    List<String> menu = new ArrayList<>();

    public Menu()
    {
        menu.add("Load the game from Xml File");
        menu.add("Start the game");
        menu.add("Show game info");
        menu.add("Play turn");
        menu.add("Show history");
        menu.add("Undo last move");
        menu.add("Save current game");
        menu.add("Load last saved game");
        menu.add("Exit");
    }

    public List<String> getMenu() {
        return menu;
    }

    public void printMenu()
    {
        int write =1;
        System.out.println();
        System.out.println("Please choose one of the following options:");
        for(String s: menu)
        {
            System.out.println(write+ ") "+s);
            write++;
        }
    }
}
