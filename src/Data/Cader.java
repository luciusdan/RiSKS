/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import InOut.FileHandler;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class Cader {
    HashMap<String,Player> player;

    public Cader() {
        this.player = new HashMap<>();
    }
    
    public HashMap<String, Player> getCader() {
        return player;
    }
    
}
