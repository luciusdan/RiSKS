/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InOut;

import Data.Cader;
import Data.Raid;
import java.util.Date;

/**
 *
 * @author Dirk
 */
public class FileHandler {
    private CaderFileHandler cfh;
    private RaidFileHandler  rfh;
    
    
    
    public FileHandler(Cader cader) {
        String trenner = "@@";
        String capTrenner = "@//@";
        cfh = new CaderFileHandler(cader , trenner);
        rfh = new RaidFileHandler (cader, trenner, capTrenner);
    }
    
    public void readCader(){
        cfh.readCader();
    }
    
    public void removeRaid(Date timestamp){
        rfh.removeRaid(timestamp);
    }
    
    public Raid readRaid(Date timestamp){
        return rfh.readRaid(timestamp);
    }
    
    public void writeRaid(Raid raid){
        rfh.writeRaid(raid);
    }
    
    public Date getLastRaidDate(){
        return rfh.getLastRaidDate();
    }
    
     public Date[] getValidDateRange(Raid raid){
         return rfh.getValidDateRange(raid);
     }
}
