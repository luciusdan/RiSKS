/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InOut;

import Data.Cader;
import Data.Player;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dirk
 */
public class CaderFileHandler {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH;mm");
    Cader cader;
    String trenner;
    String caderFileName = "cader.cdr";
    int fails;
    
    public CaderFileHandler(Cader cader, String trenner){
        this.cader = cader;
        this.trenner = trenner;
    }

    private Player readPlayer(String line, int i){
        String[] lineFields = line.split(trenner);
        Player player= null;
        if(lineFields.length!=5){
            fails++;
            System.out.println("Fehler "+fails+": Falsche Informationsmenge! Zeile"+i);
        }else{
            try {
                player = new Player(lineFields[0],dateFormat.parse(lineFields[4]));
                player.setClass(lineFields[1]);
                player.setMainSpec(lineFields[2]);
                player.setSecondSpec(lineFields[3]);
            } catch (ParseException ex) {
                Logger.getLogger(CaderFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return player;
    }
    
    public void readCader(){
        try {
            FileReader fr = new FileReader(caderFileName);
            BufferedReader br = new BufferedReader(fr);
            try {
                System.out.println("Lese Kader:"+caderFileName);
                String line;
                fails=0;
                for(int i = 1; i>0 ; i++){
                    line = br.readLine();
                    if(line == null){
                        System.out.println("Kader gelesen: "+(i-1)+" Zeilen "+fails+" Fehler");
                        i = -1;
                   }else{
                        Player newP = readPlayer(line,i);
                        cader.getCader().put(newP.getName(),newP);
                   }
                }
            } catch (IOException ex) {
                System.out.println("FEHLER: CdrFile lässt sich nicht öffnen!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FEHLER: CdrFile kann nicht gefunden werden!");
        }
    }
    
    public void writeCader(){
        //TODO 
    }
}
