/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File.DB;

import Data.Book;
import Exceptions.InvalidDateFileException;
import Exceptions.NoDataFileException;
import File.BookForgeInterface;
import File.XLS.XLSLootForge;
import File.XLS.XLSOverviewForge;
import File.XLS.XLSRaidForge;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class DBBookForge implements BookForgeInterface{
    private static final Logger logger = LogManager.getRootLogger();
    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat("YYYY_DDD_HH_mm_ss");
    
    File[] fileDir;
    XLSOverviewForge overviewForge;
    XLSRaidForge raidForge;
    XLSLootForge lootForge;
    
    public DBBookForge() throws IOException{
        
        
    }
    

    @Override
    public void readBook(Book book, boolean deleteWrong) throws InvalidDateFileException, NoDataFileException {
        //TODO
    }
    
    @Override
    public void readFrom(Book book, Object obj) throws IOException {
        //TODO
    }

    @Override
    public void writeBook(Book book) throws IOException {
            //TODO
    }

    @Override
    public void writeTo(Book book, Object obj) throws IOException{
        //TODO
    }
    
}
