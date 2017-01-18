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
    
    List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    
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
    
    public List<Point> getAvailableStates(){
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (vakken[i][j].getText().equals(" ")){
                    availablePoints.add(new Point(i,j));
                }
            }
        }
        return availablePoints;
    }
    
    public void placeAMove(Point point, String Player){
        vakken[point.x][point.y].setText(Player);
    }
    
    Point computersMove;
    
    public boolean hasXWon(){
        checkRijen();
        //System.out.println("Rij" + xWin);
        checkKolommen();
        //System.out.println("Kol" + xWin);
        checkDiagonalenLR();
        //System.out.println("RL" + xWin);
        checkDiagonalenRL();
        //System.out.println("LR" + xWin);
        //System.out.println("X check voorbij" + xWin);
        return xWin;
    }
    public boolean hasOWon(){
        //checkSpelAfgelopen();
        //System.out.println("O" + oWin);
        return oWin;
    }
    
    public int minimax(int depth, int turn) {  
        //checkSpelAfgelopen();
        if (hasXWon()) return + 1;
        if (hasOWon()) return -1;
        
        List<Point> pointsAvailable = getAvailableStates();
        
        if (pointsAvailable.isEmpty()) return 0; 
        System.out.println("Depth" + depth + turn);
        depth+=1;
        turn+=1;

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
         
        for (int i = 0; i < pointsAvailable.size(); ++i) {  
            Point point = pointsAvailable.get(i);  
            System.out.println(pointsAvailable.size() + " " + pointsAvailable.get(i) );
            if (spelerAanZet == true) { 
                placeAMove(point, spelerX); 
                int currentScore = minimax(depth + 1, 1);
                System.out.println("CS + DP" + currentScore + depth + min + max);
                max = Math.max(currentScore, max);
                
                if(currentScore >= 0){
                    if(depth == 0) computersMove = point;
                } 
                if(currentScore == 1){
                    placeAMove(point, " "); 
                    break;
                } 
                vakken[point.x][point.y].setText(" "); //Reset this point

                if(i == pointsAvailable.size()-1 && max < 0){if(depth == 0)computersMove = point;}
                //System.out.println(point + " " + computersMove + " " + pointsAvailable.size());
            } else if (spelerAanZet == false) {
                placeAMove(point, spelerO); 
                
                int currentScore = minimax(depth + 1, 2);
                min = Math.min(currentScore, min); 
                if(min == -1){
                    placeAMove(point, " ");
                    break;
                }
            }
           vakken[point.x][point.y].setText(" "); //Reset this point
        } 
        if(turn % 2 != 0){
            spelerAanZet = true ;
            return max;
        } else {
            spelerAanZet = false;
            return min;
        }
    } 
     
    public void checkRijen(){
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerX) && vakken[j][1].getText().equals(spelerX) && vakken[j][2].getText().equals(spelerX) ||
                  (vakken[j][0].getText().equals(spelerO) && vakken[j][1].getText().equals(spelerO) && vakken[j][2].getText().equals(spelerO))){
                    if (vakken[j][i].getText().contains("X")){
                        vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        xWin = true;
                    } else {
                        vakken[j][i].setBackground(Color.decode("#E74C3C")); 
                        oWin = true;
                    }
               }
           }
        }
        //return false;
    }
    public void checkKolommen(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[0][i].getText().equals(spelerX) && vakken[1][i].getText().equals(spelerX) && vakken[2][i].getText().equals(spelerX) ||
                  (vakken[0][i].getText().equals(spelerO) && vakken[1][i].getText().equals(spelerO) && vakken[2][i].getText().equals(spelerO))){
                  if (vakken[j][i].getText().contains("X")){
                        vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        xWin = true;
                    } else {
                        vakken[j][i].setBackground(Color.decode("#E74C3C")); 
                        oWin = true;
                    }
                }
            }
        }
    }
    public void checkDiagonalenLR(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){ 
                if (vakken[0][0].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][2].getText().equals(spelerX) ||
                    vakken[0][0].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][2].getText().equals(spelerO)){
                    if (vakken[0][0].getText().contains("X")){ //Voert uit wanneer X van linksboven naar rechtsonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#2C3E50"));
                        xWin = true;
                    } 
                    else if (vakken[0][0].getText().contains("O")){ //Voert uit wanneer O van linksboven naar rechtsonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#E74C3C"));                     
                        oWin = true;
                    }
                }
            }
        }
    }
    public void checkDiagonalenRL(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){ 
                if (vakken[0][2].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][0].getText().equals(spelerX) || 
                   (vakken[0][2].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][0].getText().equals(spelerO))){
                    if (vakken[0][2].getText().contains("X")){ //Voert uit wanneer X van rechtsboven naar linksonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#2C3E50"));  
                        xWin = true;
                    } 
                    else if(vakken[0][2].getText().contains("O")){ //Voert uit wanneer O van rechtsboven naar linksonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#E74C3C"));   
                        oWin = true;
                    }
                } 
            }
        }
        //return false;
    }

    public void checkSpelAfgelopen(){ 
        checkRijen();
        checkKolommen();
        checkDiagonalenLR();
        checkDiagonalenRL();
        if (xWin == true){
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
        System.out.println(computersMove.x);
        //Holster[returnNextMove()].setText(spelerO);
        //System.out.println("e");
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
        minimax(1,1);
        return 2 + 2;
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