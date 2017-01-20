package bke;
import java.awt.*;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Thom
 */

class Zet{
    int kol, rij;
    public Zet(int kol, int rij){
        this.kol = kol;
        this.rij = rij;
    }
}

public class Spel extends JPanel{
    
    final static String spelerX = "X", spelerO = "O";
    public static JButton geklikt;
    public static String spelerAanZet;
    public static boolean over = false, xWin = false, oWin = false, xZouWinnen, oZouWinnen;
    public static int scoreIntX, scoreIntO;
    public static int boven, links, onder, rechts;
    public static JButton[][] vakken;
    public static int aantalZetten = 0;
    
    public ArrayList<Integer> moves; 
    public List<Zet> zetten, zetMinimax, mogelijkeZettenMM;
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
                vakken[i][j] = new JButton(" "); //vakken is a button[vertical][horizontal]
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
                
                add(vakken[i][j]);
                tel++;
            }
        }
    }
       
    public void checkRijen(){ //Checks the rows
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerX) && vakken[j][1].getText().equals(spelerX) && vakken[j][2].getText().equals(spelerX) ||
                  (vakken[j][0].getText().equals(spelerO) && vakken[j][1].getText().equals(spelerO) && vakken[j][2].getText().equals(spelerO))){
                    if (vakken[j][i].getText().contains("X")){
                        //vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        xZouWinnen = true;
                    } else {
                        //vakken[j][i].setBackground(Color.decode("#E74C3C")); 
                        oZouWinnen = true;
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
                        //vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        xZouWinnen = true;
                    } else {
                        //vakken[j][i].setBackground(Color.decode("#E74C3C")); 
                        oZouWinnen = true;
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
                        //for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#2C3E50"));
                        xZouWinnen = true;
                        return;
                    } 
                    else if (vakken[0][0].getText().contains("O")){ //Voert uit wanneer O van linksboven naar rechtsonder 3 op een rij heeft
                        //for (int n = 0; n < 3 ; n++) vakken[n][n].setBackground(Color.decode("#E74C3C"));                     
                        oZouWinnen = true;
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
                        //for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#2C3E50"));  
                        xZouWinnen = true;
                        return;
                    } 
                    else if(vakken[0][2].getText().contains("O")){ //Voert uit wanneer O van rechtsboven naar linksonder 3 op een rij heeft
                        //for (int n = 0; n < 3 ; n++) vakken[2-n][n].setBackground(Color.decode("#E74C3C"));   
                        oZouWinnen = true;
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
            }
        }
    }   
    
    public void checkSpelGewonnen(){ //checks if the game is over
        checkRijen();
        checkKolommen();
        checkDiagonalenLR();
        checkDiagonalenRL();
    }  
    public void spelOver(){ //Runs when game is over and disables all buttons
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                vakken[i][j].setEnabled(false); // Schakelt alle vakken uit.
            }
        }
        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
    }
    
    public void xZet(){
        spelerAanZet = spelerX;
        UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
        geklikt.setText(spelerAanZet); //sets the text on a button to the string value of the current player
    }
    public void oZet(){
        spelerAanZet = spelerO;
        System.out.println(spelerAanZet);
        
        int next = returnNextMove();
        //vakken[computersZet.kol][computersZet.rij].setText(spelerO);
        System.out.println(next + "This is next");
    }
    
    public void spel(){ //Runs whenever a button gets clicked
        aantalZetten++; //The integer value of the amount of made moves

        xZet();             
        oZet();    
        geklikt.setEnabled(false); //disables the pressed button
        
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
    
    Zet computersZet;
    
    public int minimax1(int aantalZetten, String speler){
        checkSpelGewonnen();
        if (xZouWinnen == true) return aantalZetten - 10;
        if (oZouWinnen == true) return 10 - aantalZetten;
        List<Zet> mogelijkeZettenMM = mogelijkeZetten();
        if (mogelijkeZettenMM.isEmpty()) return 0;
        
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int currentScore;

        for (int i = 0; i < mogelijkeZettenMM.size(); i++){
            //System.out.println(mogelijkeZettenMM.size());
            Zet zet = mogelijkeZettenMM.get(i);
            if(spelerAanZet.equals(spelerO)){
                maakEenZet(zet, spelerO); 
                currentScore = minimax1(aantalZetten + 1, spelerX);
                
                max = Math.max(currentScore, max);
                
                if(aantalZetten == 0)System.out.println("Score for position "+(i+1)+" = "+currentScore);
                if(currentScore >= 0){ 
                    if (aantalZetten == 0){
                        System.out.println(currentScore);
                        computersZet = zet;
                    }
                } 
                if(currentScore == 1){ 
                    vakken[zet.kol][zet.rij].setText(" "); 
                    break;
                } 
                if(i == mogelijkeZettenMM.size()-1 && max < 0){
                    if(aantalZetten == 0){
                        computersZet = zet;
                    }
                }
                
            } else if (spelerAanZet.equals(spelerX)){
                maakEenZet(zet, spelerX);
                currentScore = minimax1(aantalZetten+1, spelerO); 
                min = Math.min(currentScore, min);
                System.out.println(min);
                
                if (min == -1){
                    vakken[zet.kol][zet.rij].setText(" ");
                    System.out.println("dit is hem");
                   // break;
                }
                vakken[zet.kol][zet.rij].setText(" ");
            }
        }
        //System.out.println(computersZet.rij);
        return (spelerAanZet.equals(spelerO)? min : max);
    }
    
    public int returnNextMove() {
        checkSpelGewonnen();
        if (xWin == true || oWin == true){
            return -1;
        }
        minimax1(0, spelerO);
        return computersZet.kol + computersZet.rij;
    }
    
    public List<Zet> mogelijkeZetten(){
        zetten = new ArrayList<>();
        for (int i = 0; i<3; i++){
            for(int j= 0; j<3; j++){
                if (vakken[i][j].getText().equals(" ")){
                    zetten.add(new Zet(i,j));
                }
            }
        }
        //System.out.println(zetten);
        return zetten;
    }
    
    public void maakEenZet(Zet zet, String speler){
        vakken[zet.kol][zet.rij].setText(spelerO);
    }
    
} 