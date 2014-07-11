/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File.XLS;

import Data.Book;
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
public class XLSLootForge {
    private static final Logger logger = LogManager.getRootLogger();
    private String sheetName = "LootLinks";
    private String templateName = "LootTemplate";
    
    private String lootNameTag = "#lootName";
    private String lootLinkTag = "#lootLink";
    
    private Workbook templateWorkbook;
    
    private HashMap<String, Integer> lootAttributes;
    private int lootBegin;
    
    
    
    public XLSLootForge(Workbook template) throws IOException, InvalidFormatException{
        logger.debug("Lese LootTemplate.");
        lootBegin = -1;
        lootAttributes = new HashMap<>();
        templateWorkbook = template;
        
        Sheet templateSheet = templateWorkbook.getSheet(templateName);
        
        for(int r =templateSheet.getFirstRowNum();r<=templateSheet.getLastRowNum() ;r++){
            Row row = templateSheet.getRow(r);
            if(row!=null){
                for(int c= row.getFirstCellNum();c<=row.getLastCellNum();c++){
                    Cell cell = row.getCell(c);
                    if(cell!=null&&(lootBegin==-1)){
                        try{
                            String cellValue=cell.getStringCellValue();
                            if(lootBegin<0&&cellValue.startsWith(lootNameTag)){
                                lootBegin=r;
                                break;
                            }
                        }catch(IllegalStateException e){
                            // Cell ist kein StringCell somit nicht gesuchter Tag
                        }
                    }
                }
            }
        }
        if(lootBegin >0){
            Row row = templateSheet.getRow(lootBegin);
            for(int i =0;i<row.getLastCellNum() ;i++){
                Cell cell = row.getCell(i);
                if(cell!=null){
                    switch(cell.getStringCellValue()){
                        case "#lootName": 
                            lootAttributes.put(lootNameTag, i);
                            break;
                        case "#lootLink": 
                            lootAttributes.put(lootLinkTag, i);
                            break;
                    }
                }
            }
            if(!lootAttributes.containsKey(lootNameTag)){
                throw new InvalidFormatException(lootNameTag+" konnte nicht in OverviewTemplate gefunden werden");
            }
            if(!lootAttributes.containsKey(lootLinkTag)){
                throw new InvalidFormatException(lootLinkTag+" konnte nicht in OverviewTemplate gefunden werden");
            }
        }
    }
    
    public void read(Workbook workbook, Book book){
        logger.debug("Lese LootLinkSheet.");
        book.clearLootLinks();
        
        Sheet sheet = workbook.getSheet(sheetName);
        if(sheet!=null){
            boolean endreached = false;
            for(int i=lootBegin;!endreached;i++){
                Row row= sheet.getRow(i);
                if(row==null){
                    endreached = true;
                }else{
                    Cell nameCell = row.getCell(lootAttributes.get(lootNameTag));
                    Cell lootLinkCell = row.getCell(lootAttributes.get(lootLinkTag));
                    if (nameCell!= null&&!nameCell.getStringCellValue().equals("")){
                        String name = nameCell.getStringCellValue();
                        if (lootLinkCell!= null){
                            String link = lootLinkCell.getStringCellValue();
                            logger.debug("LootLink:"+name+" ; "+link);
                            book.addLootLink(name, link);
                        }else{
                            logger.debug("LootLink:"+name+" ohne Link");
                            book.addLootLink(nameCell.getStringCellValue(), "");
                        }
                    }else{
                        logger.debug("kann LootLinkEintrag nicht lesen.");
                    }
                }
            }
        }else{
            logger.warn("Kein LootLinkSheet gefunden!");
        }
    }
   
    public void write(Workbook workbook, Book book){
        logger.info("Schreibe LootLinkSheet");
        Sheet copy = workbook.cloneSheet(workbook.getSheetIndex(templateName));
        workbook.setSheetName(workbook.getSheetIndex(copy), sheetName);
        workbook.setSheetOrder(sheetName, 0);
        
        Row row = copy.getRow(lootBegin);
        if(row!=null){
            if(lootAttributes.containsKey(lootNameTag)){
                Cell cell = row.getCell(lootAttributes.get(lootNameTag));
                if(cell!=null){
                    cell.setCellValue("");
                }
                cell = row.getCell(lootAttributes.get(lootLinkTag));
                if(cell!=null){
                    cell.setCellValue("");
                }
            }
        }
        
        HashMap<String,String> map =book.getLootLinks();
        int rowPos = lootBegin;
        
        for(String lootName : map.keySet()){
            String lootLink = map.get(lootName);
            row = copy.getRow(rowPos);
            if(row==null){
                row = copy.createRow(rowPos);
            }
            
            if(lootAttributes.containsKey(lootNameTag)){
                int col = lootAttributes.get(lootNameTag);
                if(col>=0){
                    Cell cell = row.getCell(col);
                    if(cell==null){
                        cell = row.createCell(col);
                    }
                    cell.setCellValue(lootName);
                }
            }
            if(lootAttributes.containsKey(lootLinkTag)){
                int col = lootAttributes.get(lootLinkTag);
                if(col>=0){
                    Cell cell = row.getCell(col);
                    if(cell==null){
                        cell = row.createCell(col);
                    }
                    if(lootLink!=null){
                        cell.setCellValue(lootLink);
                    }else{
                        cell.setCellValue("");
                    }
                }
            }
            rowPos++;
        }
    }
    
    public void removeTemplateSheet(Workbook workbook) {
        int index = workbook.getSheetIndex(templateName);
                if (index >= 0&& index<workbook.getNumberOfSheets()) {
                    workbook.removeSheetAt(index);
                }
    }
}
