package LogicEngine;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {
    List<String> history = new ArrayList<>();

    public void addToHistory ( int numOfPlayer, int theColumn){
        history.add("Player " + numOfPlayer + ": " + theColumn);
    }

    public void eraseLastMove (){
        history.remove(history.size() - 1);
    }

    public void printHistory (){
        System.out.println();
        System.out.println("Move history:");
        for(String s: history)
        {
            System.out.println(s);
        }
    }

    public void resetHistory () {
        history = new ArrayList<>();
    }

    public int getLastMove(){
        String lastMove = (history.get(history.size() - 1));
        int len = lastMove.length();
        int res = lastMove.charAt(len-1)-'0';  //get the value of last char of the string (the column)
        return res;
    }






}
