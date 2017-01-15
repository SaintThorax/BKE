package bke;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Thom
 */
public class Spel extends JPanel{
    final static String X = "X", O = "O";
    public static boolean over = false, xWin = false, oWin = false;
    public static int scoreIntX, scoreIntO, test = 3;
    private int boven, links, onder, rechts;
    public static int aantal_geklikt = 0;
    public Spel(){

        setLayout(new GridLayout(3,3,5,5));
        BKE.vakken = new JButton[3][3];
        int tel = 0;
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                BKE.vakken[i][j] = new JButton();
                BKE.vakken[i][j].setFont(BKE.spelFont);
                BKE.vakken[i][j].setBackground(Color.decode("#ECF0F1"));
                if (tel > 2) {
                    //BKE.vakken[i][j].setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.BLACK)); //boven
                    boven = 3;
                }
                if (tel % 3 != 0){
                    //BKE.vakken[i][j].setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.BLACK)); //links
                    links = 3;
                }
                if (tel < 6) {
                //BKE.vakken[i][j].setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK)); //onder
                    onder = 3;
                }
                if (tel % 3 != 2){
                //BKE.vakken[i][j].setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK)); //rechts
                rechts = 3;
                }
                BKE.vakken[i][j].addActionListener(new KnopHandeler());
                BKE.vakken[i][j].setBorder(BorderFactory.createMatteBorder(boven,links,onder,rechts,Color.decode("#696969")));
                
                boven = 0;
                links = 0;
                onder = 0;
                rechts=0;
                add(BKE.vakken[i][j]);
                tel++;
            }
        }
    }


    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton geklikt = (JButton)e.getSource();
            aantal_geklikt++;

            if (aantal_geklikt % 2 != 0) {
                UIManager.put("Button.disabledText", Color.decode("#2C3E50"));
                geklikt.setText("X");
            }
            if (aantal_geklikt % 2 == 0) {
                UIManager.put("Button.disabledText", Color.decode("#E74C3C"));
                geklikt.setText("O");
            }

            geklikt.setEnabled(false);
            for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){                    
                    //vakken[verticaal][horizontaal]
                    if(BKE.vakken[j][0].getText().equals(X) && BKE.vakken[j][1].getText().equals(X) && BKE.vakken[j][2].getText().equals(X)){
                        BKE.vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        System.out.println("kms");
                        over = true;
                        xWin = true;
                    } 
                    if(BKE.vakken[0][i].getText().equals(X) && BKE.vakken[1][i].getText().equals(X) && BKE.vakken[2][i].getText().equals(X)){
                        BKE.vakken[j][i].setBackground(Color.decode("#2C3E50"));
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        xWin = true;
                        over = true;
                    }
                    if(BKE.vakken[2][0].getText().equals(X) && BKE.vakken[1][1].getText().equals(X) && BKE.vakken[0][2].getText().equals(X)){
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        xWin = true;
                        over = true;
                        for (int n = 0; n < 3 ; n++){
                            BKE.vakken[2-n][n].setBackground(Color.decode("#2C3E50"));
                            System.out.println(n);
                        } break;
                    }
                    if (BKE.vakken[0][0].getText().equals(X) && BKE.vakken[1][1].getText().equals(X) && BKE.vakken[2][2].getText().equals(X)){
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        xWin = true;
                        over = true;
                        for (int n = 0; n < 3 ; n++){
                            BKE.vakken[n][n].setBackground(Color.decode("#2C3E50"));
                            System.out.println(n);    
                        } break;
                    }

                    //Horizontale winconditie voor speler O
                    if (BKE.vakken[j][0].getText().equals(O) && BKE.vakken[j][1].getText().equals(O) && BKE.vakken[j][2].getText().equals(O)){
                        BKE.vakken[j][i].setBackground(Color.decode("#E74C3C"));               
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        oWin = true;
                        over = true;
                    } // Verticale winconditie voor speler O
                    if(BKE.vakken[0][i].getText().equals(O) && BKE.vakken[1][i].getText().equals(O) && BKE.vakken[2][i].getText().equals(O)){
                        BKE.vakken[j][i].setBackground(Color.decode("#E74C3C"));
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        oWin = true;
                        over = true;
                    } //Diagonale winconditie voor speler O
                    if(BKE.vakken[2][0].getText().equals(O) && BKE.vakken[1][1].getText().equals(O) && BKE.vakken[0][2].getText().equals(O)){
                       UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        oWin = true;
                        over = true;
                        for (int n = 0; n < 3 ; n++){
                            BKE.vakken[2-n][n].setBackground(Color.decode("#E74C3C"));
                            System.out.println(n);    
                        } break; 
                    }
                    if(BKE.vakken[0][0].getText().equals(O) && BKE.vakken[1][1].getText().equals(O) && BKE.vakken[2][2].getText().equals(O)){
                        UIManager.put("Button.disabledText", Color.decode("#FFFFFF"));
                        oWin = true;
                        over = true;
                        for (int n = 0; n < 3 ; n++){
                            BKE.vakken[n][n].setBackground(Color.decode("#E74C3C"));
                            System.out.println(n);    
                        } break;
                    }

                    //Gelijk spel conditie
                    if(aantal_geklikt >= 9 && over == false){
                        over = true;
                    }//Schakelt alle vakken uit
                }
            } 
            if(over == true){
                System.out.println("Spel is over");             
                for(int i = 0;i<3;i++){
                    for (int j=0;j<3;j++){
                        BKE.vakken[i][j].setEnabled(false);
                    }
                }
            }

            if(xWin == true){
                scoreIntX = scoreIntX + 1;
                String scoreStrX = String.valueOf(scoreIntX + " ");
                BKE.scoreNummerX.setText(scoreStrX);
            }
            if(oWin == true){
                scoreIntO = scoreIntO + 1;
                String scoreStrO = String.valueOf(scoreIntO + " ");
                BKE.scoreNummerO.setText(scoreStrO);
            }
        }
    }
} 