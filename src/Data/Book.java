/*
 * RiftSKS ist ein Programm zum führen eines Raidkaders mit der Absicht
 * das SKS-Verfahren bei der Lootverteilung anzuwenden.
 * Copyright (C) 2013  Dirk Evers
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package Data;

import Controller.Comparators.PlayerComparator;
import Controller.DateFormater;
import Exceptions.RaidAlreadyExistException;
import Exceptions.RaidDateInvalidException;
import File.BookForgeInterface;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class Book {
    private static final Logger logger = LogManager.getRootLogger();
    
    private HashMap<Date,Raid> raids;
    private LinkedList<Date> dates;
    private Date activeRaid;
    private BookForgeInterface forge;
    
    private HashMap<String,Player> players;
    private HashMap<String,Guild> guilds;
    
    private HashMap<String,String> lootLinks;
    
    public Book(){
        raids = new HashMap<>();
        dates = new LinkedList<>();
        players = new HashMap<>();
        guilds = new HashMap<>();
        lootLinks = new HashMap<>();
        Guild standartGuild = new Guild("-");
        guilds.put("-", standartGuild);
    }
    
    public void reset(){
        raids.clear();
        dates.clear();
        players.clear();
        lootLinks.clear();
        
        Guild standartGuild = guilds.get("-");
        guilds.clear();
        guilds.put("-", standartGuild);
        
        Raid newRaid = new Raid(new Date());
        Player newPlayer = new Player();
        newPlayer.setGuild(standartGuild);
        newPlayer.setSksPos(1);
        
        players.put(newPlayer.getId(), newPlayer);
        newRaid.addPlayer(newPlayer);
        activeRaid = newRaid.getDate();
        dates.add(activeRaid);
        raids.put(activeRaid, newRaid);
       System.out.print("");
    }

    public BookForgeInterface getForge() {
        return forge;
    }

    public void setForge(BookForgeInterface forge) {
        this.forge = forge;
    }
    
    public void addOverviewPlayer(Player player){
        if(player != null){
            if(!players.containsKey(player.getId())){
                players.put(player.getId(), player);
            }
        }
    }
    
    public Player getOverviewPlayer(String Id){
        return players.get(Id);
    }
    
    public void addGuild(Guild guild){
        guilds.put(guild.name, guild);
    }
    
    public Guild getGuild(String name){
        return guilds.get(name);
    }
    
    public LinkedList<Date> getRaidDates(){
         return dates;
    }
    
    public Raid getRaid(Date date) {
        return raids.get(date);
     }
    
    /**
     * gibt den neuesten Raid zurück
     * @return
     */
    public Date getNewestRaidDate(){
        return dates.getLast();
    }
    
    /**
     * gibt den gerade zu bearbeiteten Raid aus
     * @return der zubearbeitende Raid
     */
    public Raid getActiveRaid (){
        return raids.get(activeRaid);
    }
    
    /**
     * legt den gerade zu bearbeiteten Raid anhand des übergebenen
     * Datums fest
     * @param date Datum des zu bearbeitenden Raid's
     */
    public void setActiveRaid(Date date)throws IndexOutOfBoundsException{
        if(raids.containsKey(date)){
            activeRaid = date;
        }else{
            throw new IndexOutOfBoundsException("Fehler! Raid Datum ("+date+") existiert im Book nicht!");
        }
    }
    
    public void changeRaidDate(Raid raid, Date date) {
        dates.remove(raid.getDate());
        dates.add(date);
        Collections.sort(dates);
        raids.remove(raid.getDate());
        raid.setTimestamp(date);
        Raid xlsRaid = raid;
        raids.put(date, xlsRaid);
    }
    
    /**
     * entfernt den Raid mit dem Passenden Datum
     * @param raid
     */
    public void removeRaid(Raid raid){
        raids.remove(raid.getDate());
        dates.remove(raid.getDate());
    }
    
    public void addRaid(Raid raid){
        raids.put(raid.getDate(), raid);
        dates.add(raid.getDate());
        Collections.sort(dates);
    }
    
    /**
     * Gibt Die Daten aus, zwischen dem sich das Datum des Raids bewegen darf
     * @param raid min, max Datum des Bereichs
     * @return
     */
    public Date[] getValidDateRange(Raid raid){
        Date raidDate = raid.getDate();
        Date[] range = (new Date[2]);
        int i;
        for(i =0; i<dates.size(); i++){
            if(dates.get(i).equals(raidDate)){
                break;
            }
        }
         Calendar cal = new GregorianCalendar();
        if(i<dates.size()-1){
            cal.setTime(dates.get(i+1));
            cal.add(Calendar.DAY_OF_WEEK, -1);
            range[1] = cal.getTime();
        }else{
            range[1] = null;
        }
        if(i>0){
            cal.setTime(dates.get(i-1));
            cal.add(Calendar.DAY_OF_WEEK, +1);
            range[0] = cal.getTime();
        }else{
            range[0] = null;
        }
        return range;
    }
    
    public boolean checkFullEditable(){
        return dates.getLast().equals(activeRaid);
    }
    
    public LinkedList<LinkedList<Object>> getDatas(){
        LinkedList<LinkedList<Object>> datas = new LinkedList<>();
        Raid lastRaid = getRaid(getNewestRaidDate());
        String[] playerNames = players.keySet().toArray(new String[players.size()]);
        HashMap<String,Player> playersMap = new HashMap<>();
        // rename Players in Overview
        for(String name : playerNames){
            Player player = lastRaid.getPlayerByID(players.get(name).getId());
            if(player!=null){
                String currentName = player.getName();
                playersMap.put(currentName, player);
            }else{
                playersMap.put(name, players.get(name));
            }
        }
        playerNames = playersMap.keySet().toArray(new String[playersMap.size()]);
        // sort Playernames
        PlayerComparator mc = new PlayerComparator(playersMap);
        Arrays.sort(playerNames, mc);
        
        LinkedList<Raid> raidList = getRaids();
        for(String name : playerNames){
            LinkedList<Object> currentData = new LinkedList<>();
            datas.add(currentData);
            currentData.add(playersMap.get(name));
            currentData.add(getData(name,raidList));
        }
        return datas;
    
    }
    
    private LinkedList<Player.RaidState> getData(String playerName,LinkedList<Raid> raids){
        LinkedList<Player.RaidState> states = new LinkedList<>();
        for(Raid raid : raids){
                Player current = raid.getPlayerByName(playerName);
                if(current!=null){
                    states.add(current.getState());
                }else{
                    states.add(Player.RaidState.GELOESCHT);
                }
            }
        return states;
    }
    
    public LinkedList<Raid> getRaids(){
        LinkedList<Raid> raidList = new LinkedList<>();
        for(Date date : dates){
            raidList.add(getRaid(date));
        }
        return raidList;
        
    }

    public void getX() {
        logger.debug("Id-Liste:"+this.players.keySet().toString());
    }

    public String[] getGuildNames() {
        return guilds.keySet().toArray(new String[guilds.size()]);
    }

    public String[] getPlayerIDs() {
        return players.keySet().toArray(new String[players.size()]);
    }

    public void removeGuild(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //TODO spieler müssen noch Gildenlos werden!
        //guilds.remove(name);
    }

    public void clear() {
        raids = new HashMap<>();
        dates = new LinkedList<>();
        players = new HashMap<>();
        guilds = new HashMap<>();
        Guild standartGuild = new Guild("-");
        guilds.put("-", standartGuild);
    }
    

    public Raid newRaid(Date date) throws RaidAlreadyExistException, RaidDateInvalidException {
        String dateString = DateFormater.raidDateFormat.format(date);
        logger.info("Neuer Raid wird erstellt("+dateString+").");
        Raid existRaid = getRaid(date);
        if (existRaid != null) {
            throw new RaidAlreadyExistException("Raid:" + dateString + " existiert bereits!");
        }
        Date newestDate = getNewestRaidDate();
        if (newestDate != null) {
            if (!newestDate.before(date)) {
                throw new RaidDateInvalidException("Raid muss nach dem letzten Raid stattfinden!");
            }
        }

        Raid raid = new Raid(date);
        calcNewRaid(raid, getRaid(getNewestRaidDate()));
        addRaid(raid);
        return raid;
    }

    private void calcNewRaid(Raid newRaid, Raid lastRaid) {
        logger.debug("Neuer Raid wird aus \""+DateFormater.raidDateFormat.format(lastRaid.getDate())+"\" gefüllt.");
        LinkedList<Player> oldPlayers = lastRaid.getNextSKS();
        for (int i = 0; i < oldPlayers.size(); i++) {
            logger.debug("Spieler \""+oldPlayers.get(i).getName()+"\" wird portiert.");
            Player current = new Player (oldPlayers.get(i));
            newRaid.addPlayer(current);
            current.setSksPos(i + 1);
            if (current.getState().equals(Player.RaidState.TEILG)) {
                current.setRaidCount((current.getRaidCount() + 1));
            } else {
                current.setRaidCount((current.getRaidCount()));
            }
            current.setState(Player.RaidState.ABWESEND);
        }
    }

    public void clearOverviewPlayers() {
        players.clear();
    }

    public HashMap<String, String> getLootLinks() {
        return lootLinks;
    }

    public void setLootLinks(HashMap<String, String> lootLinks) {
        this.lootLinks = lootLinks;
    }
    
    public String getLootLink(String name){
        return lootLinks.get(name);
    }
    
    public void addLootLink(String name , String link) {
        lootLinks.put(name, link);
    }
    
    public void clearLootLinks(){
        this.lootLinks.clear();
    }
}
