package bke;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;
import java.awt.Component;
import java.lang.Math;
import java.util.Scanner;
/**
 *
 * @author Thom
 * test2
 * Ga Verder met implementeren minimax, via bron:
 * http://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
 */

//class Zet{
//    //public int rij,kol;
//    public JButton[] rij, kol;
//    public JButton[][] fuckVak;
//    public Zet(){
//        this.rij = rij;
//        this.kol = kol;
//    }
//}

class Turn {
    enum NextMove{
        O,X,E;
    }
    NextMove next;
}

class Point{
    int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return "[" + x + "," + y + "]";
    }
}

public class Spel extends Spelbord{
    
    ArrayList<Point> availableZetten;
    
    public static String maakZet;
    public static boolean over = false, spelerAanZet = true;
    public static int scoreIntX, scoreIntO;
    public static boolean isMax;
    public static int aantalZetten = 0;
    private static Spelbord spelbord = new Spelbord();
    
    //Zet bestMove = new Zet();
    
    public static ArrayList<Integer> mogelijkeZetten;
    public static List scores;
    
    public static int max(Spelbord spelbord) {  
        Spelbord.checkSpelAfgelopen();
        //depth ++;
        
        if (Spelbord.hasXWon()) return -10; //- depth; // if X has won, return the score from his perspective
        if (Spelbord.hasOWon()) return 10; // return depth - 10
                
       // ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<Position> positions = Spelbord.getAvailableStates(); // create A list of every possible gamestate
        if (positions.isEmpty()) return 0; 
        //System.out.println("Depth" + depth + " " + turn);
        
        int best = Integer.MIN_VALUE;
         
        for (Position p : positions) {  
            //ArrayList<Position> test = spelbord.pointsAvailable(); 
            
            Spelbord s = new Spelbord(spelbord, p, spelerO);
            int move = min(s);
            if (move > best){
                best = move;
            }
        }
        return best;
    } 
    
    public static int min(Spelbord spelbord){
        Spelbord.checkSpelAfgelopen();
        //depth ++;
        
        boolean heeftXgewonnen = Spelbord.hasXWon();
        if (Spelbord.hasXWon()) return -10; //- depth; // if X has won, return the score from his perspective
        if (Spelbord.hasOWon()) return 10; // return depth - 10
                
       // ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<Position> positions = Spelbord.getAvailableStates(); // create A list of every possible gamestate
        if (positions.isEmpty()) return 0; 
        //System.out.println("Depth" + depth + " " + turn);
        
        int best = Integer.MAX_VALUE;
         
        for (Position p : positions) {  
            //ArrayList<Position> test = spelbord.pointsAvailable(); 
            
            Spelbord s = new Spelbord(spelbord, p, spelerX);
            //System.out.println(s);
            int move = min(s);
            if (move < best){
                best = move;
            }
        }
        return best;
    }
     
    
    public static void zetX(){
        UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
        Spelbord.geklikt.setText(spelerX);
    }
    public void zetO(){
        UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
        //if (over == true) System.out.println("GameOver");
    }


    public void spel(){
        aantalZetten++;
        Position position = null;
        if (spelerAanZet == true){
            zetX();
            spelerAanZet = false;
            spel();
        }
        else if (spelerAanZet == false){
            spelbord = findBestMove(spelbord);
            System.out.println(spelbord);
            //Spelbord.Holster[spelbord].setText("ey");
            //System.out.println(spelbord);
            spelerAanZet = true;
        }
        Spelbord.geklikt.setEnabled(false); 
        checkSpelAfgelopen();
    }
    
    private static Spelbord findBestMove(Spelbord spelbord){
        ArrayList<Position> positions = Spelbord.getAvailableStates();
        Spelbord bestChild = null;
        int previous = Integer.MIN_VALUE;
        for(Position p : positions){
            Spelbord child = new Spelbord(spelbord, p, spelerO);
            int current = max(child);
            //System.out.println("Child Score: " + current);
            if(current > previous){
                bestChild = child;
                previous = current;
            }
        }
        System.out.println(bestChild);
        return bestChild;
    }
      
    
    public void xWin(){
        scoreIntX = scoreIntX + 1;
        String scoreStrX = String.valueOf(scoreIntX + " ");
        BKE.scoreNummerX.setText(scoreStrX);
        spelOver();
    }
    public void oWin(){
        scoreIntO = scoreIntO + 1;
        String scoreStrO = String.valueOf(scoreIntO + " ");
        BKE.scoreNummerO.setText(scoreStrO);
        spelOver();
    }  
} 