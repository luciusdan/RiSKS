/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InOut;

import Data.Cader;
import Data.Member;
import Data.Raid;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class FileHandler {
    public static SimpleDateFormat fileDateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
    
    
    private Cader cader;
    HashMap<String,String> trenner;
    RaidFileHandler raidFileHandler;
    
    
    
    public FileHandler(Cader cader) {
        this.cader = cader;
        this.trenner = new HashMap<>();
        this.trenner.put("trenner", "@@");
        this.trenner.put("capTrenner", "@chapter@");
        this.raidFileHandler= new RaidFileHandler(cader,this.trenner, fileDateFormat);
    }
    
    /**
     * Füllt die übergebenen HashMap's aus dem letzten Raid.
     * @param memberByString
     * @param memberByDate
     */
    public Date readCader(HashMap<String, Member> memberByString, HashMap<Date, Member> memberByDate) {
        Date lastDate = getLastRaidDate();
        readRaid(lastDate);
        
        String[] names = cader.getMemberNames();
        System.out.println("CaderSize: "+names.length);
        for(int i =0;i<names.length;i++){
            Member current = cader.getMember(names[i]);
        }
        return lastDate;
    }
      
    /**
     * findet das Datum des Letzten Raid's.
     * @return
     */
    public Date getLastRaidDate(){
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return null;
        }
        LinkedList<File> childs = new LinkedList<>(Arrays.asList(file.listFiles()));
        Comparator<File> comparator = new raidFileComparator();
        java.util.Collections.sort( childs, comparator );
        
        try {
            File first = childs.getFirst();
            File last = childs.getLast();
            return fileDateFormat.parse(childs.getLast().getName().replaceAll(".raid", ""));
        } catch (ParseException ex) {
            System.out.println("FEHLER: konnte kein letzer Raid Datum gefunden werden!");
            return null;
        }
    }
    
    
    //-- weiterleitung ---------------------------------------------------------
    public Raid readRaid(Date timestamp){
        return raidFileHandler.readRaid(timestamp);
    }
    
    public void removeRaid(Date timestamp){
        raidFileHandler.removeRaid(timestamp);
    }
    
    public void writeRaid(Raid raid){
        raidFileHandler.writeRaid(raid);
    }
    
    public LinkedList<Date> getRaidDates(){
        return raidFileHandler.getRaidDates();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private class raidFileComparator implements Comparator<File>{
        @Override
        public int compare( File a, File b ) {
            if(a.getName().contains(".raid")){
                return 1;
            }
            if(b.getName().contains(".raid")){
                return -1;
            }
            String nameA = a.getName().replaceAll(".raid","");  
            String nameB = b.getName().replaceAll(".raid",""); 
            return -nameA.compareTo(nameB);
        }
    }
 
    public Date[] getValidDateRange(Raid raid){
        return (new Date[2]);/*
        Date date = raid.getTimestamp();
        Date[] range = new Date[2];            
        
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return range;
        }
        LinkedList<Date> dates = getRaidDates();
        if(dates.isEmpty()){
            return range;
        }
        if(dates.contains(date)){
            int index = dates.indexOf(date);
            if(index==0){
                range[0] = null;
                range[1] = dates.get(1);
                return range;
            }else if(index==dates.size()-1){
                range[0] = dates.get(dates.size()-2);
                range[1] = null;
                return range;
            }else{
                range[0] = dates.get(index-1);
                range[1] = dates.get(index+1);
                return range;
            }
        }
        Date before = searchNearestLower(date, dates);
        if(before==null){
            range[1] = dates.getFirst();
            return range;
        }
        range[0] = before;
        int indexBefore = dates.indexOf(before);
        if(indexBefore+1==dates.size()){
            return range;
        }
        range[1] = dates.get(indexBefore+1);
        return range;*/
    }
}
