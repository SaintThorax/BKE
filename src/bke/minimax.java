/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bke;

import static bke.Spel.spelerAanZet;
import static bke.Spel.spelerX;
import static bke.Spel.vakken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;


class Zet{
    int kol, rij;
    public Zet(int kol, int rij){
        this.kol = kol;
        this.rij = rij;
    }
}

/**
 *
 * @author Thom
 */
public class minimax extends Spel {
    
    public ArrayList<Integer> scores, moves; 
    public List<Zet> zetten, zetMinimax;
    public Zet gemaakteZet;
    
    public int score(){
        if(checkSpelGewonnen() && spelerAanZet.equals(spelerX)){
            return -10;
        } else if (checkSpelGewonnen() && spelerAanZet.equals(spelerO)){
            return 10;
        } else {
            return 0;
        }
    }
    
    public /*ArrayList<Integer> */ int minimax(JButton spel){
        if (checkSpelGewonnen() == true){
            return score();
        }
        zetMinimax = new ArrayList<>();
        scores = new ArrayList<>();
        moves = new ArrayList<>();
        
        mogelijkeZetten();
        for (Zet zet : zetten){
            JButton kanZetten = vakken[zet.kol][zet.rij];
            scores.add(minimax(kanZetten));
            zetMinimax.add(zet);
        }
        
        if(spelerAanZet.equals(spelerX)){
            int max_score_index = Collections.max(scores);
            gemaakteZet = zetMinimax.get(max_score_index);
            return scores.get(max_score_index);
        } else {
            int min_score_index = Collections.min(scores);
            gemaakteZet = zetMinimax.get(min_score_index);
            return scores.get(min_score_index);
        }
    }
    
    public List<Zet> mogelijkeZetten(){
        zetten = new ArrayList<>();
        for (int i = 0; i<3; i++){
            for(int j= 0; j<3; j++){
                if (vakken[i][j].getText().equals(" ")){
                    zetten.add(new Zet(i,j));
                }
            }
        }
        System.out.println("Zetten: " + zetten);
        return zetten;
    }
    
}
