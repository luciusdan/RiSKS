/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InOut;

import Data.Player;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Dirk
 */
public class PlayerReader {
    String caderFileName = "raidieschen.cdr";
    String trenner = "@@";
    
    public HashMap<String,Player> read(){
        HashMap<String,Player> players= new HashMap<String,Player>();
        try {
            FileReader fr = new FileReader(caderFileName);
            BufferedReader br = new BufferedReader(fr);
            try {
                System.out.println("Lese Config");
                String line;
                int fails=0;
                for(int i = 1; i>0 ; i++){
                    line = br.readLine();
                    if(line == null){
                        System.out.println("Config gelesen: "+(i-1)+" Zeilen "+fails+" Fehler");
                        i = -1;
                   }else{
                        String[] lineFields = line.split(trenner);
                        if(lineFields.length!=3){
                            fails++;
                            System.out.println("Fehler "+fails+": Falsche Informationsmenge! Zeile"+i);
                        }else{
                            Player newP = new Player(lineFields[0]);
                            newP.setClass(lineFields[1]);
                            newP.setMainSpec(lineFields[2]);
                            players.put(lineFields[0],newP);
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("CdrFile lässt sich nicht öffnen!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("CdrFile kann nicht gefunden werden!");
        }
        return players;
    }
}
