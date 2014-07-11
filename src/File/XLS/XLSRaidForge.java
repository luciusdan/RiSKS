/*
 * RiftSKS ist ein Programm zum führen eines Raidkaders mit der Absicht
 * das SKS-Verfahren bei der Lootverteilung anzuwenden.
 * Copyright (C) 2013  Dirk Evers
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.package Main;
 */
package File.XLS;

import Data.Book;
import Controller.DateFormater;
import Data.Loot;
import Data.Player;
import Data.Raid;
import Data.Raid.Place;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Dirk
 */
public class XLSRaidForge {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getRootLogger();
    private String templateName = "RaidTemplate";
    
    private int[] raidDate;
    private int[] raidPlace;
    private int[] raidComment;
    private int playerBegin;
    private int lootBegin;
    private HashMap<String, Integer> playerAttributes;
    private HashMap<String, Integer> lootAttributes;
    
    public XLSRaidForge(Workbook workbook) throws FileNotFoundException, IOException{
        logger.debug("Lese RaidTemplate.");
        Sheet sheet = workbook.getSheet(templateName);
        raidDate = null;
        raidPlace = null;
        raidComment = null;
        playerBegin = -1;
        lootBegin = -1;
        playerAttributes = new HashMap<>();
        lootAttributes = new HashMap<>();

        for(int r =sheet.getFirstRowNum();r<=sheet.getLastRowNum() ;r++){
            Row row = sheet.getRow(r);
            if(row!=null){
                for(int c= row.getFirstCellNum();c<=row.getLastCellNum();c++){
                    Cell cell = row.getCell(c);
                    if(cell!=null&&(raidDate==null||raidPlace==null||raidComment==null||playerBegin==-1||lootBegin==-1)){
                        try{
                            String cellValue=cell.getStringCellValue();
                            if(cellValue.equals("#raidDate")){
                                int[] rD = {r , c};
                                raidDate = rD;
                            }else if(cellValue.equals("#raidPlace")){
                                int[] rP = {r , c};
                                raidPlace = rP;
                            }else if(cellValue.equals("#raidComment")){
                                int[] rC = {r , c};
                                raidComment = rC;
                            }else if(playerBegin<0&&cellValue.startsWith("#player")){
                                playerBegin=r;
                            }else if(lootBegin<0&&cellValue.startsWith("#loot")){
                                lootBegin=r;
                            }
                        }catch(IllegalStateException e){}
                    }
                }
            }
        }
        if(raidDate ==null){
            throw new IOException("#raidDate in RaidTemplate");
        }
        if(raidPlace ==null){
            throw new IOException("#raidPlace in RaidTemplate");
        }
        if(playerBegin >0){
            Row row = sheet.getRow(playerBegin);
            for(int i =0;i<row.getLastCellNum() ;i++){
                Cell cell = row.getCell(i);
                if(cell!=null){
                    switch(cell.getStringCellValue()){
                        case "#playerID": 
                            playerAttributes.put("#playerID", i);
                            break;
                        case "#playerPos": 
                            playerAttributes.put("#playerPos", i);
                            break;
                        case "#playerName": 
                            playerAttributes.put("#playerName", i);
                            break;
                        case "#playerRole": 
                            playerAttributes.put("#playerRole", i);
                            break;
                        case "#playerMS": 
                            playerAttributes.put("#playerMS", i);
                            break;
                        case "#playerSS": 
                            playerAttributes.put("#playerSS", i);
                            break;
                        case "#playerSKSCount": 
                            playerAttributes.put("#playerSKSCount", i);
                            break;
                        case "#playerState": 
                            playerAttributes.put("#playerState", i);
                            break;
                    }
                }
            }
            if(!playerAttributes.containsKey("#playerID")){
                throw new IOException("#playerID in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerPos")){
                throw new IOException("#playerPos in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerName")){
                throw new IOException("#playerName in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerRole")){
                throw new IOException("#playerRole in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerMS")){
                throw new IOException("#playerMS in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerSS")){
                throw new IOException("#playerSS in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerSKSCount")){
                throw new IOException("#playerSKSCount in RaidTemplate");
            }
            if(!playerAttributes.containsKey("#playerState")){
                throw new IOException("#playerState in RaidTemplate");
            }
        }else{
            throw new IOException("#player Attribute fehlen");
        }
        if(lootBegin >0){
            Row row = sheet.getRow(lootBegin);
            for(int i =0;i<row.getLastCellNum() ;i++){
                Cell cell = row.getCell(i);
                if(cell!=null){
                    switch(cell.getStringCellValue()){
                        case "#lootPlayerName": 
                            lootAttributes.put("#lootPlayerName", i);
                            break;
                        case "#lootName": 
                            lootAttributes.put("#lootName", i);
                            break;
                        case "#lootDate": 
                            lootAttributes.put("#lootDate", i);
                            break;
                        case "#lootState": 
                            lootAttributes.put("#lootState", i);
                            break;
                    }
                }
            }
            if(!lootAttributes.containsKey("#lootPlayerName")){
                throw new IOException("#lootPlayerName in RaidTemplate");
            }
            if(!lootAttributes.containsKey("#lootName")){
                throw new IOException("#lootName in RaidTemplate");
            }
            if(!lootAttributes.containsKey("#lootDate")){
                throw new IOException("#lootDate in RaidTemplate");
            }
            if(!lootAttributes.containsKey("#lootState")){
                throw new IOException("#lootState in RaidTemplate");
            }
        }else{
            throw new IOException("#loot Attribute fehlen");
        }
    }
    
    public void read(Sheet sheet, Book book) throws ParseException{
        Raid raid = readContent(sheet, book);
                        book.addRaid(raid);
    }
    
    private Raid readContent(Sheet sheet, Book book) throws ParseException{
        if(sheet==null){
            return null;
        }
        String raidName = sheet.getSheetName();
        logger.debug("Lese Raid:"+raidName);
        // überprüfe ob Raid erstellt werden kann,
        // falls nicht gebe "null" zurück
        // erstelle neuen leeren Raid ------------------------------------------
        Raid raid = new Raid(DateFormater.raidDateFormat.parse(raidName));
        // Raid füllen --------------------------------------------------------- 
        //-- read Place
        //Raid 20er \n [RAIDTYPE]
        Row row = sheet.getRow(0);
        if(row!=null){
            Cell placeCell= getCell(sheet,raidPlace);
            if(placeCell!=null){
                String placesString = placeCell.getStringCellValue();
                ArrayList<Place> places = new ArrayList<>();
                String[] plStrings = placesString.split("/");
                for(String placeString : plStrings){
                    placeString = placeString.replaceAll(" ", "");
                    Place place = Raid.getPlace(placeString);
                    if(place!=null){
                        places.add(place);
                    }
                }
                raid.setPlaces(places);
            }
        }

        //-- read Comment
        Cell commentCell=getCell(sheet,raidComment);
        if(commentCell!=null){
            raid.setComment(commentCell.getStringCellValue());
        }
        //-- read members 
        Player player= new Player();
        for(int i=playerBegin;player!=null;i++){
            row= sheet.getRow(i);
            player= readMember(row, book);
            raid.addPlayer(player);
        }
        //-- read Loot 
            row= sheet.getRow(lootBegin);
        Loot loot= readLoot(row,raid);
            raid.addLoot(loot);
        for(int i=lootBegin+1;loot!=null;i++){
            row= sheet.getRow(i);
            loot= readLoot(row,raid);
            raid.addLoot(loot);
        }

            return raid;
    }
    
    private Player readMember(Row row, Book book){
        Player player = null;
        if(row!=null){
            Cell idCell = row.getCell(playerAttributes.get("#playerID"));
            if(idCell!=null&&!idCell.getStringCellValue().equals("")){
                player =  new Player(book.getOverviewPlayer(idCell.getStringCellValue()));
                
                player.setName((String)row.getCell(playerAttributes.get("#playerName")).getStringCellValue());
                player.setSksPos((int)row.getCell(playerAttributes.get("#playerPos")).getNumericCellValue());
                player.setRaidCount((int)row.getCell(playerAttributes.get("#playerSKSCount")).getNumericCellValue());

                player.setCalling(row.getCell(playerAttributes.get("#playerRole")).getStringCellValue());
                player.setSpecMain(row.getCell(playerAttributes.get("#playerMS")).getStringCellValue());

                Cell secSpecCell = row.getCell(playerAttributes.get("#playerSS"));
                Cell stateCell = row.getCell(playerAttributes.get("#playerState"));
                if(secSpecCell!=null){
                    String str = secSpecCell.getStringCellValue();
                    String[] strings = str.split("/");
                    for(String s : strings){
                        player.addSpecSecond(s);
                    }
                }
                if(stateCell!=null){
                    player.setState(stateCell.getStringCellValue());
                }
            }
            return player;
        }else{
            return player;
        }
    }
    
    private Loot readLoot(Row row,Raid raid){
        Loot loot = null;
            if(row!= null){
                Cell nameCell = row.getCell(lootAttributes.get("#lootPlayerName"));
                if(nameCell!= null&&!nameCell.getStringCellValue().equals("")){
                    String playerName = nameCell.getStringCellValue();
                    Cell commentCell = row.getCell(lootAttributes.get("#lootName"));
                    String comment = "";
                    if(commentCell!=null){
                        comment = commentCell.getStringCellValue();
                    }
                    Cell dateCell = row.getCell(lootAttributes.get("#lootDate"));
                    Date date = dateCell.getDateCellValue();
                    
                    loot = new Loot(date,raid.getPlayerByName(playerName),comment);
                    
                    Cell stateCell = row.getCell(lootAttributes.get("#lootState"));
                    if(stateCell!=null){
                        String stateStr = stateCell.getStringCellValue();
                        loot.setState(Loot.stringToState(stateStr));
                    }
                }
            }
        return loot;
    }
    
    
    public void write(Raid raid, Workbook workbook){
        String sheetName = DateFormater.raidDateFormat.format(raid.getDate());
        logger.info("Schreibe Sheet:"+sheetName);
        Sheet sheet = workbook.cloneSheet(workbook.getSheetIndex(templateName));
        workbook.setSheetOrder(sheet.getSheetName(), 1);
        workbook.setSheetName(workbook.getSheetIndex(sheet), sheetName);
        
        writeContent(raid, sheet);
    }
    
    private void writeContent(Raid raid, Sheet sheet){
        Row removeRow = sheet.getRow(playerBegin);
        if(removeRow!=null){
            Cell cell;
            cell = removeRow.getCell(playerAttributes.get("#playerID"));
            if(cell!=null){
                for(String attr : playerAttributes.keySet()){
                    cell = removeRow.getCell(playerAttributes.get(attr));

                    if(cell!=null){
                        cell.setCellType(1);
                        cell.setCellValue("");
                    }
                }
            }
        }
        removeRow = sheet.getRow(lootBegin);
        if(removeRow!= null){
            Cell cell;
            cell = removeRow.getCell(lootAttributes.get("#lootPlayerName"));
            if(cell!=null){
                for(String attr : lootAttributes.keySet()){
                    cell = removeRow.getCell(lootAttributes.get(attr));
                    if(cell!=null){
                        cell.setCellType(1);
                        cell.setCellValue("");
                    }
                }
            }
        }
        
        //-- write Date
        Cell dateCell = getCell(sheet,raidDate);
        dateCell.setCellValue(raid.getDate());
        
        //-- write Comment
        Cell commentCell= getCell(sheet,raidComment);
        commentCell.setCellValue(raid.getComment());

        //-- write Place
        //Raid 20er \n [RAIDTYPE]
        Cell placeCell= getCell(sheet,raidPlace);
        ArrayList<Place> places = raid.getPlaces();
        String str = "";
        int size = places.size();
        for(int i=0;i<size;i++){
            Place pl = places.get(i);
            str += Raid.getPlaceAlias(pl);
            if(i<size-1){
                str+= "/";
            }
        }
        placeCell.setCellValue(str);

        //-- write members 

        LinkedList<Player> members = raid.getSKS();
        if(members.size()>0){
            for(int i=0;i<members.size();i++){
                Row row = sheet.getRow(i+playerBegin);
                if(row==null){
                    sheet.createRow(i+playerBegin);
                }
                Player player = members.get(i);
                writeMember(player, i+playerBegin,sheet);
            }
        }else{
            Cell cell = getCell(sheet,playerBegin,playerAttributes.get("#playerID"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerPos"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerName"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerRole"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerMS"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerSS"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerSKSCount"));
            cell.setCellValue("");
            cell = getCell(sheet,playerBegin,playerAttributes.get("#playerState"));
            cell.setCellValue("");
        }

        //-- write Loot 
        Date[] dates = raid.getlootDates();
        Arrays.sort(dates);
        if(dates.length>0){
            for(int i=0;i<dates.length;i++){
                Row row = sheet.getRow(i+lootBegin);
                if(row==null){
                    sheet.createRow(i+lootBegin);
                }
                Loot loot = raid.getLoot(dates[i]);
                writeLoot(loot,lootBegin+i,sheet);
            }
        }else{
            Cell cell = getCell(sheet,lootBegin,lootAttributes.get("#lootPlayerName"));
            cell.setCellValue("");
            cell = getCell(sheet,lootBegin,lootAttributes.get("#lootName"));
            cell.setCellValue("");
            cell = getCell(sheet,lootBegin,lootAttributes.get("#lootDate"));
            cell.setCellValue("");
        }
    }
    
    private void writeMember(Player player,int row,Sheet raidSheet){
        Cell cell = getCell(raidSheet,row,playerAttributes.get("#playerID"));
        cell.setCellValue(player.getId());       
        cell = getCell(raidSheet,row,playerAttributes.get("#playerPos"));
        cell.setCellValue(player.getSksPos());
        cell = getCell(raidSheet,row,playerAttributes.get("#playerName"));
        cell.setCellValue(player.getName());
        cell = getCell(raidSheet,row,playerAttributes.get("#playerRole"));
        cell.setCellValue(player.getCallingString());
        cell = getCell(raidSheet,row,playerAttributes.get("#playerMS"));
        cell.setCellValue(player.getSpecMainString());
        cell = getCell(raidSheet,row,playerAttributes.get("#playerSS"));
                String specString="";
                LinkedList<Player.Spec> specs = player.getSpecsSecond();
                for(int j=0; j<specs.size();j++){
                    specString+=Player.specToString(specs.get(j));
                    if(j<specs.size()-1){
                        specString+="/";
                    }
                }
        cell.setCellValue(specString);
        cell = getCell(raidSheet,row,playerAttributes.get("#playerSKSCount"));
        cell.setCellValue(player.getRaidCount());
        cell = getCell(raidSheet,row,playerAttributes.get("#playerState"));
        String state = player.getStateString();
        cell.setCellValue((state.equals("abwesend"))?"":state);
    }
    
    private void writeLoot(Loot loot, int row,Sheet raidSheet){
        Cell cell = getCell(raidSheet,row,lootAttributes.get("#lootPlayerName"));
        cell.setCellValue(loot.getPlayer().getName());
        cell = getCell(raidSheet,row,lootAttributes.get("#lootName"));
        cell.setCellValue(loot.getDescription());
        cell = getCell(raidSheet,row,lootAttributes.get("#lootDate"));
        cell.setCellValue(loot.getTimestamp());
        cell = getCell(raidSheet,row,lootAttributes.get("#lootState"));
        cell.setCellValue(Loot.StateToString(loot.getState()));
    }
    
    private Cell getCell(Sheet sheet,int[] pos){
        return getCell(sheet,pos[0],pos[1]);
    }
    private Cell getCell(Sheet sheet,int row, int col){
        Cell cell = sheet.getRow(row).getCell(col);
        if(cell == null){
            cell = sheet.getRow(row).createCell(col);
        }
        return cell;
    }
    
    public void removeTemplateSheet(Workbook workbook) {
        int index = workbook.getSheetIndex(templateName);
                if (index >= 0&& index<workbook.getNumberOfSheets()) {
                    workbook.removeSheetAt(index);
                }
    }
}
