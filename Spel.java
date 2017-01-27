package bke;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

/**
 * Class spel wordt gebruikt om het spel van een speler tegen een computer uit te voeren
 * Er wordt gebruik gemaakt van het minimax algoritme om de perfecte zet voor de computer te vinden
 * Momenteel is het alleen mogelijk om zelf X te spelen, en de computer O te laten spelen. Dit zal, indien voldoende tijd beschikbaar is, ook andersom werken.
 * @author Thom
 */

/*
 De zet class kan als een lijst worden gebruikt om zetten op te slaan.
 Sinds ik een JButton[3(rij)][3(kolom)] voor mijn vakken gebruik zal dus die 2 waardes als 1 zet kunnen opslaan
 Deze class en de functie wordt gebruikt in mogelijke zetten en minimax
 */
class Zet{
    int rij, kol;
    public Zet(int rij, int kol){
        this.rij = rij;
        this.kol = kol;
    }
}
/*
Deze class werkt samen met de zet class om in de minimax functie de zetten een score te geven,
zo kan uit een lijst van zetten met daarbijhorende scores de beste of slechte worden gevonden
*/
class ZetEnScores{
    int score;
    Zet zet;
    
    ZetEnScores(int score, Zet zet){
        this.score = score;
        this.zet = zet;
        
    }
}

public class Spel extends JPanel{
    
    final static String spelerX = "X", spelerO = "O";
    public String tegenstander;
    
    public static JButton geklikt;
    public static String spelerAanZet;
    public static boolean over = false, xWin = false, oWin = false, spelerIsMens = true;
    public static boolean hasXWon, hasOWon;
    public static int scoreIntX, scoreIntO, waarde, xMMS, oMMS;
    public static int boven, links, onder, rechts, zettenVoorbijO = 0;
    public static JButton[][] vakken;
    public static int aantalZetten = 0, tel = 0;
    
    Random rand = new Random();
    public ArrayList<Integer> moves; 
    public List<Zet> zetten, mogelijkeZettenMM, gemaakteZetten;
    public Zet gemaakteZet;    
    
    public Spel(){
       initSpel(); 
    }

    
    public void initSpel(){ //Creating the playing field
        setLayout(new GridLayout(3,3,5,5)); // Zo zien de vakken eruit als een boter kaas en eieren bord.
        vakken = new JButton[3][3];
        int tel = 0;
        for (int i=0; i<3; i++){
            for (int j = 0; j<3;j++){
                vakken[i][j] = new JButton(); //vakken is een knop uit het spel [verticaal / rij][horizontal / kolom]
                vakken[i][j].setFont(BKE.spelFont);
                vakken[i][j].setBackground(Color.decode("#ECF0F1"));
                if (tel > 2)        boven = 3; //wanneer de if statement waar is, krijgt de vak die in de loop wordt gecreërd een border
                if (tel % 3 != 0)   links = 3;
                if (tel < 6)        onder = 3;
                if (tel % 3 != 2)   rechts = 3;
                vakken[i][j].addActionListener(new KnopHandeler()); //zorgt dat de knop geklikt kan worden
                vakken[i][j].setBorder(BorderFactory.createMatteBorder(boven,links,onder,rechts,Color.decode("#2a2a2a"))); //maakt de border voor de vak
                
                boven = 0;
                links = 0;
                onder = 0;
                rechts=0;
                
                add(vakken[i][j]);
                tel++;
            }
        }
    }
    
    /**
     * Heeft momenteel geen nut, nieuwspel zal gebruikt worden wanneer de computer als eerste begint en geen beste zet kan bereken, hij zal een random zet kiezen
     * Zou verandert moeten worden naar 1 van de 4 hoekzetten, aangezien deze de computer een grotere kans geven om te winnen
     */
    public void nieuwSpel(){
        Random rand = new Random();
        if(aantalZetten == 0){
            Zet p = new Zet(rand.nextInt(3), rand.nextInt(3));
            maakEenZet(p, spelerO);
        }
    }
    
    /**
     * De volgende 4 funties chechrijen checkkolommen, checkdiagonalenlr en checkdiagonalenrl checkt of er drie op een rij zijn
     * indien er een speler wint zal deze als winnaar worden verklaard en een mooie achtergrond krijgen
     */
    
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
    public void checkKolommen(){ // Checkt de kolommen
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
    public void checkDiagonalenLR(){ //checkt links naar rechts de diagonalen
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
    public void checkDiagonalenRL(){ //checkt rechts naar links de diagonalen
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

    public void checkSpelAfgelopen(){ //checks of het spel is afgelopen door de check functies uit te voeren en voor de winnende speler zijn winfunctie uit te laten voeren
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
        else if(aantalZetten >= 9 && over == false){ //In dit geval is er spreke van gelijkspel. Er kunnen nooit meer dan 9 zetten zijn.
            for(int i = 0;i<3;i++){
                for (int j=0;j<3;j++){     
                    vakken[i][j].setBackground(Color.decode("#b2b2b2"));
                }
            spelOver();
            }
        }
    }   
    public void spelOver(){ //Wordt uitgevoerd wanneer het spel over is, zorgt dat er niet meer op vakken geklikt kan worden dezze ronde
        for(int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                vakken[i][j].setEnabled(false); // Schakelt alle vakken uit.
                zettenVoorbijO = 0;
            }
        }
        UIManager.put("Button.disabledText", Color.decode("#FFFFFF")); //Zet de tekstkleur van de winnende vakken naar wit
    }
    
    
    public void spelerAanZet(){ //Kijkt of het aantal zetten even of oneven is, even = X, oneven = 0
        if (aantalZetten % 2 != 0) { //aantalZetten is the amount of turns passed
            tegenstander = spelerO;
            spelerAanZet = spelerX;
        }
        if (aantalZetten % 2 == 0) {
            tegenstander = spelerX;
            spelerAanZet = spelerO;
          
             /*
              Roept de besteZet functie, zie de functie zelf om uitleg te zien. 
              Met de beste zet plaatst hij een zet als spelerO, daarna wordt spelerIsMens (boolean die bij minimax bepaald welke loop moet worden uitgevoerd
            */
            Zet best = besteZet();
            System.out.println("best: [" + best.rij + "," + best.kol + "]");
            maakEenZet(besteZet(), spelerO);
            
            spelerIsMens = true;
            
        } 
    }
    
    public void spel(){ //Voert uit na een knop geklikt is
        aantalZetten++; //Het aantal gemaakte zetten
       
        spelerAanZet(); 
        geklikt.setText(spelerAanZet); // Zet de text van de knop naar die van de waarde van speler aan zet, 
        geklikt.setEnabled(false); //disables the pressed button      
        spelerIsMens = false;  
        
        checkSpelAfgelopen();     //checks if the game has finished 
        
        //Wordt gebruikt om te scores van zetten te vinden, die worden uitgeprint om in de output duidelijk te maken wat de beste score was voor de speler. 
        //Niet nodig voor het spel zelf
        callMinimax(0,false);
        for (ZetEnScores zes : kindZettenScores) {
                System.out.println("Zet: [" + zes.zet.rij + "," + zes.zet.kol+ "]" + " Score: " + zes.score);
        }
        
        aantalZetten++;
        spelerAanZet();
        //Zet de knoppen waar een speler zijn zet heeft gemaakt naar disabled.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if ((vakken[i][j].getText().length()>0)){
                   vakken[i][j].setEnabled(false);
               }               
            }         
        }
        checkSpelAfgelopen();
    }
    
    
    /*
    Wordt aangeroept samen met als Zet zet = besteZet() en spelerAanZet is O
    plaats in de beste zet 
    */
    public void maakEenZet(Zet zet, String spelerAanZet) {
        UIManager.put("Button.disabledText", Color.decode("#2b2b2b"));
        vakken[zet.rij][zet.kol].setText(spelerAanZet);        
    }
        
    /*
    Voert uit wanneer er geklikt is, voert daarna de spel functie uit
    */
    class KnopHandeler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            geklikt = (JButton)e.getSource(); //de geklikte knop kan zo worden aangeroepen
            spel();
        }
    }
    
    public void xWin(){ //Voert uit wanneer X heeft gewonnen
        scoreIntX = scoreIntX + 1;
        String scoreStrX = String.valueOf(scoreIntX + " ");
        BKE.scoreNummerX.setText(scoreStrX);
    }
    public void oWin(){ //Voert uit wanneer O heeft gewonnen
        if(oWin == true){
            scoreIntO = scoreIntO + 1;
            String scoreStrO = String.valueOf(scoreIntO + " ");
            BKE.scoreNummerO.setText(scoreStrO);
        }
    } 
    
    /*
    Maakt een lijst van alle vakken waar geen tekst op staat
    */  
    public List<Zet> mogelijkeZetten(){
        zetten = new ArrayList<>();
        for (int i = 0; i<3; i++){
            for(int j= 0; j<3; j++){
                if (vakken[i][j].getText().equals("")){
                    zetten.add(new Zet(i,j));
                }
            }
        }
        return zetten;
    }
    
    //Vindt de laagste score van de lijst die uit minimax geroepen wordt
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }
    
    //Vindt de hoogste waarde van de lijst die uit minimax geroepen wordt.
    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        //System.out.println(list.get(index));
        return list.get(index);
    }
    
    //Besluit de besteZet, wanneer 1 score uit de lijst groter is dan de vorige, wordt dat de nieuwe hoogste. Dit gebeurt voor elke waarde in een lijst en je eindigt met de beste zet
    public Zet besteZet() {
        int MAX = -100000;
        int best = -1;
        
        for (int i = 0; i < kindZettenScores.size(); ++i) { 
            if (MAX < kindZettenScores.get(i).score) {
                MAX = kindZettenScores.get(i).score;
                best = i;
            } 
        }   
        
        //System.out.println(kindZettenScores.get(worst).zet.rij + " " + kindZettenScores.get(worst).zet.kol);
        return kindZettenScores.get(best).zet;
    }
    
    List<ZetEnScores> kindZettenScores; 
    
    //roept minimax aan, helpt in de spel functie om de waarden uit te printen en maakt de nodige waarden bruikbaar
    public void callMinimax(int diepte, boolean spelerIsMens){
        kindZettenScores = new ArrayList<>();
        minimax(diepte, spelerIsMens);
    }
    
    
    /*
    De methode waar het allemaal om draait. 
    
    */
    public int minimax(int diepte, boolean spelerIsMens){
        List<Zet> zettenMinimax = mogelijkeZetten(); // maakt een lijst van alle mogelijke zetten (vakken zonder tekst)
       
        if (hasXWon()){ return diepte-10; } //Indien X in een zet winnaar is, zal de score van in de lijst naar diepte-10 gezet (negatieve score
        if (hasOWon()){ return 10-diepte; } // Indien O de winaar is, krijg je 
        
        if (zettenMinimax.isEmpty()){ return 0;} //Wanneer er geen zetten meer mogelijk zijn en er geen winnaar is, is de score 0. 
                
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < zettenMinimax.size(); i++) {
            
            Zet zet = zettenMinimax.get(i);
            if (spelerIsMens == true){ //Spreekt voor zich
                maakEenZet(zet, spelerX); // Plaats een X op de zet waar het deze loop omgaat
                scores.add(minimax(diepte+1, false)); //voert de functie opnieuw uit, dit keer als speler O
                
            } else if (spelerIsMens == false){
                maakEenZet(zet, spelerO); //Plaatst een O op de zet waar het deze loop omgaat
                int huidigeScore = minimax(diepte+1, true); //Maakt een integer van de score, zodat deze in de if statement hieronder gebruikt kan worden
                scores.add(huidigeScore); //voert de functie opnieuw uit, dit keer als X.
                if (diepte == 0){
                    /*
                    //Wanneer we aankomen bij de 'bron' zetten, kunnen we scores geven aan elke zet beschikbaar op het huidige bord. Deze scores en zetten roepen we in het spel op via de 
                    beste zet functie. Daar wordt de beste waarde met de daarbijbehorende zet gelezen en de hoogste teruggestuurd. De teruggestuurde zet is de zet die O maakt.
                }
                    */
                    kindZettenScores.add(new ZetEnScores(huidigeScore, zet));
                }
            }
            vakken[zet.rij][zet.kol].setText(""); //Haalt het bordt leeg van alle geplaatste zetten. 
        } 
        /*
        /Als speler X is, wordt de laagste score uit een lijst van een van de mogelijke manieren waarop het spel verloopt teruggestuurd, zo kan O daaruit de maximale halen en de zet maken.
        /Als O aanzet is wordt de hoogste score teruggegeven, zo wordt de winnende zet gevonden.
        */
        return ((spelerIsMens) ? returnMin(scores) : returnMax(scores));  
    }
    
    
    /*
    De laatste 2 functies hasXWon en hasOWon Checken de rijen, kolommen en diagonalen om te kijken of X of O zou winnen als het bord er zo uit zou zien.
    Doet eigenlijk hetzlefde als de eerdere controle functies, alleen die worden gebruikt om te kleuren te veranderen, en aan te geven dat het spel echt is afgelopen
    Bij minimax gebruiken we deze alleen om de score te berekenen.
    */
    public boolean hasXWon(){ 
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerX) && vakken[j][1].getText().equals(spelerX) && vakken[j][2].getText().equals(spelerX) ||
                  (vakken[0][i].getText().equals(spelerX) && vakken[1][i].getText().equals(spelerX) && vakken[2][i].getText().equals(spelerX))){
                    return true;
               } if ((vakken[0][0].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][2].getText().equals(spelerX) ||
                       (vakken[0][2].getText().equals(spelerX) && vakken[1][1].getText().equals(spelerX) && vakken[2][0].getText().equals(spelerX)))){
                   return true;
               }
           }
        }
        return false;
    }

    public boolean hasOWon(){ //Checks the rows
        for (int i = 0; i < 3; i++){
           for (int j=0;j<3;j++){     
                //vakken[verticaal][horizontaal]
                if(vakken[j][0].getText().equals(spelerO) && vakken[j][1].getText().equals(spelerO) && vakken[j][2].getText().equals(spelerO) ||
                  (vakken[0][i].getText().equals(spelerO) && vakken[1][i].getText().equals(spelerO) && vakken[2][i].getText().equals(spelerO))){
                    return true;
               } if ((vakken[0][0].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][2].getText().equals(spelerO) ||
                       (vakken[0][2].getText().equals(spelerO) && vakken[1][1].getText().equals(spelerO) && vakken[2][0].getText().equals(spelerO)))){
                   return true;
               }
           }
        }
        return false;
    }
} 