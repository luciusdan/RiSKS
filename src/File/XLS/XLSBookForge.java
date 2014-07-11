/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File.XLS;

import Data.Book;
import Exceptions.InvalidDateFileException;
import Exceptions.NoDataFileException;
import File.BookForgeInterface;
import File.Comparator.DataFileComparator;
import File.Filter.DataFileFilter;
import File.XLS.XLSLootForge;
import File.XLS.XLSOverviewForge;
import File.XLS.XLSRaidForge;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Dirk
 */
public class XLSBookForge implements BookForgeInterface{
    private static final Logger logger = LogManager.getRootLogger();
    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat("YYYY_DDD_HH_mm_ss");
    
    File[] fileDir;
    XLSOverviewForge overviewForge;
    XLSRaidForge raidForge;
    XLSLootForge lootForge;
    
    public XLSBookForge() throws IOException{
        try {
            InputStream is =this.getClass().getResourceAsStream("/Resource/Template/template_3_slim.xls");
            Workbook templateWorkbook = WorkbookFactory.create(is);
            overviewForge = new XLSOverviewForge(templateWorkbook);
            raidForge = new XLSRaidForge(templateWorkbook);
            lootForge = new XLSLootForge(templateWorkbook);
        } catch (InvalidFormatException ex) {
            throw new IOException(ex.getMessage());
        }
    }
    

    @Override
    public void readBook(Book book, boolean deleteWrong) throws InvalidDateFileException, NoDataFileException {
        book.setForge(this);
        logger.debug("Durchsuche Data Dir.");
        readFilePath();
        
        String list = "\nData List:  ----------------------\n";
        for(File file : fileDir){
            list+="\t"+file.getName()+"\n";
        }
        logger.info(list);
        
        scanForFile(book, deleteWrong);
    }
    
    private void readFilePath() {
        File copyDirFile = new File("Data");
        if (!copyDirFile.exists() || !copyDirFile.isDirectory()) {
            copyDirFile.delete();
            copyDirFile.mkdir();
        } else {
            fileDir = copyDirFile.listFiles(new DataFileFilter(dateFormat));
            if (fileDir.length > 9) {
                Arrays.sort(fileDir, new DataFileComparator());
            }
        }
    }
    
    private void scanForFile(Book book, Boolean remove) throws InvalidDateFileException, NoDataFileException {
        int pos = fileDir.length-1;
        if (fileDir.length>0 && fileDir[pos].exists() && fileDir[pos].canRead()) {
            try {
                logger.info("Teste Datei:"+fileDir[pos].getName());
                readFrom(book, fileDir[pos]);
                for (int i = 0; i < fileDir.length - 9; i++) {
                    fileDir[i].delete();
                }
            } catch (IOException e) {
                if(remove){
                    logger.warn("REMOVE File:"+fileDir[pos].getName());
                    fileDir[pos].delete();
                    readFilePath();
                    scanForFile(book, true);
                }else{
                    throw new InvalidDateFileException(e.getMessage());
                }
                
            }
        }else{
            throw new NoDataFileException("Keine Lesbare XLS-Copy zufinden!");
        }
    }

    @Override
    public void readFrom(Book book, Object obj) throws IOException {
        if(obj instanceof File){
            File file = (File) obj;
            try {
                logger.info("Lese Book aus \""+file.getName()+"\" .");
                book.clear();
                Workbook workbook = WorkbookFactory.create(file);
                //TODO workbook aus File laden
                overviewForge.read(workbook, book);
                lootForge.read(workbook, book);
                
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                        logger.debug("Lese Sheet("+i+"| "+workbook.getSheetName(i)+")");
                    try {
                        raidForge.read(sheet, book);
                    } catch (ParseException ex) {
                        //kommt bei jedem nichtRaid
                    }
                }
                book.setActiveRaid(book.getNewestRaidDate());
            } catch (InvalidFormatException ex) {
                throw new IOException("Kopie("+file.getName()+") ist nicht lesbar.");
            }
            logger.info("Buch gelesen.");
        }
    }

    @Override
    public void writeBook(Book book) throws IOException {
            String copyName = "Data/data_" + dateFormat.format(new Date()) + ".xls";
            File copy = new File(copyName);
            writeTo(book, copy);
    }

    @Override
    public void writeTo(Book book, Object obj) throws IOException{
        if(obj instanceof File){
            File file = (File) obj;
            logger.info("Schreibe Book in: "+file.getName());
            if (file.exists()) {
                file.delete();
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                logger.info("Lade leeres Workbook");
                InputStream is =this.getClass().getResourceAsStream("/Resource/Template/template_3_slim.xls");
                Workbook workbook = WorkbookFactory.create(is);
                

                //RaidTable's schreiben
                logger.info("Schreibe "+workbook.getNumberOfSheets()+" Sheets.");
                for (Date date : book.getRaidDates()) {
                        raidForge.write(book.getRaid(date), workbook);
                }
                
                //ÜbersichtTable schreiben
                overviewForge.write(workbook, book);
                overviewForge.setOverviewToActive(workbook);
                lootForge.write(workbook, book);
                removeTemplateSheets(workbook);
                //Stream an Datei übergeben
                workbook.write(fos); //(58915) is out of allowable range (58911..58913)
                fos.flush();
                fos.close();
                logger.info("Book geschrieben.");
            } catch (FileNotFoundException ex) {
                logger.error("Kann Datei nicht finden, die nicht existieren sollte.", ex);
            //darf nicht vorkommen
            } catch (InvalidFormatException ex) {
                logger.error("Kann Template nicht öffnen.", ex);
            }
        }
    }
    
    private void removeTemplateSheets(Workbook workbook){
        overviewForge.removeTemplateSheet(workbook);
        raidForge.removeTemplateSheet(workbook);
    }
    
}
