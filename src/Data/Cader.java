/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import InOut.PlayerReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class Cader {
    HashMap<String,Player> player;

    LinkedList<Player> sks;

    public Cader() {
        PlayerReader reader= new PlayerReader();
        player = reader.read();
    }
    
    /**
     *
     * @param changed
     * @return
     */
    public LinkedList<Player> newSKS(LinkedList<Player> changed){
        LinkedList<Player> copy = (LinkedList<Player>)changed.clone();
        LinkedList<Player> sksCopy = (LinkedList<Player>)sks.clone();
        
        for(int i =0;i<sksCopy.size();i++){
            if(changed.contains(sksCopy.get(i))){
                if(!(copy.getFirst().equals(sksCopy.get(i)))){
                    sksCopy.set(i, copy.getFirst());
                }
                copy.removeFirst();
            }
        }
        return sksCopy;
    }
    
    
    
    
    
    
}
