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
    private JButton reset, menuKnop, tegenSpeler;
    public static JButton[][] vakken;
    static JFrame frame;
      
    public static void main(String[] args) {        
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
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
        menuKnop.setBackground(Color.decode("#ECF0F1"));
        menuKnop.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        menuKnop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                createMenuGUI();
            }
        });
             
        UI.add(menuKnop);
        UI.add(reset);
        
        for (Component c : UI.getComponents()){
            c.setFont(algemeenFont);
        }
        UI.setPreferredSize(new Dimension(15, 100));
        UI.setLayout(new GridLayout(1,2,10,0));

        JPanel spelBord = new JPanel();
        spelBord.setLayout(new BorderLayout(60,40));
        
        JPanel test = new JPanel();
        test.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        test.setLayout(new GridLayout(10,10,10,10));
        Spel spel = new Spel();
        //spel.setBackground(Color.decode("#d2d2d2"));

        test.setLayout(new GridLayout(1,1,50,5));
        test.setPreferredSize(new Dimension(100, 100));
       
        //spel.setBorder(BorderFactory.createLineBorder(Color.decode("#d2d2d2"), 4));
        test.add(spel);
        
        spelBord.add(score, BorderLayout.PAGE_START);
        spelBord.add(UI, BorderLayout.PAGE_END);
        spelBord.add(test);
        
        for (Component c : spelBord.getComponents()){
            c.setBackground(Color.decode("#ECF0F1"));
        }
        spelBord.setBackground(Color.decode("#ECF0F1"));
        
        setContentPane(spelBord);
        setSize(800,800);
        setTitle("Quick and Dirty");
        setVisible(true);      
    }
    
    public void scoreCreateX(){
        scoreX = new JPanel();
        
        scoreLetterX = new JLabel(Spel.X, SwingConstants.LEFT);
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
        
        scoreLetterO = new JLabel(Spel.O, SwingConstants.LEFT);
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
                vakken[i][j].setEnabled(true);
                vakken[i][j].setBackground(Color.decode("#ECF0F1"));
                //UIManager.put("Button.disabledText", Color.decode("#263248"));
                vakken[i][j].setText("");
                Spel.aantal_geklikt = 0;
                Spel.over = false;
                Spel.xWin = false;
                Spel.oWin = false;
                }
            }       
        }
    }
}

