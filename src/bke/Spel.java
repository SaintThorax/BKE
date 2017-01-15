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
    public static int scoreIntX, scoreIntO;
    public static int aantal_geklikt = 0, aantal_zetten = 0;
    public Spel(){

        setLayout(new GridLayout(3,3,5,5));

        BKE.vakken = new JButton[3][3];
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                BKE.vakken[i][j] = new JButton("");
                BKE.vakken[i][j].addActionListener(new KnopHandeler());
                add(BKE.vakken[i][j]);
            }
        }
    }


    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton geklikt = (JButton)e.getSource();
            geklikt.setFont(BKE.spelFont);

            aantal_geklikt++;
            aantal_zetten++;

            if (aantal_geklikt % 2 != 0) geklikt.setText("X");
            if (aantal_geklikt % 2 == 0) geklikt.setText("O");

            geklikt.setEnabled(false);

            for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){
                    //vakken[verticaal][horizontaal]
                    if(BKE.vakken[j][0].getText().equals(X) && BKE.vakken[j][1].getText().equals(X) && BKE.vakken[j][2].getText().equals(X)){
                        over = true;
                        xWin = true;
                    } 
                    if(BKE.vakken[0][i].getText().equals(X) && BKE.vakken[1][i].getText().equals(X) && BKE.vakken[2][i].getText().equals(X)){
                        xWin = true;
                        over = true;
                    }
                    if(BKE.vakken[2][0].getText().equals(X) && BKE.vakken[1][1].getText().equals(X) && BKE.vakken[0][2].getText().equals(X) ||
                       BKE.vakken[0][0].getText().equals(X) && BKE.vakken[1][1].getText().equals(X) && BKE.vakken[2][2].getText().equals(X)){
                        xWin = true;
                        over = true;
                    }

                    //Horizontale winconditie voor speler O
                    if (BKE.vakken[j][0].getText().equals(O) && BKE.vakken[j][1].getText().equals(O) && BKE.vakken[j][2].getText().equals(O)){
                        oWin = true;
                        over = true;
                    } // Verticale winconditie voor speler O
                    if(BKE.vakken[0][i].getText().equals(O) && BKE.vakken[1][i].getText().equals(O) && BKE.vakken[2][i].getText().equals(O)){
                        oWin = true;
                        over = true;
                    } //Diagonale winconditie voor speler O
                    if(BKE.vakken[2][0].getText().equals(O) && BKE.vakken[1][1].getText().equals(O) && BKE.vakken[0][2].getText().equals(O) ||
                       BKE.vakken[0][0].getText().equals(O) && BKE.vakken[1][1].getText().equals(O) && BKE.vakken[2][2].getText().equals(O)){
                        oWin = true;
                        over = true;
                    }

                    //Gelijk spel conditie
                    if(aantal_zetten >= 9 && over == false){
                        //BKE_Quick_Dirty.gewonnen.setText("Gelijkspel");
                        over = true;
                    }//Schakelt alle vakken uit
                    if(over == true){
                        BKE.vakken[i][j].setEnabled(false);
                        //System.out.println(BKE_Quick_Dirty.vakken[i][j]);
                    }
                }
            } 
            if(xWin == true){
            scoreIntX = scoreIntX + 1;
            String scoreStrX = String.valueOf(scoreIntX);           
            BKE.scoreNummerX.setText(scoreStrX);
            }
            if(oWin == true){
            scoreIntO = scoreIntO + 1;
            String scoreStrO = String.valueOf(scoreIntO);
            BKE.scoreNummerO.setText(scoreStrO);
            }
        }
    }
} 