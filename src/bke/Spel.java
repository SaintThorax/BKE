package bke;
import java.awt.*;
import java.util.List;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author Thom
 */
class Zet{
    int rij, kol;
    public Zet(int rij, int kol){
        this.rij = rij;
        this.kol = kol;
    }
}

class ZetEnScores{
    int score;
    Zet zet;
    
    ZetEnScores(int score, Zet zet){
        this.score = score;
        this.zet = zet;
    }
}

public class Spel extends JPanel{
    
    final static String spelerX = "X", spelerO = "O";
    public String tegenstander;
    public static JButton geklikt;
    public static String spelerAanZet;
    public static boolean over = false, xWin = false, oWin = false, spelerIsMens = true;
    public static boolean hasXWon, hasOWon;
    public static int scoreIntX, scoreIntO, waarde, xMMS, oMMS;
    public static int boven, links, onder, rechts, zettenVoorbijO = 0;
    public static JButton[][] vakken;
    public static int aantalZetten = 0, tel = 0;
    
    Random rand = new Random();
    public ArrayList<Integer> moves; 
    public List<Zet> zetten, mogelijkeZettenMM, gemaakteZetten;
    public Zet gemaakteZet;    
    
    public Spel(){
       initSpel(); 
    }

    
    public void initSpel(){ //Creating the playing field
        setLayout(new GridLayout(3,3,5,5));
        vakken = new JButton[3][3];
        int tel = 0;
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                vakken[i][j] = new JButton(); //vakken is a button[vertical][horizontal]
                vakken[i][j].setFont(BKE.spelFont);
                vakken[i][j].setBackground(Color.decode("#ECF0F1"));
                if (tel > 2)        boven = 3; //These variables decide the border on the buttons
                if (tel % 3 != 0)   links = 3;
                if (tel < 6)        onder = 3;
                if (tel % 3 != 2)   rechts = 3;
                vakken[i][j].addActionListener(new KnopHandeler());
                vakken[i][j].setBorder(BorderFactory.createMatteBorder(boven,links,onder,rechts,Color.decode("#2a2a2a")));
                
                boven = 0;
                links = 0;
                onder = 0;
                rechts=0;
                
                /* Zou dit kunnen gebruiken om knoppen te refrencen*/
                add(vakken[i][j]);
                tel++;
            }
        }
    }
    
    public void nieuwSpel(){
        Random rand = new Random();
        if(aantalZetten == 0){
            Zet p = new Zet(rand.nextInt(3), rand.nextInt(3));
            placeAMove(p, spelerO);
        }
    }
    
    public void checkRijen(){ //Checks the rows
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
    }
    public void checkKolommen(){ // Checks the columns
        for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){                   
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
    public void checkDiagonalenLR(){ //checks left to right diagnals
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){ 
                if (vakken[0][0].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][2].getText().equals(spelerX) ||
                    vakken[0][0].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][2].getText().equals(spelerO)){
                    if (vakken[0][0].getText().contains("X")){ //Voert uit wanneer X van linksboven naar rechtsonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#2C3E50"));
                        xWin = true;
                        return;
                    } 
                    else if (vakken[0][0].getText().contains("O")){ //Voert uit wanneer O van linksboven naar rechtsonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#E74C3C"));                     
                        oWin = true;
                        return;
                    }
                }
            }
        }
    }
    public void checkDiagonalenRL(){ //checks right to left diagnals
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){ 
                if (vakken[0][2].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][0].getText().equals(spelerX) || 
                   (vakken[0][2].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][0].getText().equals(spelerO))){
                    if (vakken[0][2].getText().contains("X")){ //Voert uit wanneer X van rechtsboven naar linksonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#2C3E50"));  
                        xWin = true;
                        return;
                    } 
                    else if(vakken[0][2].getText().contains("O")){ //Voert uit wanneer O van rechtsboven naar linksonder 3 op een rij heeft
                        for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#E74C3C"));   
                        oWin = true;
                        return;
                    }
                } 
            }
        }
    }

    public void checkSpelAfgelopen(){ //checks if the game is over
        checkRijen();
        checkKolommen();
        checkDiagonalenLR();
        checkDiagonalenRL();
        if (xWin == true){
            xWin();
            spelOver();
        } else if (oWin == true){
            oWin();
            spelOver();
        }
        else if(aantalZetten >= 9 && over == false){ // decides it's a draw
            for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){     
                    vakken[i][j].setBackground(Color.decode("#b2b2b2"));
                }
            spelOver();
            }
        }
    }   
    public void spelOver(){ //Runs when game is over and disables all buttons
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                vakken[i][j].setEnabled(false); // Schakelt alle vakken uit.
                zettenVoorbijO = 0;
            }
        }
        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
    }
    
    
    public void spelerAanZet(){ //Decides who's turn it is 
        if (aantalZetten % 2 != 0) { //aantalZetten is the amount of turns passed
            tegenstander = spelerO;
            spelerAanZet = spelerX;
            //UIManager.put("Button.disabledText", Color.decode("#2C3E50"));                     
        }
        if (aantalZetten % 2 == 0) {
            tegenstander = spelerX;
            spelerAanZet = spelerO;
          //minimax(1, true);                      
          
            if (zettenVoorbijO % 2 == 0){
                Zet best = returnBestMove(true);
                System.out.println("best: [" + best.rij + "," + best.kol + "]");
                placeAMove(returnBestMove(true), spelerO);
                //UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
            } else {
                Zet worst = returnBestMove(true);
                System.out.println("worst: [" + worst.rij + "," + worst.kol + "]");
                placeAMove(returnBestMove(true), spelerO);
                //UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
            }
            zettenVoorbijO ++;
            
            spelerIsMens = true;
            
        } 
    }
    
    public void spel(){ //Runs whenever a button gets clicked
        aantalZetten++; //The integer value of the amount of made moves
       
        spelerAanZet(); 
        geklikt.setText(spelerAanZet); //sets the text on a button to the string value of the current player
        geklikt.setEnabled(false); //disables the pressed button      
        spelerIsMens = false;  
        
        checkSpelAfgelopen();     //checks if the game has finished 
        
        callMinimax(0,false);
        for (ZetEnScores zes : rootsChildrenScores) {
                System.out.println("Point: [" + zes.zet.rij + "," + zes.zet.kol+ "]" + " Score: " + zes.score);
        }
        
        aantalZetten++;
        spelerAanZet();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if ((vakken[i][j].getText().length()>0)){
                   vakken[i][j].setEnabled(false);
               }               
            }         
        }
        checkSpelAfgelopen();
    }
    
    public void placeAMove(Zet zet, String spelerAanZet) {
        UIManager.put("Button.disabledText", Color.decode("#2b2b2b"));
        vakken[zet.rij][zet.kol].setText(spelerAanZet); 
        
    }
        
    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            geklikt = (JButton)e.getSource(); //gives the clicked button a way to refrence      
            spel();
        }
    }
    
    public void xWin(){ //runs when x wins
        scoreIntX = scoreIntX + 1;
        String scoreStrX = String.valueOf(scoreIntX + " ");
        BKE.scoreNummerX.setText(scoreStrX);
    }
    public void oWin(){ // runs when o wins
        if(oWin == true){
            scoreIntO = scoreIntO + 1;
            String scoreStrO = String.valueOf(scoreIntO + " ");
            BKE.scoreNummerO.setText(scoreStrO);
        }
    }  
    
    public List<Zet> mogelijkeZetten(){
        zetten = new ArrayList<>();
        for (int i = 0; i<3; i++){
            for(int j= 0; j<3; j++){
                if (vakken[i][j].getText().equals("")){
                    zetten.add(new Zet(i,j));
                }
            }
        }
        return zetten;
    }
    
    
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        //System.out.println(list.get(index));
        return list.get(index);
    }
    
    public Zet returnBestMove(boolean bestOrWorst) {
        int MAX = -100000;
        int MIN = 1000000;
        int best = -1;
        
        if (bestOrWorst){
            for (int i = 0; i < rootsChildrenScores.size(); ++i) { 
                if (MAX < rootsChildrenScores.get(i).score) {
                    MAX = rootsChildrenScores.get(i).score;
                    best = i;
                } 
            }   
        } else {
            for (int i = 0; i < rootsChildrenScores.size(); i++) {
                if (MIN > rootsChildrenScores.get(i).score) {
                    MIN = rootsChildrenScores.get(i).score;
                    best = i;
                }               
            }
        }
        //System.out.println(rootsChildrenScores.get(worst).zet.rij + " " + rootsChildrenScores.get(worst).zet.kol);
        return rootsChildrenScores.get(best).zet;
    }
    
    List<ZetEnScores> rootsChildrenScores;
    
    public void callMinimax(int diepte, boolean spelerIsMens){
        rootsChildrenScores = new ArrayList<>();
        minimax(diepte, spelerIsMens);
    }
    
    public int minimax(int diepte, boolean spelerIsMens){
        List<Zet> zettenMinimax = mogelijkeZetten();
        //System.out.println("Size van zettenMinimax: " + zettenMinimax.size());
        if (hasXWon()){ return diepte-10; }
        if (hasOWon()){ return 10-diepte; }
        
        if (zettenMinimax.isEmpty()) {
            return 0;
        }
                
        List<Integer> scores = new ArrayList<>();
        
        for (int i = 0; i < zettenMinimax.size(); i++) {
            
            Zet zet = zettenMinimax.get(i);
            //System.out.println("Zet: [" + zet.rij + "," + zet.kol + "]");
            if (spelerIsMens == true){
                placeAMove(zet, spelerX);
                //int huidigeScore = minimax(diepte+1, false);
                scores.add(minimax(diepte+1, false));                
                
            } else if (spelerIsMens == false){
                placeAMove(zet, spelerO);
                int huidigeScore = minimax(diepte+1, true);
                scores.add(huidigeScore);
                if (diepte == 0){
                    rootsChildrenScores.add(new ZetEnScores(huidigeScore, zet));
                }
            }
            vakken[zet.rij][zet.kol].setText("");
        } 
        //System.out.println(scores+ "-");
        return ((spelerIsMens) ? returnMin(scores) : returnMax(scores));
    }
    
    public boolean hasXWon(){ //Checks the rows
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerX) && vakken[j][1].getText().equals(spelerX) && vakken[j][2].getText().equals(spelerX) ||
                  (vakken[0][i].getText().equals(spelerX) && vakken[1][i].getText().equals(spelerX) && vakken[2][i].getText().equals(spelerX))){
                    return true;
               } if ((vakken[0][0].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][2].getText().equals(spelerX) ||
                       (vakken[0][2].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][0].getText().equals(spelerX)))){
                   return true;
               }
           }
        }
        return false;
    }

    public boolean hasOWon(){ //Checks the rows
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerO) && vakken[j][1].getText().equals(spelerO) && vakken[j][2].getText().equals(spelerO) ||
                  (vakken[0][i].getText().equals(spelerO) && vakken[1][i].getText().equals(spelerO) && vakken[2][i].getText().equals(spelerO))){
                    return true;
               } if ((vakken[0][0].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][2].getText().equals(spelerO) ||
                       (vakken[0][2].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][0].getText().equals(spelerO)))){
                   return true;
               }
           }
        }
        return false;
    }
} 