/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Raid;

import Controller.ControllerInterface;
import Controller.Edit.PlayerEditController;
import Data.Book;
import Data.Raid;
import GUI.Raid.SKSPanel;
import PDF.PDFHandler;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class SKSPanelController implements ControllerInterface {
    private static final Logger logger = LogManager.getRootLogger();
    private PDFHandler pdf;
    private Book book;
    private SKSPanel sksPanel;
    private PlayerEditController pec;
    private LootListController llc;

    public SKSPanelController(SKSPanel sksPanel, PlayerEditController mec, Book book) {
        pdf = new PDFHandler(book);
        this.book = book;
        this.sksPanel = sksPanel;
        this.pec = mec;
        this.llc = new LootListController(book);
        setListener();
    }
    private void setListener(){
        pec.addAcceptController(this);
        sksPanel.addPlayerAddListener(new MemberAddListener());
        sksPanel.addPlayerEditListener(new MemberEditListener());
        sksPanel.addPlayerRemoveListener(new MemberRemoveListener());
        sksPanel.addRoleFilterListener(new FilterListener());
        sksPanel.addSpecFilterListener(new FilterListener());
        sksPanel.addPrintListener(new PrintListener());
        sksPanel.addPrintPathListener(new PrintPathListener());
        sksPanel.addLootListListener(new LootListListener());
    }
    
    class MemberAcceptListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.acceptMember();
            updateView();
        }
    }
    
    public void updateView(){
        sksPanel.setContent(book.getActiveRaid());
        sksPanel.setEditable(book.checkFullEditable());
    }
     
    class LootListListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            llc.showFrame();
        }
    }
    class FilterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateView();
        } 
    }
    class MemberAddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.createMember();
        }
    }
    class MemberRemoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = sksPanel.getSelectedPlayerName();
            pec.removePlayer(name);
            updateView();        
        }
    }
    class MemberEditListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String memberName = sksPanel.getSelectedPlayerName();
            if(memberName!=null){
                Raid raid = book.getActiveRaid();
                pec.editMember(raid.getPlayerByName(memberName));
            }
        }
    }
    class PrintListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            logger.info("Print PDF");
            pdf.printPDF();
        }
    }
    
    private class PrintPathListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            File file = new File("SKS");
            if(file.exists()&&file.isDirectory()){
                try {
                    Desktop.getDesktop().open(file);
                    //Runtime.getRuntime().exec("explorer.exe SKS");
                } catch (IOException ex) {
                    logger.error("Kann SKS-Pfad nicht öffnen.",ex);
                }
            }
        }
    }
     
}
