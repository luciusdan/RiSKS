/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Console.ErrorController;
import Controller.Edit.PlayerEditController;
import Controller.Edit.GuildEditController;
import Data.Book;
import Data.Raid;
import GUI.MainFrame;
import Exceptions.RaidAlreadyExistException;
import Exceptions.RaidDateInvalidException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Dirk
 */
public class MainFrameController implements ControllerInterface {
    Book book;
    MainFrame mainFrame;
    PlayerEditController pec;
    
    OverviewPanelController opc;
    RaidPanelController rpc;
    ItemPanelController ipc;
    GuildEditController gc;
    ImportExportController iec;
    ErrorController ec;

    public MainFrameController(MainFrame mainFrame, Book book, ErrorController ec){
        this.ec = ec;
        this.mainFrame = mainFrame;
        this.book = book;
        pec= new PlayerEditController(book);
        
        this.iec = new ImportExportController(book , mainFrame.getFileChooser());
        this.gc = new GuildEditController(book);
        this.opc = new OverviewPanelController(mainFrame.getRaiderPanel(),pec,book);
        this.rpc = new RaidPanelController(mainFrame.getRaidPanel(),pec,book);
        this.ipc = new ItemPanelController(mainFrame.getItemPanel(),book);
        
        addListener();
        updView();
    }
    
    private void addListener(){
        pec.addAcceptController(this);
        mainFrame.addMemberAddListener(new MemberAddListener());
        mainFrame.addFormListener(new FormListener());
        mainFrame.addGuildListener(new GuildListener());
        mainFrame.addSaveAllListener(new SaveListener());
        mainFrame.addTabListener(new TabChangeListener());
        mainFrame.addSKSResetListener(new ResetListener());
        
        RemoveListener rl = new RemoveListener();
        CreateListener cl = new CreateListener();
        mainFrame.addRemoveRaidListener(rl);
        mainFrame.addCreateRaidListener(cl);
        rpc.setRemoveButtonListener(rl);
        rpc.setCreateButtonListener(cl);
        
        mainFrame.addImportListener(new ImportListener());
        mainFrame.addExportListener(new ExportListener());
        
        mainFrame.addErrorFrameButtonListener(new ErrorFrameButtonListener());
    }
    class ErrorFrameButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ec.setVisible();
        }
    }
    class MemberAddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.createMember();
        }
    }
    
    class CreateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("ChangeDialog");
            Date date = new Date();
            //date glätten
            try {
            String dateString = DateFormater.raidDateFormat.format(date);
                date = DateFormater.raidDateFormat.parse(dateString);
            } catch (ParseException ex) {
                // Kommt nicht vor!
            }
            Date lastRaidDate = book.getNewestRaidDate();

            if(date.equals(lastRaidDate)||!date.after(lastRaidDate)){
                Calendar cal = new GregorianCalendar();
                cal.setTime(lastRaidDate);
                cal.add(Calendar.DAY_OF_WEEK, 1);
                date = cal.getTime();
            }
            try {
                Raid raid = book.newRaid(date);
                book.setActiveRaid(raid.getDate());
                updateView();
            } catch (RaidAlreadyExistException ex) {
                JOptionPane.showMessageDialog(
                frame,
                "Ein Raid am "+DateFormater.fullDateFormat.format(date)+" existiert bereits!",
                "Fehler beim Raid erstellen!",
                JOptionPane.WARNING_MESSAGE);
            } catch (RaidDateInvalidException ex) {
                Date oldDate = book.getNewestRaidDate();
                JOptionPane.showMessageDialog(
                frame,
                "Der Raid "+DateFormater.fullDateFormat.format(date)+" muss nach dem letzten Raid vom  "+DateFormater.fullDateFormat.format(oldDate)+" stattfinden!",
                "Fehler beim Raid erstellen!",
                JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    class ResetListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("ChangeDialog");
            int n = JOptionPane.showConfirmDialog(
            frame,
            "Sollen die SKS-Liste und alle Informationen gelöscht werden?",
            "Neue SKS-Liste  und alles zurücksetzten?",
            JOptionPane.YES_NO_OPTION);
            if(n==0){
                book.reset();
            }
            updateView();
        }
    }
    
    private class ExportListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean bool = iec.startExport();
            if(bool){
                updateView();
            }
        }
    }
    
    private class ImportListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean bool = iec.startImport();
            if(bool){
                updateView();
            }
        }
    }
    
    class FormListener implements WindowListener{
        private void closeAction(){
            JFrame frame = new JFrame("Änderungen speichern?");
            int n = JOptionPane.showConfirmDialog(
            frame,
            "Sollen die Änderungen vor dem Beenden\n gespeichert werden?",
            "Änderungen speichern?",
            JOptionPane.YES_NO_CANCEL_OPTION);
            if(n==0){
                try {
                    book.getForge().writeBook(book);
                    System.exit(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Es ist ein fehler beim lesen der Datei "
                            + "aufgetreten!\n für genaueres siehe die Konsole.",
                            "Datei konnte nicht geöffnet werden!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }else if(n==1){
                System.exit(0);
            }else{
                mainFrame.setVisible(true);
            }
        }
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            closeAction();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            closeAction();
        }
        
        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
    
    class GuildListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            gc.showFrame();
        }
    }
    
    class RemoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("ChangeDialog");

                Date lastRaidDate = book.getNewestRaidDate();
                if(book.getRaidDates().size()<=1){
                    JOptionPane.showMessageDialog(
                    frame,
                    "Es muss mindestens ein Raid vorhanden sein.",
                    "Letzter Raid nicht löschbar!",
                    JOptionPane.WARNING_MESSAGE);
                }else{
                    int n = JOptionPane.showConfirmDialog(
                    frame,
                    "Wollen sie wirklich den letzten Raid vom\n"+DateFormater.raidDateFormat.format(lastRaidDate)+"\n löschen?",
                    "Letzten Raid löschen",
                    JOptionPane.YES_NO_OPTION);
                    if(n==0){
                        Raid raid = book.getRaid(lastRaidDate);
                        book.removeRaid(raid);
                        book.setActiveRaid(book.getNewestRaidDate());
                        updateView();
                    }
                }
        }
    }
  
    class SaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Frame");
            try {
                book.getForge().writeBook(book);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                            frame,
                            "Es ist ein fehler beim lesen der Datei "
                            + "aufgetreten!\n für genaueres siehe die Konsole.",
                            "Datei konnte nicht geöffnet werden!",
                            JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    class TabChangeListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            updateView();
        }
        
    }

    private void updView(){
        switch(mainFrame.getTabbedPanel()){
            case(0):
                opc.updateView();
                break;
            case(2):
                ipc.updateView();
                break;
            default:
                rpc.updateView();
        }
    }
    
    @Override
    public void updateView(){
        updView();
    }
    
}
