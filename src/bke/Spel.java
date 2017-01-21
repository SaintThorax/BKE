package bke;
import java.awt.*;
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
    public static boolean over = false, xWin = false, oWin = false;
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
                //System.out.println(vakken[i][j].hashCode()); 
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
            }
        }
        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
    }
    
    public void spelerAanZet(){ //Decides who's turn it is 
        if (aantalZetten % 2 != 0) { //aantalZetten is the amount of turns passed
            spelerAanZet = spelerX;
            UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
        }
        if (aantalZetten % 2 == 0) {
            spelerAanZet = spelerO;
            UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
        } 
    }
    
    public void spel(){ //Runs whenever a button gets clicked
        aantalZetten++; //The integer value of the amount of made moves
        mogelijkeZetten();
                
        spelerAanZet(); 
        geklikt.setText(spelerAanZet); //sets the text on a button to the string value of the current player
       
        checkSpelAfgelopen();     //checks if the game has finished   
        geklikt.setEnabled(false); //disables the pressed button
        System.out.println(waarde());
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
        System.out.println(zetten.hashCode());
        return zetten;
    }
    
    public int minimax(Spel status, int diepte, String spelerAanZet){
        /* als status kan ik geklikt gebruiken denk ik
        Als diepte aantalZetten
        spelerAanZet is wanneer de computerspeelt spelerO
        */
        List<Zet> zettenMinimax = mogelijkeZetten();
        if (diepte == 0 || zettenMinimax.size() -1 ==0 ){
            return 0; //spelWaarde();
            /**
             * Spelwaarde is een functie waarmee de waarde van het bord wordth berekent. Voor elke van de 8 win lijen 
             * rijen:[00-01-02], [10-11-12], [20-21-22]
             * kolommen: [00-10-20], [01-11-21], [02-12-22]
             * diagonalen: [00-11-22], [02-11-20]
             * als er een X en O op dezelfde lijn zitten is de score 0 punten
             * Asl er 1 unieke speler op een lijn zit is die lijn 1 punt
             * Als er 2 unieke spelers op een lijn zitten is die lijn 10 punten
             * Als er 3 unieke spelers op een lijn zitten is die lijn 100 punten is de speler de winnaar
             */
        }
        return 0;
    }
    
    public int waarde(){
        
        int waarde = 0;
        
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (vakken[j][0].getText().equals(vakken[j][1].getText()) && vakken[j][0].getText().equals(spelerAanZet)){
                    waarde = 100;
                    if (vakken[j][0].getText().equals(vakken[j][2].getText()) && vakken[j][0].getText().equals(spelerAanZet)){
                        waarde = 1000;
                    }                  
                }
            }
        }
        return waarde;
    }
} 