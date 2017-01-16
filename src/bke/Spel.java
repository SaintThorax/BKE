package bke;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 *
 * @author Thom
 */
public class Spel extends JPanel{
    final static String spelerX = "X", spelerO = "O";
    public static String spelerAanZet;
    public static boolean over = false, xWin = false, oWin = false;
    public static int scoreIntX, scoreIntO, test = 3;
    public static int boven, links, onder, rechts;
    public static JButton[][] vakken;
    public static int aantal_geklikt = 0;
    public Spel(){
                
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
    
    public void checkForWin(){ 
        checkRijen();
        checkKolommen();
        checkDiagonalenLR();
        checkDiagonalenRL();
        if (xWin == true || oWin == true){
            System.out.println("Het spel is gewonnen, checkForWin()");
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
    public void spelerAanZet(){
        if (aantal_geklikt % 2 != 0) { 
            spelerAanZet = spelerX;
            UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
        }
        if (aantal_geklikt % 2 == 0) {
            spelerAanZet = spelerO;
            UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
        } 
    }

    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton geklikt = (JButton)e.getSource();          
            aantal_geklikt++;
            
            spelerAanZet(); 
            geklikt.setText(spelerAanZet);
            
            checkForWin();           
            geklikt.setEnabled(false);  
            
            //Gelijk spel conditie
            if(aantal_geklikt >= 9 && over == false){
                for(int i = 0;i<3;i++){
                    for (int j=0;j<3;j++){     
                        vakken[i][j].setBackground(Color.decode("#b2b2b2"));
                    }
                over = true;
                }
            }

            if(xWin == true){
                scoreIntX = scoreIntX + 1;
                String scoreStrX = String.valueOf(scoreIntX + " ");
                BKE.scoreNummerX.setText(scoreStrX);
                over = true;
            }
            if(oWin == true){
                scoreIntO = scoreIntO + 1;
                String scoreStrO = String.valueOf(scoreIntO + " ");
                BKE.scoreNummerO.setText(scoreStrO);
                over = true;
            }
            if(over == true){
                for(int i = 0;i<3;i++){
                    for (int j=0;j<3;j++){
                        vakken[i][j].setEnabled(false); // Schakelt alle vakken uit.
                    }
                }
                BKE.reset.setBackground(Color.decode("#989B9C"));
                UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
            }
        }
    }
} 