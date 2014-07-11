/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Threading;

import Controller.Console.ErrorController;
import Controller.MainFrameController;
import Data.Book;
import Exceptions.InvalidDateFileException;
import Exceptions.NoDataFileException;
import File.BookForgeInterface;
import File.XLS.XLSBookForge;
import GUI.MainFrame;
import GUI.WaitScreen;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class ControllerThread implements Runnable{
    private static final Logger logger = LogManager.getRootLogger();
        MainFrame mainFrame;
        WaitScreen ws;
        ErrorController ec ;
    
    public ControllerThread(MainFrame mf,WaitScreen ws,ErrorController ec){
        super();
        this.mainFrame = mf;
        this.ws = ws;
        this.ec = ec;
    }
    
    @Override
    public void run() {
        try{
            Book book = new Book();
            BookForgeInterface forge = new XLSBookForge();
            try {
                forge.readBook(book, false);
            } catch (InvalidDateFileException ex) {
                JFrame frame = new JFrame("Änderungen speichern?");
                        int n = JOptionPane.showConfirmDialog(
                        frame,
                        "Die zuletzt gespeicherten Daten sind nicht lesbar!\n\n"+
                                "Soll ein früherer Stand geladen werden?\n"+
                                "Die letzen Änderungen werden dadurch unwirksam."+ex.getLocalizedMessage(),
                        "Gespeicherte Datein sind beschädigt!",
                        JOptionPane.YES_NO_OPTION);
                        if(n==0){
                        try {
                            forge.readBook(book, true);
                        } catch (InvalidDateFileException ex1) {
                            //wird nur geworfen, falls false übergeben wird,
                            //also somit nicht möglich.
                        }
                        }else{
                            logger.warn("Abbruch durch User, statt löschen von.");
                            System.exit(0);
                        }
            }
            MainFrameController mfController = new MainFrameController(mainFrame, book, ec);
            mainFrame.setVisible(true);
            ws.setVisible(false);
        } catch (NoDataFileException e) {
            JFrame frame = new JFrame("DateErrorDialog");
            logger.fatal("Kann keine .xls im Data-Ordner finden.");
            JOptionPane.showMessageDialog(
                    frame,
                    "Fehler beim lesen der Dateien:\n" + e.getMessage(),
                    "Fehler beim laden der data.xls!",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }   catch (IOException ex) {
             JFrame frame = new JFrame("DateErrorDialog");
            logger.fatal("Kann Template nicht laden.");
            JOptionPane.showMessageDialog(
                    frame,
                    "Fehler beim lesen der Template Datei.\n" + ex.getMessage(),
                    "Fehler beim laden des Template!",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
    
    
}
