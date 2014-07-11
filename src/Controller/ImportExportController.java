/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Book;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class ImportExportController {
    private static final Logger logger = LogManager.getRootLogger();
    Book book;
    JFileChooser jfc;
    Boolean export;
    
    public ImportExportController(Book book, JFileChooser jfc){
        logger.debug("Erstelle ImportExportController.");
        this.book = book;
        this.jfc = jfc;
        File path = new File(System.getProperty("user.dir"));
        //jfc.setCurrentDirectory(path);
        jfc.setFileFilter(new FileNameExtensionFilter("Exel-Datei \".xls\"", "xls"));
        jfc.setSelectedFile(new File(path.getAbsolutePath()+"//SKSBook.xls"));
    }
    
    private void accept(){
            JFrame frame = new JFrame("Import/Export");
            File file = jfc.getSelectedFile();
            if(export){
                try {
                    if(file.exists()){
                        int n = JOptionPane.showConfirmDialog(
                            frame,
                            "Die ausgewählte Datei \""+file.getName()+"\"existiert bereits!\n"+
                            "Soll die Datei überschrieben werden?"
                                    ,
                            "Datei überschreiben?",
                            JOptionPane.YES_NO_OPTION);
                        if(n==0){
                            book.getForge().writeTo(book,file);
                        }
                    }else{
                        book.getForge().writeTo(book,file);
                    }
                } catch (IOException ex) {
                    logger.error("Konnte Datei \""+file.getName()+"\" nicht schreiben.",ex);
                    JOptionPane.showMessageDialog(
                            frame,
                            "Es ist ein fehler beim schreiben der Datei \""+file.getName()+"\" aufgetreten!",
                            "Datei konnte nicht geschrieben werden!\n ("+ex.getMessage()+")",
                            JOptionPane.WARNING_MESSAGE);
                }
            }else{
                if(file.exists()){
                    try{
                        book.getForge().readFrom(book,file);
                    }catch(IOException ex)  {
                        logger.error("Konnte Datei\""+file.getName()+"\" nicht lesen.",ex);
                        JOptionPane.showMessageDialog(
                            frame,
                            "Es ist ein fehler beim lesen der Datei"
                                + " \""+file.getName()+"\" aufgetreten!\n"
                                + " für genaueres siehe die Konsole."
                                ,
                            "Datei konnte nicht geöffnet werden!",
                            JOptionPane.WARNING_MESSAGE);
                        }
                }else{
                    logger.error("Keine gülitge Datei ausgewählt.");
                    JOptionPane.showMessageDialog(
                        frame,
                        "Entweder es wurde keine Datei ausgewählt\n oder die Datei konnte nicht gefunden werden!",
                        "Keine Datei gefunden!",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    
    public boolean startExport(){
        export = true;
        
        jfc.setDialogTitle("Exportieren");
        int i = jfc.showSaveDialog(null);
        if(i==JFileChooser.APPROVE_OPTION){
            accept();
            return true;
        }else{
            return false;
        }
    }
    
    public boolean startImport(){
        export = false;
        jfc.setDialogTitle("Importieren");
        int i = jfc.showOpenDialog(null);
        if(i==JFileChooser.APPROVE_OPTION){
            accept();
            return true;
        }else{
            return false;
        }
    }
    
    
}
