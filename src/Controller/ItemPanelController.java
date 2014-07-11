/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Data.Book;
import Data.Loot;
import Data.Player;
import Data.Raid;
import GUI.Items.ItemOverviewPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JToggleButton;

/**
 *
 * @author Dirk
 */
public class ItemPanelController implements ControllerInterface {
    ItemOverviewPanel iP;
    Book book;
    boolean showMode;
    boolean showOldPlayer;
    
    public ItemPanelController(ItemOverviewPanel ip, Book book){
        this.book = book;
        this.iP = ip;
        showMode = false;
        showOldPlayer = false;
        addListener();
    }
    
    private void addListener(){
        iP.addRefreshListener(new RefreshListener());
        iP.addFilterListener(new FilterListener());
        iP.addShowModeListener(new ShowModeListener());
        iP.addAllPlayerListener(new AllPlayerListener());
    }
    
    class RefreshListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateView();
        }
    }
    
    class FilterListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            updateView();
        }
    }
    
    class ShowModeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            showMode = iP.getShowMode();
            updateView();
        }
    }
    
    class AllPlayerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton jtb = (JToggleButton) e.getSource();
            showOldPlayer = jtb.isSelected();
            updateView();
        }
    }
    
    public void updateView(){
        
        HashMap<String,HashMap<String,Content>> outerHash = new HashMap<>();
        HashMap<String,Player> lootMembers = new HashMap<>();
        Raid newestRaid =book.getRaid(book.getNewestRaidDate());
        
        for(Raid raid: book.getRaids()){
            for(Date date : raid.getlootDates()){
                Loot loot= raid.getLoot(date);
                //entweder ist der Filter nicht Aktiv oder der Spieler der Loot bekommen hat existiert noch im aktuellsten Raid
                Player lootMember = newestRaid.getPlayerByID(loot.getPlayer().getId());
                if(showOldPlayer&&lootMember==null){
                    lootMember = loot.getPlayer();
                }
                if(lootMember!=null){
                    lootMembers.put(lootMember.getName(), lootMember);
                    //showmode false == spieler zuerst ; showmode true == loot zuerst ;
                    if(showMode){
                        HashMap<String,Content> innerHash;
                        if(outerHash.containsKey(loot.getDescription())){
                            innerHash = outerHash.get(loot.getDescription());
                        }else{
                            innerHash = new HashMap<>();
                            outerHash.put(loot.getDescription(), innerHash);
                        }
                        Content cont = innerHash.get(lootMember.getName());
                        if(cont!=null){
                            cont.addCount();
                            cont.valDate(raid.getDate());
                        }else{
                            innerHash.put(lootMember.getName(), new Content(raid.getDate()));
                        }
                    }else{
                        HashMap<String,Content> innerHash;
                        if(outerHash.containsKey(loot.getPlayer().getName())){
                            innerHash = outerHash.get(lootMember.getName());
                        }else{
                            innerHash = new HashMap<>();
                            outerHash.put(lootMember.getName(), innerHash);
                        }
                        Content cont = innerHash.get(loot.getDescription());
                        if(cont!=null){
                            cont.addCount();
                            cont.valDate(raid.getDate());
                        }else{
                            innerHash.put(loot.getDescription(), new Content(raid.getDate()));
                        }
                    }
                }
            }
        }
        
        
            //create LinkedList with HashTables
            LinkedList<LinkedList<Object>> content = new LinkedList<>();
            for(String outerName : outerHash.keySet()){
                HashMap<String,Content> innerHash = outerHash.get(outerName);
                for(String innerName : innerHash.keySet()){
                    Content cont = innerHash.get(innerName);
                    LinkedList<Object> items= new LinkedList<>();
                    items.add(outerName);
                    items.add(innerName);
                    items.add(cont.getCount());
                    items.add(cont.getDate());
                    Player member = lootMembers.get(outerName);
                    if(member==null){
                        member = lootMembers.get(innerName);
                    }
                    items.add(member);
                    //items.add(innerName+" ("+cont.getCount()+"):"+dateFormat.format(cont.getDate()));
                    content.add(items);
                }
            }
            iP.setContent(content);
    }
    
    private class Content{
        private int count;
        private Date date;
        public Content(Date date){
            this.count = 1;
            this.date = date;
        }

        public int getCount() {
            return count;
        }

        public void addCount() {
            count++;
        }

        public Date getDate() {
            return date;
        }
        
        public void valDate(Date date){
            if(date.after(this.date)){
                this.date = date;
            }
        }
    }
}
