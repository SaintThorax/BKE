/*
 * 
 * 
 * 
 */
package bke;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class BKE extends JFrame {

    public static Font spelFont = new Font("Berlin Sans FB", Font.PLAIN, 100);
    public static Font scoreLetterFont = new Font("Helvetica", Font.BOLD, 130);
    public static Font scoreNummerFont = new Font("Century Gothic", Font.PLAIN, 70);
    public static Font titelFont = new Font("Arial", Font.PLAIN, 60);
    public static Font algemeenFont = new Font("Century Gothic", Font.BOLD, 20);
    
    private JPanel scoreX, scoreO;
    public static JLabel scoreLetterX, scoreNummerX, scoreLetterO, scoreNummerO, Titel;
    public static JButton reset, menuKnop, tegenSpeler, tegenComputer;
    static JFrame frame;
      
    public static void main(String[] args) {        
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new BKE().createMenuGUI();
            }
        });
    }
    public void createMenuGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
        Titel = new JLabel("Boter-kaas-en-eieren", SwingConstants.CENTER);
        Titel.setFont(titelFont);
        Titel.setPreferredSize(new Dimension(600,100));
        
        JPanel Menu = new JPanel();
        JPanel keuzes = new JPanel();
        keuzes.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
        keuzes.setLayout(new GridLayout(2,1,10,50));
      
        tegenSpeler = new JButton("Speler - Speler");
        tegenSpeler.setFont(algemeenFont);
        tegenSpeler.setFocusPainted(false);
        tegenSpeler.setBackground(Color.decode("#ECF0F1"));
        tegenSpeler.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        tegenSpeler.setPreferredSize(new Dimension(500,100));
        tegenSpeler.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                createSpelGUI();
            }
        });
        tegenComputer = new JButton("Speler - Computer");
        tegenComputer.setFont(algemeenFont);
        tegenComputer.setFocusPainted(false);
        tegenComputer.setBackground(Color.decode("#ECF0F1"));
        tegenComputer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        tegenComputer.setPreferredSize(new Dimension(500,100));
        
        keuzes.add(tegenSpeler);
        keuzes.add(tegenComputer);
        Menu.add(Titel);
        Menu.add(keuzes);    
        
        setContentPane(Menu);
        setSize(800,800);
        setTitle("Quick and Dirty");
        setVisible(true); 
    }
        
    public void createSpelGUI(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        scoreCreateX();
        scoreCreateO();
        
        JPanel score = new JPanel();

        score.setPreferredSize(new Dimension(10,100));
        score.setLayout(new GridLayout(1,2,5,5));
        score.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
       
        score.add(scoreX);
        score.add(scoreO);
        
        JPanel UI = new JPanel();
        UI.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        UI.setLayout(new BorderLayout());
        
        reset = new JButton("Nieuw spel");
        reset.setBackground(Color.decode("#ECF0F1"));       
        reset.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        reset.addActionListener(new ResetHandeler());
        
        menuKnop = new JButton("Menu");
        menuKnop.setFocusPainted(false);
        menuKnop.setBackground(Color.decode("#ECF0F1"));
        menuKnop.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        menuKnop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                createMenuGUI();
            }
        });
        menuKnop.addActionListener(new ResetHandeler());
             
        UI.add(menuKnop);
        UI.add(reset);
        
        for (Component c : UI.getComponents()){
            c.setFont(algemeenFont);
        }
        UI.setPreferredSize(new Dimension(15, 100));
        UI.setLayout(new GridLayout(1,2,10,0));

        JPanel spelPaneel = new JPanel();
        spelPaneel.setLayout(new BorderLayout(60,40));
        
        JPanel spelVakkenPaneel = new JPanel();
        spelVakkenPaneel.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        Spel spelVakken = new Spel();

        spelVakkenPaneel.setLayout(new GridLayout(1,1,50,5));
        spelVakkenPaneel.setPreferredSize(new Dimension(100, 100));
       
        spelVakkenPaneel.add(spelVakken);
        
        spelPaneel.add(score, BorderLayout.PAGE_START);
        spelPaneel.add(UI, BorderLayout.PAGE_END);
        spelPaneel.add(spelVakkenPaneel);
        
        for (Component c : spelPaneel.getComponents()){
            c.setBackground(Color.decode("#ECF0F1"));
        }
        spelPaneel.setBackground(Color.decode("#ECF0F1"));
        
        setContentPane(spelPaneel);
        setSize(800,800);
        setTitle("Quick and Dirty");
        setVisible(true);      
    }
    
    public void scoreCreateX(){
        scoreX = new JPanel();
        
        scoreLetterX = new JLabel(Spel.spelerX, SwingConstants.LEFT);
        scoreNummerX = new JLabel("-  ", SwingConstants.RIGHT);
        
        scoreLetterX.setFont(scoreLetterFont);
        scoreNummerX.setFont(scoreNummerFont);
        
        scoreLetterX.setForeground(Color.decode("#2C3E50"));
        scoreNummerX.setForeground(Color.decode("#2d2d2d"));
        
        scoreX.setLayout(new GridLayout(1,2));
        scoreX.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        scoreX.add(scoreLetterX);
        scoreX.add(scoreNummerX);
    }
    
    public void scoreCreateO(){
        scoreO = new JPanel();
        
        scoreLetterO = new JLabel(Spel.spelerO, SwingConstants.LEFT);
        scoreNummerO = new JLabel("- ", SwingConstants.RIGHT);
        
        scoreLetterO.setFont(scoreLetterFont);
        scoreNummerO.setFont(scoreNummerFont);
        
        scoreLetterO.setForeground(Color.decode("#E74C3C"));
        scoreNummerO.setForeground(Color.decode("#2d2d2d"));        
        
        scoreO.setLayout(new GridLayout(1,2));
        scoreO.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        scoreO.add(scoreLetterO);
        scoreO.add(scoreNummerO);
    }

    class ResetHandeler implements ActionListener{        
    public void actionPerformed(ActionEvent e){
        for (int i =0; i<3; i++){
            for (int j=0; j<3;j++){
                Spel.vakken[i][j].setEnabled(true);
                Spel.vakken[i][j].setBackground(Color.decode("#ECF0F1"));
                //UIManager.put("Button.disabledText", Color.decode("#263248"));
                Spel.vakken[i][j].setText("");
                Spel.aantalZetten = 0;
                Spel.over = false;
                Spel.xWin = false;
                Spel.oWin = false;
                }
            }       
        }
    }   
}

