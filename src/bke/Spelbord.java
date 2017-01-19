/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author Thom
 */


class Position{
    public final int column;
    public final int row;
    
    public Position(int column, int row){
        this.column = column;
        this.row = row;
    }
}
enum gameState{
    Incomplete, Draw, KruisWin, RondWin;
}

public class Spelbord extends JPanel{
    public static int boven, links, onder, rechts;
    public static JButton[][] vakken;
    public static JButton[] Holster;
    public static JButton geklikt;
    public final static String spelerX = "X", spelerO = "O";

    public static boolean xWin = false, oWin = false;
    

    public Spelbord(){
        vakken = new JButton[3][3];
        Holster = new JButton[9];
        Position position = null;
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
                boven = 0;
                links = 0;
                onder = 0;
                rechts=0;
                
                add(vakken[i][j]);
                Holster[tel] = vakken[i][j];
                tel++;
            }
        }
    }
    
    public Spelbord(Spelbord from, Position position, String speler){
        vakken = new JButton[3][3];
        //System.out.println("Referred here");
        for (int i = 0; i < 3; i ++){
            for (int j = 0; j < 3; j++){
                vakken[i][j] = vakken[i][j];
            }
        }
       vakken[position.column][position.row].setText(Spel.spelerAanZet ? spelerX: spelerO);
    }
    
    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            geklikt = (JButton)e.getSource();
            new Spel().spel();
        }
    }
    
    public static void checkRijKolX(){
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
        }
    }
    public static void checkRijKolO(){
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
    public static void checkDiagonalenX(){
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
    public static void checkDiagonalenO(){
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

    public static void checkSpelAfgelopen(){ 
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
    public static void spelOver(){
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                vakken[i][j].setEnabled(false); // Schakelt alle vakken uit.
            }
        }
        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
    }
    

    
    public static ArrayList<Position> getAvailableStates(){
       ArrayList<Position> availableZetten = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (vakken[i][j].getText().equals(" ")){
                    availableZetten.add(new Position(i,j));
                }
            }
        }
        return availableZetten;
    }
    
    public void placeAMove(Point point, Spel Player){
        geklikt.setText(Spel.spelerAanZet ? spelerX : spelerO);
    }
    
    public static boolean hasXWon(){
        checkRijKolX();
        //.out.println("Rij" + xWin);
        checkDiagonalenX();
        //System.out.println("Kol" + xWin);
        return xWin;
    }
    public static boolean hasOWon(){
        //checkSpelAfgelopen();
        checkRijKolO();
        checkDiagonalenO();
        //System.out.println("O" + oWin);
        return oWin;
    }
    
}
