package bke;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;
/**
 *
 * @author Thom
 * test2
 * Ga Verder met implementeren minimax, via bron:
 * http://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
 */

class Zet{
    public int rij,kol;
    public Zet(){
        this.rij = rij;
        this.kol = kol;
    }
}

public class Spel extends JPanel{
    
    final static String spelerX = "X", spelerO = "O";
    public static JButton geklikt;
    public static String maakZet;
    public static boolean over = false, xWin = false, oWin = false;
    public static int scoreIntX, scoreIntO;
    public static int boven, links, onder, rechts;
    public static JButton[][] vakken;
    public static boolean isMax;
    public static int aantalZetten = 0;
    
    Zet bestMove;
    
    public static ArrayList<Integer> mogelijkeZetten;
    
    public Spel(){
       initSpel(); 
    }

    
    public void initSpel(){
        
        setLayout(new GridLayout(3,3,5,5));
        vakken = new JButton[3][3];
        int tel = 0;
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                vakken[i][j] = new JButton();
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
                tel++;
            }
        }
    }
    
    public int minimax(JButton[][] vakken, int diepte, boolean isMax){
       int best = -1000;
       if (xWin == true){
           return 10 - aantalZetten;
       } 
       if (oWin == true){
            return aantalZetten - 10;
        }
       if (over == true && xWin == false && oWin == false) {
           return 0;
       }
       
       
       if (isMax){
           best = -1000;
           System.out.println("Max" + diepte);
           for(int i = 0; i<3; i++){
               for (int j = 0; j<3; j++){
                   if (!(vakken[i][j].getText().equals(""))){
                    vakken[i][j].setText(spelerX);
                    best = Math.max(best,minimax(vakken, diepte+1, !isMax));
                    vakken[i][j].setText("");
                   }
               }
           }
       return best; 
       }
       else {
           System.out.println("Min" + diepte);
           best = 1000;
           for(int i = 0; i<3; i++){
               for(int j=0; j<3;j++){
                   if(!(vakken[i][j].getText().equals(""))){
                    vakken[i][j].setText(spelerO);
                    best = Math.min(best,minimax(vakken, diepte+1, !isMax));
                    vakken[i][j].setText("");
                   }
               }
           }
           return best;
       }
    }
    
   Zet findBestMove(JButton[][] neppeVakken){
        int bestVal = -1000;
        bestMove.rij = -1;
        bestMove.kol = -1;
        //return vakken[0][0];
        
        for (int i = 0; i<3; i++){
            for (int j=0; j<3; j++){
                if(!(neppeVakken[i][j].getText().equals(""))){
                    System.out.println("Test1");
                    neppeVakken[i][j].setText(spelerO);
                    System.out.println("Test1.2");
                    
                    int moveVal = minimax(neppeVakken, 0, false);
                    System.out.println("Test1.3");
                    neppeVakken[i][j].setText("");
                    System.out.println("Test1.4");
                    
                    if(moveVal > bestVal){
                        System.out.println("Test1.5");
                        bestMove.rij = i;
                        bestMove.kol = j;
                        bestVal = moveVal;
                    }
                    System.out.println("Test2");
                }
                System.out.println("Test2");
            }
            System.out.println("Test3");
        }
        System.out.println("De beste zet is " + bestVal);
        System.out.println("RIJ: " + bestMove.rij + "KOL: "+ bestMove.kol);
        System.out.println(bestMove);
        return bestMove;
   }
    
    //int diepte = 0;
    public ArrayList<Integer> mogelijkeZetten(){
        //diepte += 1;
        mogelijkeZetten = new ArrayList<>();
        
        for (int i = 0; i < 3; i++){
            for(int j = 0; j<3; j++){
                if(vakken[i][j].getText().equals("")){
                    mogelijkeZetten.add(i);
                    mogelijkeZetten.add(j);   
                } 
            }
        }
        
        for (int i = 0; i <mogelijkeZetten.size();i+=2){
            System.out.println(mogelijkeZetten.subList(i,i+2));
        }
        System.out.println("----------");
        return mogelijkeZetten;
    }
    
    
    public void checkRijen(){
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerX) && vakken[j][1].getText().equals(spelerX) && vakken[j][2].getText().equals(spelerX) ||
                  (vakken[j][0].getText().equals(spelerO) && vakken[j][1].getText().equals(spelerO) && vakken[j][2].getText().equals(spelerO))){
                    if (vakken[j][i].getText().contains("X")){
                        System.out.println("x win horizon");
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
    public void checkDiagonalenRL(){
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

    public void checkSpelAfgelopen(){ 
        checkRijen();
        checkKolommen();
        checkDiagonalenLR();
        checkDiagonalenRL();
        if (xWin == true){
            xWin();
            spelOver();
        }
        
        else if (oWin == true){
            oWin();
            spelOver();
        }
            //minimax();
        else if(aantalZetten >= 9 && over == false){
            for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){     
                    vakken[i][j].setBackground(Color.decode("#b2b2b2"));
                }
            spelOver();
            }
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
    
    public void maakZet(){
        if (aantalZetten % 2 != 0) { 
            maakZet = spelerX;
            UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
            //isMax = true;
            geklikt.setText(maakZet);
        }
        if (aantalZetten % 2 == 0) {
            maakZet = spelerO;
            UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
            isMax = false;
            geklikt.setText(maakZet);       
            //besteZet.setText(maakZet);
        } 
    }
    
    public void spel(){
        aantalZetten++;
        maakZet();

//        if (aantalZetten > 1){
//            Zet besteZet = findBestMove(vakken);
//            System.out.println(besteZet);
//        }
        
        System.out.println("Zet" + aantalZetten);
        //geklikt.setText(maakZet);
        Zet besteZet = findBestMove(vakken);
        //mogelijkeZetten();
        checkSpelAfgelopen();   
        geklikt.setEnabled(false); 
    }
    
    
    
    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            geklikt = (JButton)e.getSource();   
            spel();
        }
    }
    
    public void xWin(){
        System.out.println("hier komt het x");
        scoreIntX = scoreIntX + 1;
        String scoreStrX = String.valueOf(scoreIntX + " ");
        BKE.scoreNummerX.setText(scoreStrX);
        spelOver();
    }
    public void oWin(){
        if(oWin == true){
            System.out.println("Hier komt het ");
            scoreIntO = scoreIntO + 1;
            String scoreStrO = String.valueOf(scoreIntO + " ");
            BKE.scoreNummerO.setText(scoreStrO);
        }
    }  
} 