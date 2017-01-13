/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bke;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class BKE extends JFrame {
    
    JPanel cards;
    static JFrame frame;
    private JButton menu;
      
    public static void main(String[] args) {        
        frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createGUI();
    }
    
    public static void createGUI(){

                
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout(60,60));
        
        JButton button = new JButton("Button 1 (PAGE_START)");
        pane.add(button, BorderLayout.PAGE_START);
        button.setPreferredSize(new Dimension(0, 0));

        button = new JButton("Button 3 (LINE_START)");
        pane.add(button, BorderLayout.LINE_START);
        button.setPreferredSize(new Dimension(0, 0));   
        button = new JButton("Long-Named Button 4 (PAGE_END)");
        pane.add(button, BorderLayout.PAGE_END);
        button.setPreferredSize(new Dimension(0, 0));
        button = new JButton("5 (LINE_END)");
        pane.add(button, BorderLayout.LINE_END);
        button.setPreferredSize(new Dimension(0, 0));
        
        //contentPane.setSize(400,400);
        pane.add(new Spel());
        
        BKE bke = new BKE();     
        
        frame.setSize(600,600);
        frame.setTitle("Quick and Dirty");
        frame.setVisible(true);      
    }
    
    public void naarMenu(){
        System.out.println("Freeze all motor functions");
        frame.setContentPane(new Menu());
        frame.revalidate();
    }
    public void naarSpelerS(){
        System.out.println("De-freeze all motor functions");
        this.dispose();
        createGUI();
    }    
}


class Menu extends JPanel{
    private JButton tegenSpeler;
    
    
    public Menu(){
        tegenSpeler = new JButton("Speler - Speler");
        tegenSpeler.addActionListener(new CODHandeler());
        
        add(tegenSpeler);
    }
    
    class CODHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            BKE speler = new BKE();
            speler.naarSpelerS();
        }
    }
}

class Spel extends JPanel{
    
    private JButton[][] vakken;
    private Font spelFont = new Font("Berlin Sans FB", Font.PLAIN, 100);
    private Font textFont = new Font("Arial", Font.PLAIN, 20);
    
    private boolean over = false;
    private JButton reset, menu;
    private int aantal_geklikt = 0, aantal_zetten = 0;
    private JTextField gewonnen;
    final String X = "X", O = "O";
    private JFrame BKE;
    
    public Spel(){
               
        setLayout(new GridLayout(4,3,5,5));
        
        gewonnen = new JTextField(10);
        gewonnen.setFont(textFont);
        gewonnen.setHorizontalAlignment(SwingConstants.CENTER);
        
        reset = new JButton("Reset");
        reset.addActionListener(new ResetHandeler());
        
        menu = new JButton("Menu");
        menu.addActionListener(new MenuHandeler());
        
        add(menu);
        add(gewonnen);
        add(reset);


        vakken = new JButton[3][3];
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                vakken[i][j] = new JButton("");
                vakken[i][j].addActionListener(new KnopHandeler());
                add(vakken[i][j]);
            }
        }
    }
    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){

            
            JButton geklikt = (JButton)e.getSource();
            geklikt.setFont(spelFont);
            
            aantal_geklikt++;
            aantal_zetten++;
            
            if (aantal_geklikt % 2 != 0) geklikt.setText("X");
            if (aantal_geklikt % 2 == 0) geklikt.setText("O");
            
            geklikt.setEnabled(false);
            
            for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){
                    //vakken[verticaal][horizontaal]
                    if(vakken[j][0].getText().equals(X) && vakken[j][1].getText().equals(X) && vakken[j][2].getText().equals(X)){
                        gewonnen.setText("X wint!");
                        over = true;
                    } 
                    if(vakken[0][i].getText().equals(X) && vakken[1][i].getText().equals(X) && vakken[2][i].getText().equals(X)){
                        gewonnen.setText("X wint!");
                        over = true;
                    }
                    if(vakken[2][0].getText().equals(X) && vakken[1][1].getText().equals(X) && vakken[0][2].getText().equals(X) ||
                       vakken[0][0].getText().equals(X) && vakken[1][1].getText().equals(X) && vakken[2][2].getText().equals(X)){
                        gewonnen.setText("X wint!");
                        over = true;
                    }
                    
                    //Horizontale winconditie voor speler O
                    if (vakken[j][0].getText().equals(O) && vakken[j][1].getText().equals(O) && vakken[j][2].getText().equals(O)){
                        gewonnen.setText("O wint!");
                        over = true;
                    } // Verticale winconditie voor speler O
                    if(vakken[0][i].getText().equals(O) && vakken[1][i].getText().equals(O) && vakken[2][i].getText().equals(O)){
                        gewonnen.setText("O wint!");
                        over = true;
                    } //Diagonale winconditie voor speler O
                    if(vakken[2][0].getText().equals(O) && vakken[1][1].getText().equals(O) && vakken[0][2].getText().equals(O) ||
                       vakken[0][0].getText().equals(O) && vakken[1][1].getText().equals(O) && vakken[2][2].getText().equals(O)){
                        gewonnen.setText("O wint!");
                        over = true;
                    }
                    
                    //Gelijk spel conditie
                    if(aantal_zetten >= 9){
                        gewonnen.setText("Gelijkspel");
                        over = true;
                    }//Schakelt alle 
                    if(over == true){
                        vakken[i][j].setEnabled(false);
                    }
                }
            }
        }
    }
    
    class ResetHandeler implements ActionListener{        
        public void actionPerformed(ActionEvent e){
            for (int i =0; i<3; i++){
                for (int j=0; j<3;j++){
                    vakken[i][j].setEnabled(true);
                    vakken[i][j].setText("");
                    gewonnen.setText("");
                    aantal_zetten = 0;
                    aantal_geklikt = 0;
                    aantal_zetten = 0;
                    over = false;
                }
            }       
        }
    }
    class MenuHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            BKE menu = new BKE();
            menu.naarMenu();
        }
    }
}
