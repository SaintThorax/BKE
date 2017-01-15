/*
 * probeer UI class te veranderen in een new jpanel van class BKE_Quick_Dirty
 * 
 * 
 */
package bke;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BKE extends JFrame {

    public static Font spelFont = new Font("Berlin Sans FB", Font.PLAIN, 100);
    public static Font scoreLetterFont = new Font("Helvetica", Font.BOLD, 150);
    public static Font scoreNummerFont = new Font("Helvetica", Font.PLAIN, 60);
    public static Font titelFont = new Font("Arial", Font.PLAIN, 60);
    public static Font algemeenFont = new Font("Helvetica", Font.PLAIN, 20);
    
    
    public static JLabel scoreLabX, scoreTestX, scoreLabO, scoreTestO, Titel;
    private JButton reset, menuKnop, tegenSpeler;
    public static JButton[][] vakken;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Titel = new JLabel("Boter-kaas-en-eieren", SwingConstants.CENTER);
        Titel.setFont(titelFont);
        Titel.setPreferredSize(new Dimension(600,100));
        
        JPanel Menu = new JPanel();
      
        tegenSpeler = new JButton("Speler - Speler");
        tegenSpeler.setFont(algemeenFont);
        tegenSpeler.setPreferredSize(new Dimension(500,100));
        tegenSpeler.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            createSpelGUI();
        }
    });
        
        Menu.add(Titel);
        Menu.add(tegenSpeler);    
        
        setContentPane(Menu);
        setSize(800,800);
        setTitle("Quick and Dirty");
        setVisible(true); 
    }
        
    public void createSpelGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel UI = new JPanel();
        UI.setLayout(new BorderLayout());
        
        JPanel scoreX = new JPanel();
        
        scoreLabX = new JLabel(Spel.X, SwingConstants.LEFT);
        scoreTestX = new JLabel("- ", SwingConstants.RIGHT);
        
        scoreLabX.setFont(scoreLetterFont);
        scoreTestX.setFont(scoreNummerFont);
        
        scoreLabX.setForeground(Color.LIGHT_GRAY);
        scoreTestX.setForeground(Color.GRAY);
        
        scoreX.setLayout(new GridLayout(1,2));
        scoreX.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        
        scoreX.add(scoreLabX);
        scoreX.add(scoreTestX);
        
        JPanel scoreO = new JPanel();
        
        scoreLabO = new JLabel(Spel.O, SwingConstants.LEFT);
        scoreTestO = new JLabel("- ", SwingConstants.RIGHT);
        
        scoreLabO.setFont(scoreLetterFont);
        scoreTestO.setFont(scoreNummerFont);
        
        scoreLabO.setForeground(Color.LIGHT_GRAY);
        scoreTestO.setForeground(Color.GRAY);        
        
        scoreO.setLayout(new GridLayout(1,2));
        scoreO.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        
        scoreO.add(scoreLabO);
        scoreO.add(scoreTestO);
        
        JPanel score = new JPanel();
        
        score.add(scoreX);
        score.add(scoreO);
        
        score.setPreferredSize(new Dimension(10,100));
        score.setLayout(new GridLayout(1,2,5,5));
        score.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        
        reset = new JButton("Nieuw spel");
        reset.setFont(algemeenFont);
        reset.addActionListener(new ResetHandeler());
        
        menuKnop = new JButton("Menu");
        menuKnop.setFont(algemeenFont);
        menuKnop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                createMenuGUI();
            }
        });
        
        UI.add(menuKnop);
        UI.add(reset);
        UI.setPreferredSize(new Dimension(10, 100));
        UI.setLayout(new GridLayout(1,2,10,0));

        JPanel spelBord = new JPanel();
        spelBord.setLayout(new BorderLayout(60,40));
        
        Spel spel = new Spel();
        spel.setBorder(BorderFactory.createEmptyBorder(10,150,10,150));
        
        spelBord.add(score, BorderLayout.PAGE_START);
        spelBord.add(UI, BorderLayout.PAGE_END);
        spelBord.add(spel);
        
        setContentPane(spelBord);
        setSize(800,800);
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
                Spel.xWin = false;
                Spel.oWin = false;
                }
            }       
        }
    }
}

class Spel extends JPanel{
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
            BKE.scoreTestX.setText(scoreStrX);
            }
            if(oWin == true){
            scoreIntO = scoreIntO + 1;
            String scoreStrO = String.valueOf(scoreIntO);
            BKE.scoreTestO.setText(scoreStrO);
            }
        }
    }
} 