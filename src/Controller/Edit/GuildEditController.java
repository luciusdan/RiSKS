/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Edit;

import Controller.ControllerInterface;
import Data.Book;
import Data.Guild;
import Data.Player;
import Data.Raid;
import GUI.Edit.GuildEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Dirk
 */
public final class GuildEditController implements ControllerInterface {
    Book book;
    GuildEditFrame gf;
    Guild guild;
    
    public GuildEditController(Book book) {
        this.book = book;
        this.gf = new GuildEditFrame();
        addListener();
    }

    private void addListener(){
        gf.addTableListener(new LootTableListener());
        gf.addEditListener(new EditListener());
        gf.addAddListener(new AddListener());
    }
    
    public void showFrame() {
        this.updateView();
        gf.setVisible(true);
    }
    
    class LootTableListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            String name =gf.getSelectedGuildName();
            if(name!=null){
                gf.setGuildName(name);
                guild = book.getGuild(name);
            }else{
                gf.setGuildName("");
                guild = null;
            }
        }
    }
    
    class AddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = gf.getGuildName();
            Guild guild = new Guild(str);
            book.addGuild(guild);
            updateView();
        }
    }
    
    class EditListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = gf.getGuildName();
            guild.setName(str);
            updateView();
        }
    }
    
    public void updateView(){
        LinkedList<Object[]> datas = new LinkedList<>();
        HashMap<String, Integer[]> map = new HashMap<>();
        for(String name :book.getGuildNames()){
            Integer[] values = {0,0};
            map.put(name, values);
        }
        for(String id: book.getPlayerIDs()){
            Player member = book.getOverviewPlayer(id);
            String guildName = member.getGuild().getName();
            Integer[] values = map.get(guildName);
            values[1]++;
        }
        Raid raid = book.getRaid(book.getNewestRaidDate());
        for(String id: raid.getPlayerIds()){
            Player member = raid.getPlayerByID(id);
            String guildName = member.getGuild().getName();
            Integer[] values = map.get(guildName);
            values[0]++;
        }
        for(String name: map.keySet()){
            Integer[] values = map.get(name);
            Object[] data = {name , values[0] , values[1]};
            datas.add(data);
        }
        gf.setContent(datas);
    }
}
