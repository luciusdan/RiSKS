/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Comparators;

import Data.Player;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author Dirk
 */
public class PlayerComparator implements Comparator<String> { 
    HashMap<String,Player> members;
    public PlayerComparator(HashMap<String,Player> members){
        super();
        this.members = members;
    }
    @Override
    public int compare(String name1, String name2) {
        Player o1 = members.get(name1);
        Player o2 = members.get(name2);
        String guildO1 = "";
        String guildO2 = "";
        if(o1.getGuild()!=null){
            guildO1 = o1.getGuild().getName();
        }
        if(o2.getGuild()!=null){
            guildO2 =o2.getGuild().getName();
        }

        int comp1 = guildO1.compareTo(guildO2);
        if(comp1==0){
            String nameO1 = o1.getName();
            String nameO2 = o2.getName();
            return nameO1.compareTo(nameO2);
        }else{
            return comp1;
        }
    }
}
