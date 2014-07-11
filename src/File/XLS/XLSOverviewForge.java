/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File.XLS;

import Data.Book;
import Data.Guild;
import Data.Player;
import java.io.IOException;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Dirk
 */
public class XLSOverviewForge {
    private static final Logger logger = LogManager.getRootLogger();
    private String sheetName = "Overview";
    private String templateName = "OverviewTemplate";
    
    private String playerIdTag = "#player";
    private String playerGuildTag = "#playerGuild";
    
    private Workbook templateWorkbook;
    
    private HashMap<String, Integer> playerAttributes;
    private int playerBegin;
    
    
    
    public XLSOverviewForge(Workbook template) throws IOException, InvalidFormatException{
        logger.debug("Lese OverviewTemplate.");
        playerBegin = -1;
        playerAttributes = new HashMap<>();
        templateWorkbook = template;
        
        Sheet templateSheet = templateWorkbook.getSheet(templateName);
        
        for(int r =templateSheet.getFirstRowNum();r<=templateSheet.getLastRowNum() ;r++){
            Row row = templateSheet.getRow(r);
            if(row!=null){
                for(int c= row.getFirstCellNum();c<=row.getLastCellNum();c++){
                    Cell cell = row.getCell(c);
                    if(cell!=null&&(playerBegin==-1)){
                        try{
                            String cellValue=cell.getStringCellValue();
                            if(playerBegin<0&&cellValue.startsWith(playerIdTag)){
                                playerBegin=r;
                                break;
                            }
                        }catch(IllegalStateException e){
                            // Cell ist kein StringCell somit nicht gesuchter Tag
                        }
                    }
                }
            }
        }
        if(playerBegin >0){
            Row row = templateSheet.getRow(playerBegin);
            for(int i =0;i<row.getLastCellNum() ;i++){
                Cell cell = row.getCell(i);
                if(cell!=null){
                    switch(cell.getStringCellValue()){
                        case "#playerID": 
                            playerAttributes.put(playerIdTag, i);
                            break;
                        case "#playerGuild": 
                            playerAttributes.put(playerGuildTag, i);
                            break;
                    }
                }
            }
            if(!playerAttributes.containsKey(playerIdTag)){
                throw new InvalidFormatException(playerIdTag+" konnte nicht in OverviewTemplate gefunden werden");
            }
            if(!playerAttributes.containsKey(playerGuildTag)){
                throw new InvalidFormatException(playerGuildTag+" konnte nicht in OverviewTemplate gefunden werden");
            }
        }
    }
    
    public void read(Workbook workbook, Book book){
        logger.debug("Lese Overview.");
        book.clearOverviewPlayers();
        Sheet sheet = workbook.getSheet(sheetName);
        boolean endreached = false;
        for(int i=playerBegin;!endreached;i++){
            Row row= sheet.getRow(i);
            Player player;
            if(row==null){
                player=null;
                endreached = true;
            }else{
                player= readPlayer(row, book);
            }
            book.addOverviewPlayer(player);
        }
    }
    
    private Player readPlayer(Row row, Book book){
        Cell idCell = row.getCell(playerAttributes.get(playerIdTag));
        Cell guildCell = row.getCell(playerAttributes.get(playerGuildTag));
        if (idCell== null||idCell.getStringCellValue().equals("")){
            return null;
        }
        Player player = new Player(idCell.getStringCellValue());
        logger.debug("Lese Spieler "+ idCell.getStringCellValue());
        if(guildCell!=null){
            String guildName = guildCell.getStringCellValue();
                Guild guild = book.getGuild(guildName);
                if(guild==null){
                    guild = new Guild(guildName);
                    book.addGuild(guild);
                    player.setGuild(guild);
                }else{
                    player.setGuild(guild);
                }
        }else{
            Guild guild = book.getGuild("-");
            player.setGuild(guild);
        }
        return player;
    }
   
    public void write(Workbook workbook, Book book){
        logger.info("Schreibe Overview");
        Sheet copy = workbook.cloneSheet(workbook.getSheetIndex(templateName));
        workbook.setSheetName(workbook.getSheetIndex(copy), sheetName);
        workbook.setSheetOrder(sheetName, 0);
        
        Row row = copy.getRow(playerBegin);
        if(row!=null){
            if(playerAttributes.containsKey(playerIdTag)){
                Cell cell = row.getCell(playerAttributes.get(playerIdTag));
                if(cell!=null){
                    cell.setCellValue("");
                }
                cell = row.getCell(playerAttributes.get(playerGuildTag));
                if(cell!=null){
                    cell.setCellValue("");
                }
            }
        }
        
        String[] ids = book.getPlayerIDs();
        int rowPos = playerBegin;
        for(String id : ids){
            Player player = book.getOverviewPlayer(id);
            
            row = copy.getRow(rowPos);
            if(row==null){
                row = copy.createRow(rowPos);
            }
            
            if(playerAttributes.containsKey(playerIdTag)){
                int col = playerAttributes.get(playerIdTag);
                if(col>=0){
                    Cell cell = row.getCell(col);
                    if(cell==null){
                        cell = row.createCell(col);
                    }
                    cell.setCellValue(player.getId());
                }
            }
            if(playerAttributes.containsKey(playerGuildTag)){
                int col = playerAttributes.get(playerGuildTag);
                if(col>=0){
                    Cell cell = row.getCell(col);
                    if(cell==null){
                        cell = row.createCell(col);
                    }
                    if(player.getGuild()!=null){
                        cell.setCellValue(player.getGuild().getName());
                    }
                }
            }
            rowPos++;
        }
    }
    
    public void setOverviewToActive(Workbook workbook) {
        workbook.setActiveSheet(workbook.getSheetIndex(sheetName));
    }
    
    public void removeTemplateSheet(Workbook workbook) {
        int index = workbook.getSheetIndex(templateName);
                if (index >= 0&& index<workbook.getNumberOfSheets()) {
                    workbook.removeSheetAt(index);
                }
    }
}
