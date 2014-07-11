/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Raid;

import Controller.ControllerInterface;
import Controller.DateFormater;
import Controller.Edit.PlayerEditController;
import Data.Book;
import Data.Loot;
import Data.Loot.LootState;
import Data.Player;
import Data.Player.RaidState;
import Data.Raid;
import GUI.Raid.ServingPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class ServingController implements ControllerInterface {
    private static final Logger logger = LogManager.getRootLogger();
    private Book book;
    private HashMap<String,HashMap<String,Integer>> lootMap;
    private ServingPanel lp;
    private PlayerEditController pec;
    private boolean editable;
    
    Date currentLootDate;
    
    public ServingController(ServingPanel lp, PlayerEditController mec, Book book){
        this.book = book;
        this.lp = lp;
        currentLootDate = null;
        this.pec = mec;
        setListener();
        editable = true;
    }
    
    private void setListener(){
        FilterListener fl = new FilterListener();
        lp.addRoleFilterListener(fl);
        lp.addSpecFilterListener(fl);
        
        lp.addMultiSpecFilterListener(new MultiFilterListener());
        lp.addTankMultiSpecFilterListener(new TankMultiFilterListener());
        
        lp.addLootTableListener(new LootTableListener());
        lp.addLootTableMouseListener(new LootMouseListener());
        lp.addLootNameListener(new LootNameListener());
        
        lp.addCreateLootListener(new CreateLootListener());
        lp.addChangeLootListener(new ChangeLootListener());
        lp.addRemoveLootListener(new RemoveLootListener());
        
        pec.addAcceptController(this);
        lp.addPlayerAddListener(new MemberAddListener());
        lp.addPlayerEditListener(new MemberEditListener());
        lp.addPlayerRemoveListener(new MemberRemoveListener());
    }
    
    class FilterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateMemberList();
            lp.setItemCounts(getLootCounts(lp.getLootName()));
        }
    }
    
    class MultiFilterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lp.multiFilterAction();
            updateMemberList();
            lp.setItemCounts(getLootCounts(lp.getLootName()));
        }
    }
    class TankMultiFilterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            lp.tankMultiFilterAction();
            updateMemberList();
            lp.setItemCounts(getLootCounts(lp.getLootName()));
        }
    }
    
    class MemberAcceptListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.acceptMember();
            updateMemberList();
        }
    }
    
    class MemberAddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.createMember(RaidState.TEILG);
        }
    }
    class LootMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {
            HashMap <Date,Boolean> map = lp.getLootStateMap();
            Raid raid = book.getActiveRaid();
            Boolean changed = false;
            for(Date date: map.keySet()){
                Loot loot = raid.getLoot(date);
                if(loot!=null){
                    if(map.get(date)){
                        if(editable){
                            if(loot.getState().equals(LootState.ELSE)){
                                loot.setState(Loot.LootState.RAID);
                                changed = true;
                            }
                        }else{
                            if(loot.getState().equals(LootState.ELSE)){
                                changed = true;
                            }
                        }
                    }else{
                        if(editable){
                            if(loot.getState().equals(LootState.RAID)){
                                loot.setState(Loot.LootState.ELSE);
                                changed = true;
                            }
                        }else{
                            if(loot.getState().equals(LootState.RAID)){
                                changed = true;
                            }
                        }
                    }
                }
            }
            if(changed){
                updateLootList();
                updateMemberList();
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }
                        
//                  if(map.get(date)){
//                        if(loot.getState().equals(LootState.ELSE)){
//                            loot.setState(Loot.LootState.RAID);
//                            updateLootList();
//                            updateMemberList();
//                        }
//                    }else{
//                        if(loot.getState().equals(LootState.RAID)){
//                            loot.setState(Loot.LootState.ELSE);
//                            updateLootList();
//                            updateMemberList();
//                        }
//                    }      
                        
    class MemberRemoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String member = lp.getSelectedPlayerName();
            if(member!=null){
                JFrame frame = new JFrame("ChangeDialog");
                int n = JOptionPane.showConfirmDialog(
                    frame,
                    "Wirklich den Teilnehmer "+member+" löschen?\n Er wird dann vollständig aus dem Kader entfernt!"
                    ,
                    "Teilnehmer löschen",
                    JOptionPane.YES_NO_OPTION);
                if(n==0){
                        book.getActiveRaid().removePlayer(member);
                        updateMemberList();
                }
            }
        }
    }
    class MemberEditListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String memberName = lp.getSelectedPlayerName();
            if(memberName!=null){
                Raid raid = book.getActiveRaid();
                pec.editMember(raid.getPlayerByName(memberName));
            }
        }
    }
    
    class RemoveLootListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("ChangeDialog");
            Loot loot = book.getActiveRaid().getLoot(currentLootDate);
            if(loot!=null){
                int n = JOptionPane.showConfirmDialog(
                    frame,
                    "Wirklich den Loot löschen?\n"+
                        loot.getPlayer().getName()+" , "+loot.getDescription()+" , "+DateFormater.lootDateFormat.format(currentLootDate)
                    ,
                    "Loot löschen",
                    JOptionPane.YES_NO_OPTION);
                if(n==0){
                        book.getActiveRaid().removeLoot(currentLootDate);
                        currentLootDate = null;
                        updateLootList();
                        updateMemberList();
                        updateLootContent();
                }
            }
        }
    }
    
    class CreateLootListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            currentLootDate = null;

            calcLootCounts();
            updateLootList();
            updateLootContent();
            lp.setItemCounts(getLootCounts(lp.getLootName()));      
        }
    }
    
    class ChangeLootListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Date date = lp.getLootDate();
            if(date==null){
                JFrame frame = new JFrame("");
                JOptionPane.showMessageDialog(
                        frame,
                        "Das eingegebene Loot-Datum ist ungültig!\n"+
                        "Nötig ist HH.MM.SS\n"+
                        "Das Datum wird grün wenn gültig.",
                        "Ungültiges Loot-Datum!",
                        JOptionPane.WARNING_MESSAGE);
            }else{
                Raid raid= book.getActiveRaid();
                if(raid.getLoot(date)!=null&&(!date.equals(currentLootDate))){
                    JFrame frame = new JFrame("");
                    JOptionPane.showMessageDialog(
                            frame,
                            "Das angegebene Datum wird bereits verwendet!\n"+
                            "Es kann immer nur ein Loot zum gleichen Datum geben",
                            "Loot zum Datum bereits vorhanden!",
                            JOptionPane.WARNING_MESSAGE);
                }else{
                    String memberName = lp.getSelectedPlayer();
                    Player member = raid.getPlayerByName(memberName);
                    if(member==null||member.getState()!=RaidState.TEILG){
                        JFrame frame = new JFrame("");
                        JOptionPane.showMessageDialog(
                                frame,
                                "Es wurde kein gültiger Spieler ausgewählt!",
                                "Ungültiger Spieler!",
                                JOptionPane.WARNING_MESSAGE);
                    }else{
                        if (currentLootDate!=null){
                            Loot loot = raid.getLoot(currentLootDate);
                            logger.info("zuänderner Loot("+DateFormater.lootDateFormat.format(loot.getTimestamp())+"):"+loot);
                            if(!loot.getTimestamp().equals(date)&&!loot.getDescription().equals(lp.getName())&&!loot.getPlayer().equals(member)){
                                JFrame frame = new JFrame("");
                                int n = JOptionPane.showConfirmDialog(
                                frame,
                                "Es wurden alle Informationen geändert!\n Soll ein neuer Loot angelegt werden?\n"
                                ,
                                "Loot erstellen vs. Loot ändern",
                                JOptionPane.YES_NO_OPTION);
                                if(n==0){
                                    loot = new Loot(date,member, lp.getLootName());
                                    raid.addLoot(loot);
                                }else{
                                    if(!loot.getTimestamp().equals(date)){
                                        raid.removeLoot(currentLootDate);
                                        loot.setTimestamp(date);
                                        currentLootDate = date;
                                        raid.addLoot(loot);
                                    }
                                    loot.setDescription(lp.getLootName());
                                    loot.setPlayer(member);
                                }
                            }else{
                                if(!loot.getTimestamp().equals(date)){
                                    raid.removeLoot(currentLootDate);
                                    loot.setTimestamp(date);
                                    currentLootDate = date;
                                    raid.addLoot(loot);
                                }
                                loot.setDescription(lp.getLootName());
                                loot.setPlayer(member);
                            }
                        }else{
                            Loot loot = new Loot(date,member, lp.getLootName());
                            raid.addLoot(loot);
                            
                        }
                        calcLootCounts();
                        updateLootList();
                        updateLootContent();
                        updateMemberList();
                        lp.setItemCounts(getLootCounts(lp.getLootName()));
                        lp.setSelectedItem(date);
                    }
                }
            }
        }
    }
    
    class LootNameListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {
            lp.setItemCounts(getLootCounts(lp.getLootName()));
        }

    }
    
    class LootTableListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int val =e.getLastIndex();
            Date date =lp.getSelectedLoot();
            
            if(date!=null&&!(date.equals(currentLootDate))){
                currentLootDate = date;
                //warum ensteht hierdurch eine endlosschleife?
                //vorallem, weil alle println sagen, dass hier garnicht hinkommt
                updateLootContent();
                 lp.setItemCounts(getLootCounts(lp.getLootName()));
            }else{
            }
        }
    }
    
    public void updateView(){
        editable = book.checkFullEditable();
        lp.setEditable(editable);
        
        if(book.getActiveRaid()!=null){
            currentLootDate = null;
            calcLootCounts();
            updateLootList();
            updateMemberList();
            updateLootContent();
        }
//        Raid activeRaid = book.getActiveRaid();
//        Date currentDate = (currentRaid!=null)?currentRaid.getDate():null;
//        Date activeDate = (activeRaid!=null)?activeRaid.getDate():null;
//        if((activeDate!=null)&&!(activeDate.equals(currentDate))){
//            calcLootCounts();
//            currentRaid = activeRaid;
//            currentLootDate = null;
//            lp.clearSelectedItem();
//        }
//        
    }
    
    private void updateLootList(){
        Date[] lootDates =book.getActiveRaid().getlootDates();
        LinkedList<Loot> lootContent = new LinkedList<>();
        int li = 0;
        for(Date date : lootDates){
            Loot loot = book.getActiveRaid().getLoot(date);
            lootContent.add(loot);
        }
        lp.setLootTable(lootContent);
    }
    
    private void updateLootContent(){
        Raid raid = book.getActiveRaid();
        
        if(currentLootDate==null){
            lp.clearSelectedItem();
        }else{
            Loot loot = raid.getLoot(currentLootDate);
            lp.setSelectedItem(loot.getDescription(), loot.getPlayer().getName(), loot.getTimestamp());
            HashMap<String,Integer> counts = getLootCounts(loot.getDescription());
            logger.debug("Neue Lootliste:"+counts);
        }
    }
    
    private void updateMemberList(){
        
        LinkedList<Player> filteredMember = new LinkedList<>();
            LinkedList<Player> players = book.getActiveRaid().getNextSKS();
        for(Player member : players){
            if(member.getState().equals(RaidState.TEILG)){
                filteredMember.add(member);
            }
        }
        lp.setPlayerTable(filteredMember);
    }
    
    private HashMap<String,Integer> getLootCounts(String itemName){
        if(lootMap.containsKey(itemName.toLowerCase())){
            return lootMap.get(itemName.toLowerCase());
        }else{
            return null;
        }
    }
    
    private void calcLootCounts(){
        HashMap<String,HashMap<String,Integer>> om = new HashMap<>();
        for(Date raidDate:book.getRaidDates()){
            Raid raid = book.getRaid(raidDate);
            for(Date lootDate: raid.getlootDates()){
                Loot loot = raid.getLoot(lootDate);
                if(!om.containsKey(loot.getDescription().toLowerCase())){
                    om.put(loot.getDescription().toLowerCase(),  new HashMap<String,Integer>());
                }
                //--------------
                HashMap<String,Integer> im = om.get(loot.getDescription().toLowerCase());
                    String memberName = raid.getPlayerByID(loot.getPlayer().getId()).getName();
                    
                    if(im.containsKey(memberName)){
                        int i =im.get(memberName)+1;
                        im.remove(memberName);
                        im.put(memberName, i);
                    }else{
                        im.put(memberName, 1);
                    }
            }
        }
    lootMap = om;
    }
}
