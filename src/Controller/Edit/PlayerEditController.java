/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Edit;

import Controller.ControllerInterface;
import Data.Book;
import Data.Guild;
import Data.Player;
import Data.Player.*;
import Data.Raid;
import GUI.Edit.PlayerEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Dirk
 */
public class PlayerEditController implements ControllerInterface {
    Player player;
    RaidState state;
    Book book;
    PlayerEditFrame pef;
    Boolean editMode;
    HashMap<String,Player> previewPlayers;
    HashMap<String,Player> existingPlayers;
    
    AcceptListener ac;

    public PlayerEditController(Book book) {
        this.book = book;
        this.pef = new PlayerEditFrame();
        previewPlayers = new HashMap<>();
        existingPlayers = new HashMap<>();
        addListener();
    }  
    
    private void addListener(){
        pef.addCloseButtonListener(new CloseListener());
        pef.addNameListener(new NameListener());
        ac = new AcceptListener();
        pef.addAcceptListener(ac);
    }

    @Override
    public void updateView(){
        if(player!=null){
            pef.setContent(player.getGuild().getName(),player.getCalling(),
                    player.getSpecMain(),player.getSpecsSecond());
        }
    }
    
    public void createMember(){
        createMember(RaidState.ABWESEND);
    }
        
    public void createMember(RaidState state){
        editMode = false;
        this.state = state;
        
        String[] guildNames = (book.getGuildNames());
        Arrays.sort(guildNames);
        pef.setGuildList(guildNames);
        
        String[] ids = book.getPlayerIDs();
        LinkedList<String>oldNames = new LinkedList<>();
        previewPlayers.clear();
        existingPlayers.clear();
        for(String id : ids){
            Raid raid = book.getRaid(book.getNewestRaidDate());
            Player current = raid.getPlayerByID(id);
            
            if(current==null){
                boolean found = false;
                LinkedList<Date> dates = book.getRaidDates();
                for(int i =1; i<dates.size()&&!found;i++){
                    raid = book.getRaid(dates.get(i));
                    current = raid.getPlayerByID(id);
                    if(current!=null){
                        found = true;
                        oldNames.add(current.getName());
                        previewPlayers.put(current.getName(), current);
                    }
                }
            }else{
                existingPlayers.put(current.getName(), current);
            }
        }
        
        Collections.sort(oldNames);
        
        Player newPlayer = new Player();
        newPlayer.setName("neuer Spieler");
        
        oldNames.addFirst(newPlayer.getName());
        previewPlayers.put(newPlayer.getName(), newPlayer);
        
        pef.setMemberList(oldNames);
        player = null;
        pef.setVisible(true);
    }
    
    public void editMember(Player member){
        if(member!=null){
            editMode = true;
            previewPlayers.clear();
            previewPlayers.put(member.getName(), member);
            
            existingPlayers.clear();
            Raid raid = book.getRaid(book.getNewestRaidDate());
            for(String playerName: raid.getPlayersNames()){
                if(!playerName.equals(member.getName())){
                    existingPlayers.put(playerName, raid.getPlayerByName(playerName));
                }
            }
            String[] guildNames = (book.getGuildNames());
            Arrays.sort(guildNames);
            pef.setGuildList(guildNames);

            LinkedList<String> memberList = new LinkedList<>();
            memberList.add(member.getName());
            pef.setMemberList(memberList);
            this.player = member;
            pef.setVisible(true);
        }
    }
    
    public void addAcceptController(ControllerInterface ci){
        ac.addController(ci);
    }
    
    private class CloseListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pef.setVisible(false);
        }
    }
    
    private class NameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = pef.getSelectedMemberName();
            if(previewPlayers.containsKey(name)){
                player = new Player(previewPlayers.get(name));
                String guildName = "";
                if(player.getGuild()!=null){
                    guildName = player.getGuild().getName();
                }
                pef.setContent(guildName, player.getCalling(), player.getSpecMain(), player.getSpecsSecond());
            }else{
                if(!editMode){
                    player = null;
                }
            }
        }
    }
    
    public void acceptMember(){
        String name = pef.getSelectedMemberName();
        if(existingPlayers.containsKey(name)){
            JFrame frame = new JFrame("ChangeDialog");
                JOptionPane.showMessageDialog(
                frame,
                "Der Name "+name+" ist bereits im Raid vorhanden",
                "Spielername bereits vergeben!",
                JOptionPane.WARNING_MESSAGE);
        }else if(name.equals("")){
            JFrame frame = new JFrame("ChangeDialog");
                JOptionPane.showMessageDialog(
                frame,
                "Der Name "+name+" ist ungültig",
                "Ungültiger Spielername!",
                JOptionPane.WARNING_MESSAGE);
        }else{
            if(player==null||player.getName().equals("neuer Spieler")){
                    player = new Player();
                    player.setSksPos(book.getActiveRaid().getPlayersNames().size()+1);
                    player.setGuild(book.getGuild("-"));
                }
                if(state!=null){
                    player.setState(state);
                }

                Raid raid = book.getActiveRaid();
                Guild guild = book.getGuild(pef.getSelectedGuildName());
                Calling role = pef.getSelectedRole();
                Spec mainSpec = pef.getSelectedMainSpec();
                LinkedList<Spec> secSpec = pef.getSelectedSecSpecs();
                player.setName(name);
                player.setGuild(guild);
                player.setCalling(role);
                player.setSpecMain(mainSpec);
                player.setSpecsSecond(secSpec);

                if((!editMode)&&pef.getResMode()&&previewPlayers.containsKey(name)){
                    int skspos =raid.getPlayersNames().size();
                    player.setSksPos(skspos+1);
                }
                raid.setPlayer(player);
                book.addOverviewPlayer(player);

                pef.setVisible(false);
        }
    }
    
    class AcceptListener implements ActionListener{
        LinkedList<ControllerInterface>controller;
        public AcceptListener(){
            super();
            controller = new LinkedList<>();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            acceptMember();
            for(ControllerInterface ci :controller){
                ci.updateView();
            }
        }
        
        public void addController(ControllerInterface ci){
            controller.add(ci);
        }
    }
    
    public void removePlayer(String name) {
        JFrame frame = new JFrame("ChangeDialog");
        if(name!=null){
            if(book.getRaid(book.getNewestRaidDate()).getPlayersNames().size()<=1){
                    JOptionPane.showMessageDialog(
                    frame,
                    "Es muss mindestens ein Spieler im Kader sein!",
                    "Minimale Spieleranzahl wird unterschritten!",
                    JOptionPane.WARNING_MESSAGE);
            }else{
                Player delPlayer = book.getRaid(book.getNewestRaidDate()).getPlayerByName(name);
                if(delPlayer!=null){
                    int n = JOptionPane.showConfirmDialog(
                        frame,
                        "Wirklich den Teilnehmer "+name+" löschen?\n Er wird dann vollständig aus dem Kader entfernt!"
                        ,
                        "Teilnehmer löschen",
                        JOptionPane.YES_NO_OPTION);
                    if(n==0){
                            book.getActiveRaid().removePlayer(name);
                    }
                }
            }
        }
    }
        
}
