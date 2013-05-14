/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InOut;

import Data.Cader;
import Data.Loot;
import Data.Raid;
import Data.Member;
import Data.Member.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class RaidFileHandler {
    SimpleDateFormat filedateFormat = new SimpleDateFormat("dd.MM.yyyy HH mm");
    SimpleDateFormat lootDateFormat = new SimpleDateFormat("HH:mm:ss");
    Cader cader;
    String trenner = "@@";
    String capTrenner = "@//@";
    
    
    public RaidFileHandler(Cader cader,String trenner, String capTrenner){
        this.cader = cader;
        this.trenner = trenner;
        this.capTrenner = capTrenner;
    }
    
    public Date getLastRaidDate(){
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return null;
        }
        ArrayList<File> childs = new ArrayList(Arrays.asList(file.listFiles()));
        getNewest(childs);
        try {
            return filedateFormat.parse(childs.get(0).getName());
        } catch (ParseException ex) {
            System.out.println("FEHLER: konnte kein letzer Raid Datum gefunden werden!");
            return null;
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
    
    private static Date searchNearestLower(Date date ,LinkedList<Date> dates){
        //-- abbruch Bedingung ---------------------------------------------
        if(dates.size()==1){
            if(date.after(dates.getFirst())){
                return dates.getFirst();
            }else{
                return null;
            }
        }
        if(dates.size()==2){
            if(date.after(dates.getLast())){
                return dates.getLast();
            }
            return searchNearestLower(date,new LinkedList(dates.subList(0, 1)));
        }
        //-- rekursion -----------------------------------------------------

        int middle = dates.size()/2;
        Date comp = dates.get(middle);
        if(comp.after(date)){
            return searchNearestLower(date,new LinkedList(dates.subList(0, middle)));
        }else{
            return searchNearestLower(date,new LinkedList(dates.subList(middle, dates.size()-1)));

        }
    }
    private void getNewest(ArrayList<File> files){
        if(files.size()>1){
            Date firstFileDate= null;
            Date secFileDate= null;
            try {
                String fileName = files.get(0).getName();
                fileName.replaceAll(".raid", "");
                firstFileDate = filedateFormat.parse(fileName);
            } catch (ParseException ex) {
                
            }
            try {
                String fileName = files.get(1).getName();
                fileName.replaceAll(".raid", "");
                secFileDate = filedateFormat.parse(fileName);
            } catch (ParseException ex) {
                files.remove(1);
            }
            if(firstFileDate!=null&&secFileDate!=null){
                if(firstFileDate.before(secFileDate)){
                    files.remove(0);
                }else{
                    files.remove(1);
                }

            }
            getNewest(files);
        }
    }
    
    public Raid readRaid(Date timestamp){
        //-- pruefe ob Raid-Ordner existiert -----------------------------------
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return null;
        }
        //-- pruefe ob Raid-File existiert -------------------------------------
        String filePath=(".\\RAIDS\\"+filedateFormat.format(timestamp)+".raid");
        file = new File(filePath);
        if(!(file.exists()&&file.canRead())){
            System.out.println("FEHLER: Raid nicht vorhanden!"+filePath);
            return null;
        }
        //-- beginne lesen -----------------------------------------------------
        Raid newRaid = new Raid(timestamp,cader, false);
        try{
            String line;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try{
                System.out.println("Lese Raid:"+filedateFormat.format(timestamp)+".raid ------------");
                //--lese comment -----------------------------------------------
                String comment = "";
                line = br.readLine();
                while(!line.equals(capTrenner)){
                    comment += line+"\n";
                    line = br.readLine();
                }
                newRaid.setComment(comment);
                //-- lese Raidteilnehmer ---------------------------------------
                line = br.readLine();
                while(!line.equals(capTrenner)){
                    readMember(line, newRaid);
                    line = br.readLine();
                }
                //-- lese LootListe ----------------------------------------
                line = br.readLine();
                while(!line.equals(capTrenner)){
                    readLoot(line,newRaid);
                    line = br.readLine();
                }         
            } catch (IOException ex) {
                System.out.println("FEHLER: Raid-Datei \""+filedateFormat.format(timestamp)+".raid\" lässt sich nicht öffnen!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FEHLER: Raid-Datei \""+filedateFormat.format(timestamp)+".raid\" kann nicht geladen werden");
        }
        System.out.println("");
        return newRaid;
        
    }
    
    public void removeRaid(Date timestamp){
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
        }else{
            //-- pruefe ob Raid-File existiert -------------------------------------
            String filePath=(".\\RAIDS\\"+filedateFormat.format(timestamp)+".raid");
            file = new File(filePath);
            if(!(file.exists()&&file.canRead())){
                System.out.println("FEHLER: Raid nicht vorhanden!"+filePath);
            }else{
                file.delete();
            }
        }
    }
    
    public void writeRaid(Raid raid){
        //-- pruefe ob Raid-Ordner existiert -----------------------------------
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            if(!file.mkdir()){
                System.out.println("FEHLER: Raid-Ordner kann nicht erstellt werden!");
            }
        }
        //-- pruefe ob Raid-File existiert -------------------------------------
        String filePath=(".\\RAIDS\\"+filedateFormat.format(raid.getTimestamp())+".raid");
        file = new File(filePath);
        if((file.exists())){
            file.renameTo(file);
        }
        try {
            try (FileWriter writer = new FileWriter(file ,false)) {
                writer.write(raid.getComment());
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                //schreibe trenner in File -------------------------------------
                writer.write(capTrenner);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                //schreibe Teilnehmer in File ----------------------------------
                String[] names = raid.getMembersNames();
                for(int i=0;i<names.length;i++){
                    writer.write(memberToString(raid.getMemberByName(names[i])));
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
                //schreibe trenner in File -------------------------------------
                writer.write(capTrenner);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                //schreibe Loot in File ----------------------------------------
                Date[] items = raid.getlootDates();
                for(int i=0;i<items.length;i++){
                    writer.write(lootToString(raid.getLoot(items[i])));
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
                //schreibe trenner in File -------------------------------------
                writer.write(capTrenner);
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                
                writer.close();
            }
        } catch (IOException ex) {
            System.out.println("FEHLER: Kann Raid nicht schreiben!"+filedateFormat.format(raid.getTimestamp()));
        } 
    }

    
    private void readMember(String line, Raid newRaid){
        String[] parts = line.split(trenner);
        if(parts.length!=3){
            System.out.println("FEHLER: Falsche Anzahl der Player-Attribute!:"+line);
        }else{
            //Spieler Name
            if(!cader.getCader().containsKey(parts[0])){
                System.out.println("FEHLER: Spieler ist nicht im Kader!:"+line);
            }else{
                //nehme Player aus Kader als Grundlage
                Member plyr = newRaid.getMemberByName(parts[0]);
                //Spieler Status Teilgenommen/Ersatzbank
                switch(parts[1].toLowerCase()){
                    case "teilgenommen" :
                            plyr.setState(Member.State.TEILG);
                        break;
                    case "ersatz" :
                            plyr.setState(Member.State.ERSATZ);
                        break;
                    case "abwesend" :
                            plyr.setState(Member.State.ABWESEND);
                        break;
                    default  : 
                        System.out.println("FEHLER: Spieler ("+parts[0]+")hat ungültigen Raidstatus!:"+parts[1]);
                }
                //setze Sks-Position
                plyr.setSksPos(Integer.parseInt(parts[2]));
            }
        }
    }   
    
    private String memberToString(Member member){
        String line = member.getPlayer().getName();
        line+=trenner+member.getStateString();
        line+=trenner+member.getSksPos();
        return line;
    }
    
    private void readLoot(String line, Raid newRaid){
        String[] parts = line.split(trenner);
        if(parts.length!=3){
            System.out.println("FEHLER: Falsche Anzahl der Loot-Attribute!:"+line);
        }else{
            //Loot PlayerName
            Member member = newRaid.getMemberByName(parts[0]);
            if(member==null||!member.getState().equals(State.TEILG)){
                System.out.println("FEHLER: Spieler ist nicht Lootfähig!:"+line);
            }else{
                try {
                    Loot newLoot =  new Loot(lootDateFormat.parse(parts[1]),member,parts[2]);
                    newRaid.addLoot(newLoot);
                } catch (ParseException ex) {
                    System.out.println("FEHLER: Datum des Loots ungültig!:"+line);
                }
            }
        }
    }
    
    private String lootToString(Loot loot){
        String line = loot.getMember().getPlayer().getName();
        line+=trenner+lootDateFormat.format(loot.getTimestamp());
        line+=trenner+loot.getName();
        return line;
    }
    
private LinkedList<Date> getRaidDates(){
        LinkedList<Date> dates = new LinkedList<>();
        
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return dates;
        }
        ArrayList<File> files = new ArrayList(Arrays.asList(file.listFiles()));
        for(int i=0;i<files.size();i++){
            File current = files.get(i);
            String fileName = current.getName();
            if(current.exists()&&fileName.endsWith(".raid")){}
            try {
                String fileDate = fileName.replaceAll(".raid", "");
                dates.add(filedateFormat.parse(fileDate));
            } catch (ParseException ex) {
                System.out.println("FEHLER: kann "+fileName+" nicht entziffern!");
            }
        }
        
        Comparator<Date> comparator = new DateComparator();
        java.util.Collections.sort( dates, comparator );
        return dates;
    }

    
    private class DateComparator implements Comparator<Date>{
        @Override
        public int compare( Date a, Date b ) {
            String valA = filedateFormat.format(a);
            String valB = filedateFormat.format(b);
            return valA.compareTo(valB);
        }
    }
}
