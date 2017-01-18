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

public class Spel extends JPanel{
    
    ArrayList<Point> availableZetten;
    
    final static String spelerX = "X", spelerO = "O";
    public static JButton geklikt;
    public static String maakZet;
    public static boolean over = false, xWin = false, oWin = false, spelerAanZet = true;
    public static int scoreIntX, scoreIntO;
    public static int boven, links, onder, rechts;
    public static JButton[][] vakken = new JButton[3][3];
    public static JButton[] next, Holster = new JButton[9];
    public static boolean isMax;
    public static int aantalZetten = 0;
    
    //Zet bestMove = new Zet();
    
    public static ArrayList<Integer> mogelijkeZetten;
    public static List scores;
    
    public Spel(){
       initSpel(); 
    }
    

    
    public void initSpel(){
        
        setLayout(new GridLayout(3,3,5,5));
        int tel = 0;
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                vakken[i][j] = new JButton(" ");
                vakken[i][j].setFont(BKE.spelFont);
                vakken[i][j].setBackground(Color.decode("#ECF0F1"));
                if (tel > 2)        boven = 3;
                if (tel % 3 != 0)   links = 3;
                if (tel < 6)        onder = 3;
                if (tel % 3 != 2)   rechts = 3;
                vakken[i][j].addActionListener(new KnopHandeler());
                vakken[i][j].setBorder(BorderFactory.createMatteBorder(boven,links,onder,rechts,Color.decode("#2a2a2a")));
                Holster[tel] = (vakken[i][j]);
                boven = 0;
                links = 0;
                onder = 0;
                rechts=0;
                
                add(vakken[i][j]);
                //add(Holster[tel]);
                tel++;
            }
        }
    }
    
    public ArrayList<Point> getAvailableStates(){
        availableZetten = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (vakken[i][j].getText().equals(" ")){
                    availableZetten.add(new Point(i,j));
                }
            }
        }
        return availableZetten;
    }
    
    public void placeAMove(Point point, String Player){
        vakken[point.x][point.y].setText(Player);
    }
    
    Point computersMove;
    
    public boolean hasXWon(){
        checkRijKolX();
        //System.out.println("Rij" + xWin);
        checkDiagonalenX();
        //System.out.println("Kol" + xWin);
        return xWin;
    }
    public boolean hasOWon(){
        //checkSpelAfgelopen();
        //System.out.println("O" + oWin);
        return oWin;
    }
    
    public int minimax(Point mogSpel) {  
        //checkSpelAfgelopen();
        //depth ++;
        if (hasXWon()) return 10; //- depth; // if X has won, return the score from his perspective
        if (hasOWon()) return -10; // return depth - 10
                
        ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<Point> pointsAvailable = getAvailableStates(); // create A list of every possible gamestate
        if (pointsAvailable.isEmpty()) return 0; 
        //System.out.println("Depth" + depth + " " + turn);
        
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
         
        for (int i = 0; i < pointsAvailable.size(); ++i) {  
            Point point = pointsAvailable.get(i); 
            System.out.println("Point: "+point.toString());
            int x1 = point.x;
            int y1 = point.y;
            scores.add(minimax(point));
            System.out.println(x1 + " " + y1);
            
            
            //scores[i] = minimax(point);
            
//            if (spelerAanZet == true) { 
//                placeAMove(point, spelerX); 
//                int currentScore = minimax(depth + 1, 1);
//                System.out.println("CS + DP" + currentScore + depth);
//                max = Math.max(currentScore, max);
//                System.out.println(max);
//                return max;
//                //if(currentScore >= 0){ if(depth == 0) computersMove = point;} 
//                //if(currentScore == 1){vakken[point.x][point.y].setText(" "); break;} 
//                //if(i == pointsAvailable.size()-1 && max < 0){if(depth == 0)computersMove = point;}
//            } else if (spelerAanZet == false) {
//                placeAMove(point, spelerO); 
//                int currentScore = minimax(depth + 1, 2);
//                min = Math.min(currentScore, min); 
//                if(min == -1){vakken[point.x][point.y].setText(" "); break;}
//                //return min;
//            }
            //vakken[point.x][point.y].setText(" "); //Reset this point
        } 
        if(spelerAanZet == true){
            spelerAanZet = false ;
            return max;
        } else {
            spelerAanZet = true;
            return min;
        }
    } 
     
    public void checkRijKolX(){
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerX) && vakken[j][1].getText().equals(spelerX) && vakken[j][2].getText().equals(spelerX) ||
                  (vakken[0][i].getText().equals(spelerX) && vakken[1][i].getText().equals(spelerX) && vakken[2][i].getText().equals(spelerX))){
                    if (vakken[j][i].getText().contains("X")){
                        vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        xWin = true;
                    }
                }
            }
        //return false;
        }
    }
    public void checkRijKolO(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerO) && vakken[j][1].getText().equals(spelerO) && vakken[j][2].getText().equals(spelerO) ||
                  (vakken[0][i].getText().equals(spelerO) && vakken[1][i].getText().equals(spelerO) && vakken[2][i].getText().equals(spelerO))){
                  if (vakken[j][i].getText().contains("O")){
                        vakken[j][i].setBackground(Color.decode("#E74C3C"));
                        oWin = true;
                    } 
                }
            }
        }
    }
    public void checkDiagonalenX(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){ 
                if (vakken[0][0].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][2].getText().equals(spelerX) ||
                    vakken[0][2].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][0].getText().equals(spelerX)){
                    if (vakken[0][0].getText().contains("X")){ //Voert uit wanneer X van linksboven naar rechtsonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#2C3E50"));
                        xWin = true;
                    } 
                }
            }
        }
    }
    public void checkDiagonalenO(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){ 
                if (vakken[0][0].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][2].getText().equals(spelerO) || 
                   (vakken[0][2].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][0].getText().equals(spelerO))){
                    if(vakken[0][2].getText().contains("O")){ //Voert uit wanneer O van rechtsboven naar linksonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#E74C3C"));   
                        oWin = true;
                    }
                } 
            }
        }
    }

    public void checkSpelAfgelopen(){ 
        checkRijKolX();
        checkRijKolO();
        checkDiagonalenX();
        checkDiagonalenO();
        if (xWin == true){
            spelOver();
        }
        if (oWin == true){
            spelOver();
        }
    }   
    public void spelOver(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                vakken[i][j].setEnabled(false); // Schakelt alle vakken uit.
            }
        }
        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
    }
    
    public void zetX(){
        UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
        geklikt.setText(spelerX);
    }
    public void zetO(){
        UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
        returnNextMove();
        //if (over == true) System.out.println("GameOver");
        //int next = returnNextMove();
        //System.out.println(computersMove.x);
        Holster[returnNextMove()].setText(spelerO);
        System.out.println(returnNextMove());
        vakken[computersMove.x][computersMove.y].setText("O");
        //vakken[computersMove.x][computersMove.y].setEnabled(false);
    }
    public void test(JButton[] x){
        //x.setText("fool");
    }

    public void spel(){
        aantalZetten++;

        if (spelerAanZet == true){
            zetX();
            spelerAanZet = false;
        }
        else if (spelerAanZet == false){
            zetO();
            spelerAanZet = true;
        }
        geklikt.setEnabled(false); 
        checkSpelAfgelopen();
    }
      
    public int returnNextMove(){
        if(over == true){
            return -1;
        }
        //getAvailableStates().;
        //minimax();
        System.out.println(computersMove.x + " " + computersMove.y);
        return computersMove.x + computersMove.y;
    }
    
    
    
    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            geklikt = (JButton)e.getSource();
            spel();
        }
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