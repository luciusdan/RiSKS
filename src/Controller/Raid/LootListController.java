/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Raid;

import Controller.ControllerInterface;
import Data.Book;
import Controller.DateFormater;
import Data.Loot;
import Data.Raid;
import GUI.Raid.SKSLootFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.HashMap;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Dirk
 */
public class LootListController implements ControllerInterface {
    
    Book book;
    SKSLootFrame slf;
    HashMap<String,String> linkMap;
    
    public LootListController(Book book){ 
        this.slf = new SKSLootFrame();
        this.book = book;
        this.linkMap = new HashMap<>();
        slf.addFormatListener(new FormatListener());
        slf.addChangeListener(new TabChangeListener());
        slf.addFrameListener(new LootFrameListener());
    }
    
    public void showFrame(){
        updateView();
        slf.setVisible(true);
    }
    
    public void updateView(){
         updateLinks();
         updateText();
    }
    
    private void updateText(){
        Raid raid = book.getActiveRaid();
            String content="Loot vom "+DateFormater.raidDateFormat.format(raid.getDate())+":\n\n";
            for(Date date : raid.getlootDates()){
                Loot loot = raid.getLoot(date);
                if(slf.getFormatButtonState()){
                content += loot.getDescription()+" - "+loot.getPlayer().getName()+"\n";
                }else{
                    String link = book.getLootLink(loot.getDescription());
                    if(link!=null&&!link.equals("")){
                        content += "[url='"+link+"']"+loot.getDescription()+"[/url] - "+loot.getPlayer().getName()+"\n";
                    }else{
                        content += loot.getDescription()+" - "+loot.getPlayer().getName()+"\n";
                    }
                }
            }
            content += "\n Und die SKS-Liste.";
            slf.setText(content);
    }
    
    private void updateLinks(){
        Raid raid = book.getActiveRaid();
        linkMap.clear();
        for(Date date : raid.getlootDates()){
            Loot loot = raid.getLoot(date);
            String lootName = loot.getDescription();
            String link = book.getLootLink(lootName);
            if(link!=null){
                linkMap.put(lootName,link);
            }else{
                linkMap.put(lootName,"");
            }
        }
            slf.setList(linkMap);
    }
    
    private void readLinks(){
        HashMap<String,String> map = slf.getLinks();
        for(String key:linkMap.keySet()){
            if(map.containsKey(key)){
                book.addLootLink(key, map.get(key));
            }
        }
    }
    
    class FormatListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            updateText();
        }
    }
    class TabChangeListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            readLinks();
            updateLinks();
            updateText();
        }
    }
    
    class LootFrameListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {}
        @Override
        public void windowClosing(WindowEvent e) {
            readLinks();
            
        }
        @Override
        public void windowClosed(WindowEvent e) {}
        @Override
        public void windowIconified(WindowEvent e) {}
        @Override
        public void windowDeiconified(WindowEvent e) {}
        @Override
        public void windowActivated(WindowEvent e) {}
        @Override
        public void windowDeactivated(WindowEvent e) {}
    }
}
