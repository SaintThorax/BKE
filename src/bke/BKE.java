/*
 * probeer UI class te veranderen in een new jpanel van class BKE
 * 
 * 
 */
package bke;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BKE extends JFrame {

    public static Font spelFont = new Font("Berlin Sans FB", Font.PLAIN, 100);
    public static Font textFont = new Font("Arial", Font.PLAIN, 20);
    
    public static JLabel scoreLabX, scoreLabO;
    private JButton reset, menuKnop, tegenSpeler;
    public static JButton[][] vakken;
    public static JTextField gewonnen;
    static JFrame frame;
      
    public static void main(String[] args) {        
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                System.out.println("Test");
                new BKE().createSpelGUI();
            }
        });
    }
    public void createMenuGUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel Menu = new JPanel();
      
        tegenSpeler = new JButton("Speler - Speler");
        tegenSpeler.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            createSpelGUI();
        }
    });

        Menu.add(tegenSpeler);    
        
        setContentPane(Menu);
        setSize(600,600);
        setTitle("Quick and Dirty");
        setVisible(true); 
    }
        
    public void createSpelGUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel UI = new JPanel();
        UI.setLayout(new BorderLayout());
//        gewonnen = new JTextField(10);
//        gewonnen.setFont(textFont);
//        gewonnen.setHorizontalAlignment(SwingConstants.CENTER);
        
        scoreLabX = new JLabel();
        reset = new JButton("Reset");
        reset.addActionListener(new ResetHandeler());
        
        menuKnop = new JButton("Menu");
        menuKnop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                createMenuGUI();
            }
        });
        
        UI.add(menuKnop, BorderLayout.LINE_START);
        UI.add(scoreLabX, BorderLayout.CENTER);
        UI.add(reset, BorderLayout.LINE_END);
        UI.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        JPanel spelBord = new JPanel();
        //spelBord.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));

        spelBord.setLayout(new BorderLayout(60,40));
        
//        JButton button = new JButton("Button 3 (LINE_START)");
//        spelBord.add(button, BorderLayout.LINE_START);
//        button.setPreferredSize(new Dimension(0, 0));   
//        button = new JButton("5 (LINE_END)");
//        spelBord.add(button, BorderLayout.LINE_END);
//        button.setPreferredSize(new Dimension(0, 0));
        
        spelBord.add(UI, BorderLayout.PAGE_START);
        UI.setPreferredSize(new Dimension(0, 100));

        Spel TEST = new Spel();
        
        TEST.setBorder(BorderFactory.createEmptyBorder(10,50,0,50));
        //TEST.setLayout(new BorderLayout());
        
        spelBord.add(TEST);
        //spelBord.add(new Spel());
        
        setContentPane(spelBord);
        setSize(600,600);
        setTitle("Quick and Dirty");
        setVisible(true);      
    }

    class ResetHandeler implements ActionListener{        
    public void actionPerformed(ActionEvent e){
        for (int i =0; i<3; i++){
            for (int j=0; j<3;j++){
                vakken[i][j].setEnabled(true);
                vakken[i][j].setText("");
                Spel.aantal_geklikt = 0;
                Spel.aantal_zetten = 0;
                Spel.over = false;
                }
            }       
        }
    }
}

class Spel extends JPanel{
    final String X = "X", O = "O";
    public static boolean over = false;
    public static int scoreIntX, scoreIntO;
    public static int aantal_geklikt = 0, aantal_zetten = 0;
    public Spel(){

        setLayout(new GridLayout(4,3,5,5));

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
                        //BKE.gewon1 1 nen.setText("X wint!");
                        scoreIntX =+ 1;
                        String scoreStrX = String.valueOf(scoreIntX);
                        BKE.scoreLabX.setText(scoreStrX);
                        over = true;
                    } 
                    if(BKE.vakken[0][i].getText().equals(X) && BKE.vakken[1][i].getText().equals(X) && BKE.vakken[2][i].getText().equals(X)){
                        //BKE.gewonnen.setText("X wint!");
                        over = true;
                    }
                    if(BKE.vakken[2][0].getText().equals(X) && BKE.vakken[1][1].getText().equals(X) && BKE.vakken[0][2].getText().equals(X) ||
                       BKE.vakken[0][0].getText().equals(X) && BKE.vakken[1][1].getText().equals(X) && BKE.vakken[2][2].getText().equals(X)){
                        //BKE.gewonnen.setText("X wint!");
                        over = true;
                    }
                    
                    //Horizontale winconditie voor speler O
                    if (BKE.vakken[j][0].getText().equals(O) && BKE.vakken[j][1].getText().equals(O) && BKE.vakken[j][2].getText().equals(O)){
                        //BKE.gewonnen.setText("O wint!");
                        over = true;
                    } // Verticale winconditie voor speler O
                    if(BKE.vakken[0][i].getText().equals(O) && BKE.vakken[1][i].getText().equals(O) && BKE.vakken[2][i].getText().equals(O)){
                        //BKE.gewonnen.setText("O wint!");
                        over = true;
                    } //Diagonale winconditie voor speler O
                    if(BKE.vakken[2][0].getText().equals(O) && BKE.vakken[1][1].getText().equals(O) && BKE.vakken[0][2].getText().equals(O) ||
                       BKE.vakken[0][0].getText().equals(O) && BKE.vakken[1][1].getText().equals(O) && BKE.vakken[2][2].getText().equals(O)){
                        //BKE.gewonnen.setText("O wint!");
                        over = true;
                    }
                    
                    //Gelijk spel conditie
                    if(aantal_zetten >= 9 && over == false){
                        //BKE.gewonnen.setText("Gelijkspel");
                        over = true;
                    }//Schakelt alle vakken uit
                    if(over == true){
                        BKE.vakken[i][j].setEnabled(false);
                        //System.out.println(BKE.vakken[i][j]);
                    }
                }
            }
        }
    }
} 