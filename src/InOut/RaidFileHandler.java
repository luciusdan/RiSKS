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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class RaidFileHandler {
    public static SimpleDateFormat lootDateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private Cader cader;
    private HashMap<String,String> trenner;
    private SimpleDateFormat fileDateFormat;
    
    /**
     *
     * @param cader
     * @param trenner
     */
    public RaidFileHandler(Cader cader,HashMap<String,String>trenner, SimpleDateFormat fileDateFormat){
        this.fileDateFormat = fileDateFormat;
        this.cader = cader;
        this.trenner = trenner;
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public Raid readRaid(Date timestamp){
        //-- pruefe ob Raid-Ordner existiert -----------------------------------
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return null;
        }
        //-- beginne lesen -----------------------------------------------------
        Raid newRaid = new Raid(timestamp,cader);
        try{
            file= new File(".\\RAIDS\\"+fileDateFormat.format(timestamp)+".raid");
            String line;
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try{
                System.out.println("Lese Raid:"+fileDateFormat.format(timestamp)+".raid ------------");
                //--lese comment -----------------------------------------------
                String comment = "";
                line = br.readLine();
                
                while(!(line.equals(trenner.get("capTrenner")))){
                    comment += line+"\n";
                    line = br.readLine();
                }
                newRaid.setComment(comment);
                //-- lese Raidteilnehmer ---------------------------------------
                line = br.readLine();
                while(!line.equals(trenner.get("capTrenner"))){
                    readMember(line, newRaid);
                    line = br.readLine();
                }
                //-- lese LootListe ----------------------------------------
                line = br.readLine();
                while(!line.equals(trenner.get("capTrenner"))){
                    readLoot(line,newRaid);
                    line = br.readLine();
                }         
            } catch (IOException ex) {
                System.out.println("FEHLER: Raid-Datei \""+fileDateFormat.format(timestamp)+".raid\" lässt sich nicht öffnen!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FEHLER: Raid-Datei \""+fileDateFormat.format(timestamp)+".raid\" kann nicht geladen werden");
        }
        System.out.println("");
        return newRaid;
        
    }
    
    /**
     *
     * @param timestamp
     */
    public void removeRaid(Date timestamp){
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
        }else{
            //-- pruefe ob Raid-File existiert -------------------------------------
            String filePath=(".\\RAIDS\\"+fileDateFormat.format(timestamp)+".raid");
            file = new File(filePath);
            if(!(file.exists()&&file.canRead())){
                System.out.println("FEHLER: Raid nicht vorhanden!"+filePath);
            }else{
                file.delete();
            }
        }
    }
    
    /**
     *
     * @param raid
     */
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
        String filePath=(".\\RAIDS\\"+fileDateFormat.format(raid.getTimestamp())+".raid");
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
                writer.write(trenner.get("capTrenner"));
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                //schreibe Teilnehmer in File ----------------------------------
                String[] names = raid.getMembersNames();
                Arrays.sort(names);
                for(int i=0;i<names.length;i++){
                    writer.write(writeMember(raid.getMemberByName(names[i])));
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
                //schreibe trenner in File -------------------------------------
                writer.write(trenner.get("capTrenner"));
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                //schreibe Loot in File ----------------------------------------
                Date[] items = raid.getlootDates();
                for(int i=0;i<items.length;i++){
                    writer.write(writeLoot(raid.getLoot(items[i])));
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
                //schreibe trenner in File -------------------------------------
                writer.write(trenner.get("capTrenner"));
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                
                writer.close();
            }
        } catch (IOException ex) {
            System.out.println("FEHLER: Kann Raid nicht schreiben!"+fileDateFormat.format(raid.getTimestamp()));
        } 
    }

    
    private void readMember(String line, Raid newRaid){
        String[] parts = line.split(trenner.get("trenner"));
        //[Name|Role|MainSpec|SecSpec|joiningDate|RaidCount|State|SKSPos]
        if(parts.length!=8){
            System.out.println("FEHLER: Falsche Anzahl der Player-Attribute("+parts.length+")!:"+line);
        }else{
            //schaue, ob bereits Spieler Existiert -----------------------------
            Member member = cader.getMember(parts[0]);
            if(member== null){
                try {
                    member = new Member(parts[0]);
                    newRaid.addMember(member);
                    
                } catch (ParseException ex) {
                    System.out.println("FEHLER: dieser Zustand darf nicht stattfinden!:"+line);
                }
            }
                readMemberPart2(parts, member);
        }
    }   
    
    private void readMemberPart2(String[] parts, Member member){
        member.setName(parts[0]);
        member.setRole(parts[1]);
        member.setMainSpec(parts[2]);
        member.setSecondSpec(parts[3]);
        member.setRaidCount(Integer.parseInt(parts[5]));
        member.setState(parts[6]);
        member.setSksPos(Integer.parseInt(parts[7]));
    }
    
    
    private String writeMember(Member member){
        String line = member.getName();
        line+=trenner.get("trenner")+member.getStringRole();
        line+=trenner.get("trenner")+member.getStringMainSpec();
        line+=trenner.get("trenner")+member.getStringSecondSpec();
        line+=trenner.get("trenner")+"Not Supported Anymore";
        line+=trenner.get("trenner")+member.getRaidCount();
        line+=trenner.get("trenner")+member.getStringState();
        line+=trenner.get("trenner")+member.getSksPos();
        return line;
    }
    
    private void readLoot(String line, Raid newRaid){
        String[] parts = line.split(trenner.get("trenner"));
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
    
    private String writeLoot(Loot loot){
        String line = loot.getMember().getName();
        line+=trenner.get("trenner")+lootDateFormat.format(loot.getTimestamp());
        line+=trenner.get("trenner")+loot.getName();
        return line;
    }
    
    
    public LinkedList<Date> getRaidDates(){
        LinkedList<Date> dates = new LinkedList<>();
        
        File file = new File(".\\RAIDS");
        if(!(file.exists()&&file.isDirectory())){
            System.out.println("FEHLER: Raid-Ordner existiert nicht!");
            return dates;
        }
        ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
        for(int i=0;i<files.size();i++){
            File current = files.get(i);
            String fileName = current.getName();
            if(current.exists()&&fileName.endsWith(".raid")){
                try {
                    String fileDate = fileName.replaceAll(".raid", "");
                    dates.add(fileDateFormat.parse(fileDate));
                } catch (ParseException ex) {
                    System.out.println("FEHLER: kann "+fileName+" nicht entziffern!");
                }
            }
        }
        java.util.Collections.sort(dates);
        return dates;
    }

}
