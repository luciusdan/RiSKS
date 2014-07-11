/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Raid;

import Controller.ControllerInterface;
import Controller.Edit.PlayerEditController;
import Data.Book;
import Data.Player;
import Data.Player.RaidState;
import Data.Raid;
import GUI.Raid.SelectPlayerPanel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JTable;

/**
 *
 * @author Dirk
 */
public class SelectPlayerController implements ControllerInterface {
    private SelectPlayerPanel rmp;
    private Book book;
    private PlayerEditController pec;

    public SelectPlayerController(SelectPlayerPanel raidMemberPanel, PlayerEditController mec, Book book) {
        this.book = book;
        this.rmp = raidMemberPanel;
        this.pec = mec;
        this.rmp.setRenderer(book);
        addListener();
    }
    private void addListener(){
        rmp.addRoleFilterListener(new FilterListener());
        rmp.addSpecFilterListener(new FilterListener());
        rmp.addDropTargetListener(new MemberDropTargetListener());
        
        rmp.addAToTListener(new AToTListener());
        rmp.addAToSListener(new AToSListener());
        rmp.addAToOListener(new AToOListener());
        rmp.addTToAListener(new TToAListener());
        rmp.addTToSListener(new TToSListener());
        rmp.addTToOListener(new TToOListener());
        rmp.addSToAListener(new SToAListener());
        rmp.addSToTListener(new SToTListener());
        rmp.addSToOListener(new SToOListener());
        rmp.addOToAListener(new OToAListener());
        rmp.addOToTListener(new OToTListener());
        rmp.addOToSListener(new OToSListener());
        
        rmp.addAToDCListener(new AToTDCListener());
        rmp.addTToDCListener(new TToSDCListener());
        rmp.addSToDCListener(new SToODCListener());
        rmp.addOToDCListener(new OToADCListener());
        
        pec.addAcceptController(this);
        rmp.addPlayerAddListener(new MemberAddListener());
        rmp.addPlayerEditListener(new MemberEditListener());
        rmp.addPlayerRemoveListener(new MemberRemoveListener());
        
    }

    @Override
    public void updateView() {
        rmp.setContent(book.getActiveRaid());
        rmp.setEditable(book.checkFullEditable());
    }
    
    public void dropAction(DropTargetDropEvent dtde) {
        JTable[] memberLists= rmp.getPlayerLists();
        Transferable tr = dtde.getTransferable();
        if ( tr.isDataFlavorSupported (DataFlavor.stringFlavor) ){
            String[] names;
            try {
                names = ((String)tr.getTransferData(DataFlavor.stringFlavor)).split("\n");
            } catch (        UnsupportedFlavorException | InvalidDnDOperationException | IOException ex ) {
                names = new String[0];
            }
            JTable table = (JTable)(((DropTarget)dtde.getSource()).getComponent());
            Player.RaidState state;
            if(table.equals(memberLists[2])){
                state = Player.RaidState.ERSATZ;
            }else if(table.equals(memberLists[1])){
                state = Player.RaidState.TEILG;
            }else if(table.equals(memberLists[3])){
                state = Player.RaidState.ABGEMELDET;
            }else{
                state = Player.RaidState.ABWESEND;
            }
            setMembersToState(names,state);
            updateView();
        }
    }
    class MemberAcceptListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.acceptMember();
            updateView();
        }
    }
    class MemberAddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           if(e.getSource() instanceof JMenuItem){
               RaidState state = rmp.getMenuItemState((JMenuItem) e.getSource());
               pec.createMember(state);
           }
        }
    }
    class MemberEditListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           if(e.getSource() instanceof JMenuItem){
                RaidState state = rmp.getMenuItemState((JMenuItem) e.getSource());
                String memberName = rmp.getSelectedPlayerName(state);
                if(memberName!=null){
                    Raid raid = book.getActiveRaid();
                    pec.editMember(raid.getPlayerByName(memberName));
                }
            }
        }
    }
    class MemberRemoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           if(e.getSource() instanceof JMenuItem){
                RaidState state = rmp.getMenuItemState((JMenuItem) e.getSource());
                String memberName = rmp.getSelectedPlayerName(state);
                    pec.removePlayer(memberName);
                    updateView();  
           }
        }
    }
    
    class FilterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateView();
        }
    }
    
    class AToTDCListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)  {   
                JTable table = rmp.getPlayerLists()[0];
                toListenerAction(table, Player.RaidState.TEILG);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    
    class TToSDCListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)  {   
                JTable table = rmp.getPlayerLists()[1];
                toListenerAction(table, Player.RaidState.ERSATZ);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    
    class SToODCListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)  {   
                JTable table = rmp.getPlayerLists()[2];
                toListenerAction(table, Player.RaidState.ABGEMELDET);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    class OToADCListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)  {   
                JTable table = rmp.getPlayerLists()[3];
                toListenerAction(table, Player.RaidState.ABWESEND);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
    class AToTListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[0];
            toListenerAction(table, Player.RaidState.TEILG);
        }
    }
    class AToSListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[0];
            toListenerAction(table, Player.RaidState.ERSATZ);
        }
    }
    class AToOListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[0];
            toListenerAction(table, Player.RaidState.ABGEMELDET);
        }
    }
    class TToAListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[1];
            toListenerAction(table, Player.RaidState.ABWESEND);
        }
    }
    class TToSListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[1];
            toListenerAction(table, Player.RaidState.ERSATZ);
        }
    }
    class TToOListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[1];
            toListenerAction(table, Player.RaidState.ABGEMELDET);
        }
    }
    class SToAListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[2];
            toListenerAction(table, Player.RaidState.ABWESEND);
        }
    }
    class SToTListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[2];
            toListenerAction(table, Player.RaidState.TEILG);
        }
    }
    class SToOListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[2];
            toListenerAction(table, Player.RaidState.ABGEMELDET);
        }
    }
    class OToAListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[3];
            toListenerAction(table, Player.RaidState.ABWESEND);
        }
    }
    class OToTListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[3];
            toListenerAction(table, Player.RaidState.TEILG);
        }
    }
    class OToSListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = rmp.getPlayerLists()[3];
            toListenerAction(table, Player.RaidState.ERSATZ);
        }
    }
    
    private void toListenerAction(JTable from, Player.RaidState to){
        int[] indizies = from.getSelectedRows();
        String[] names = new String[indizies.length];
        for(int i=0;i<indizies.length;i++){
            names[i] = (String)from.getValueAt(indizies[i], 0);
        }
        setMembersToState(names,to);
        updateView();
    }
    
    private class MemberDropTargetListener implements DropTargetListener{

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            dropAction(dtde);
            updateView();
        }

    }
    
    private void setMembersToState(String[] names, Player.RaidState state){
        Raid raid = book.getActiveRaid();
        for(String name : names){
            if(!name.equals("")){
                Player member = raid.getPlayerByName(name);
                if(member.getState().equals(Player.RaidState.TEILG)){

                    if(!raid.isPlayerGetLoot(member)){
                        raid.getPlayerByName(name).setState(state);
                    }
                }else{
                    raid.getPlayerByName(name).setState(state);
                }
            }
        }
        
    }
}

