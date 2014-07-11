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

import Controller.Comparators.DateComparator;
import Controller.Comparators.SKSComparator;
import Data.Loot.LootState;
import Data.Player.RaidState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Objekt welches einen Raid repräsentiert.
 * Dabei wird hauptsächlich eine Beziehung zwischen
 * <p> einer Uhrzeit
 * <p> mehreren Playern (Anwesend,Ersatz,Abwesend)
 * <p> mehreren Loot's
 * gezogen.
 * @author Dirk
 */
public class Raid {

    //Enum für den Raid-Ort
    public enum Place{
        EE,FS,IG,PBB,ELSE
    }
    
    //Variablen für den Spieler
    private Date timestamp;
    private String comment;
    private ArrayList<Place> places;
    
    private HashMap<String, Player> playersByID;
    private HashMap<String, Player> playersByName;
    private LinkedList<Player> playersBySKS;
    
    private HashMap<Date, Loot> lootList;

    /**
     * Konstruktor
     * @param timestamp Datum des Raid's.
     */
    public Raid(Date timestamp) {
        this.timestamp = timestamp;
        this.comment = "";
        this.places = new ArrayList<>();

        this.playersByID = new HashMap<>();
        this.playersByName = new HashMap<>();
        this.playersBySKS = new LinkedList<>();
        this.lootList = new HashMap<>();
    }

    
    /**
     * Fügt ein Loot dem Raid hinzu, dadurch wird ebenso die SKS-Liste neu
     * berechnet.
     * @param loot der hinzuzufügende Loot.
     */
    public void addLoot(Loot loot) {
        if(loot!=null){
            this.lootList.put(loot.getTimestamp(), loot);
            validateLoot(loot);
        }
    }
    
    /**
     * Fügt einen Spieler dem Raid hinzu.
     * @param player der hinzuzufügende Spieler
     */
    public void addPlayer(Player player) {
        if(player !=null){
            playersByName.put(player.getName(), player);
            playersByID.put(player.getId(), player);
        }
    }
    
    /**
     * Gibt den Kommentar Textblock zum Raid wieder.
     * @return String des Kommentars.
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * Gibt das Datum des Raid's zurück.
     * @return Datum des Raid's.
     */
    public Date getDate() {
        return timestamp;
    }

    /**
     * Gibt den Loot zurück der im Raid zu dem Zeitpunkt vergeben wurde,
     * falls vorhanden, ansonnsten null.
     * @param timestamp Uhrzeit zu der der Loot ausgegeben wurde.
     * @return 
     */
    public Loot getLoot(Date timestamp) {
        return lootList.get(timestamp);
    }
    
    /**
     * Gibt den passenden Spieler zur Id wieder, falls die Id bei diesem Raid
     * verwendet wird.
     * @param id des gesuchten Spielers.
     * @return der gesuchte Spieler.
     */
    public Player getPlayerByID(String id) {
        return playersByID.get(id);
    }
    
    /**
     * Gibt den passenden Spieler zum Namen wieder, falls der Name bei diesem
     * Raid verwendet wird.
     * @param name des gesuchten Spielers.
     * @return der gesuchte Spieler.
     */
    public Player getPlayerByName(String name) {
        return playersByName.get(name);
    }   
    
    /**
     * Gibt die Idliste der Spieler zurück, die während dieses Raid's im Kader
     * sind.
     * @return die Idliste.
     */
    public LinkedList<String> getPlayerIds() {
        LinkedList<String> result = new LinkedList<>();
        result.addAll(playersByID.keySet());
        return result;
    }
    
    /**
     * Gibt die Namensliste der Spieler zurück, die während dieses Raid's im
     * Kader sind.
     * @return die Namensliste.
     */
    public LinkedList<String> getPlayersNames() {
        LinkedList<String> result = new LinkedList<>();
        result.addAll(playersByName.keySet());
        return result;
    }
    
    /**
     * Gibt die SKS-Liste zurück, die sich auf alle Ereignisse des Raid's
     * bildet. 
     * @return die abschließende SKS-Liste.
     */
    public LinkedList<Player> getNextSKS() {
        //finish local SKS 
        recalcSKS();
        //get DataPointers 
        LinkedList<String> names = getPlayersNames();
        Player[] newSKS = new Player[names.size()];
        //loop over all Players 
        for (int i = 0; i < names.size(); i++) {
            Player player = getPlayerByName(names.get(i));
            if (!((player.getState().equals(RaidState.TEILG))||(player.getState().equals(RaidState.ERSATZ)))) {
                newSKS[player.getSksPos() - 1] = player;
            }
        }
        int localSKS = 0; //localSKSPointer
        if(playersBySKS.size()>0){
            for (int i = 0; i < names.size(); i++) {
                if (newSKS[i] == null) {
                    newSKS[i] =  playersBySKS.get(localSKS);
                    localSKS++;
                }
            }
        }
        return new LinkedList<>(Arrays.asList(newSKS));
    }
    
    /**
     * Gibt den Ort des Raid's zurück.
     * @return Ort des Raids.
     */
    public ArrayList<Place> getPlaces() {
        return (ArrayList<Place>) places.clone();
    }
    /**
     * Gibt das Küzel des Übergebenen Ortes zurück.
     * @param place Der Ort.
     * @return Das Kürzel des Ortes.
     */
    public static String getPlaceAlias(Place place) {
        if(place!=null){
            switch(place){
                case EE : return "EE";
                case FS   : return "FS";
                case IG : return "IG";
                case PBB   : return "PBB";
                default: return "-";
            }
        }else{
            return "-";
        }
    }
    
    /**
     * Gibt den Ort zu dem Kürzel wieder.
     * @param place das Kürzel zu einem Raidort.
     * @return Passender Ort.
     */
    public static Place getPlace(String place) {
        switch(place.toLowerCase()){
            case "ee"  : return Place.EE;
            case "fs"  : return Place.FS;
            case "ig"  : return Place.IG;
            case "pb"  :
            case "pbb" : return Place.PBB;
            default: return null;
        }
    }
    
    /**
     * Gibt den Namen des übergebenen Ortes wieder.
     * @param place Ort.
     * @return String als Name des Ortes.
     */
    public static String getPlaceName(Place place) {
        switch(place){
            case EE : return "Endlose Sonnenfinsterniss";
            case FS   : return "Froststurm";
            case IG : return "Tor der Unendlichkeit";
            case PBB   : return "Ebenenbrecher-Bastion";
            default: return "undefiniert";
        }
    }
    
    /**
     * Gibt die SKS-Liste zu Beginn des Raid's wieder.
     * @return SKS-Liste
     */
    public LinkedList<Player> getSKS() {
        Player[] caderBySks = new Player[playersByName.size()];
        LinkedList<String> names = getPlayersNames();
        //loop over all Players 
        for (int i = 0; i < names.size(); i++) {
            Player player = this.getPlayerByName(names.get(i));
            caderBySks[player.getSksPos()-1] = player;
            }
        return new LinkedList<>(Arrays.asList(caderBySks));
    }
    
    /**
     * Gibt die Uhrzeiten wieder, zudenen Loot verteilt wurde,
     * also den Id's der verteilten Loot's.
     * @return Liste der Uhrzeiten
     */
    public Date[] getlootDates() {
        return lootList.keySet().toArray(new Date[lootList.size()]);
    }
    
    /**
     * Gibt zurück, ob der Spieler während des Raid's Loot erhalten hat.
     * @param player der überprüft werden soll.
     * @return ob der Spieler Loot erhalten hat.
     */
    public boolean isPlayerGetLoot(Player player){
        for(Date date : lootList.keySet()){
            if(lootList.get(date).getPlayer().equals(player)){
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Berechnet die momentane SKS-Liste neu.
     */
    public void recalcSKS() {
        playersBySKS.clear();
        for (String name : playersByName.keySet()) {
            Player player = playersByName.get(name);
            if ((player.getState().equals(RaidState.ERSATZ))||(player.getState().equals(RaidState.TEILG))) {
                playersBySKS.add(player);
            }
        }
        Comparator<Player> plyrComp = new SKSComparator();
        java.util.Collections.sort(playersBySKS, plyrComp);
        Date[] dates = getlootDates();
        Comparator<Date> dateComp = new DateComparator();
        java.util.Arrays.sort(dates, dateComp);
        for (int i = 0; i < dates.length; i++) {
            Loot loot = lootList.get(dates[i]);
            if(loot.getState().equals(LootState.RAID)){
                validateLoot(loot);
            }
        }
    }

    
    public void removeAllPlaces() {
        places.clear();
    }
    /**
     * Entfernt den Loot der zu dem Zeitpunkt vergeben wurde, falls vorhanden.
     * @param timestamp Zeitpunkt des zu entfernenden Loot's.
     */
    public void removeLoot(Date timestamp) {
        lootList.remove(timestamp);
    }
    
    /**
     * Entfernt den zum Namen passenden Spieler im Raid.
     * @param name des zu entfernenden Spielers.
     */
    public void removePlayer(String name) {
        Player player = this.getPlayerByName(name);
        this.playersBySKS.remove(player);
        this.playersByName.remove(name);
        this.playersByID.remove(player.getId());
        LinkedList<String> names = getPlayersNames();
        for(String n : names){
            Player plyrs = getPlayerByName(n);
            int index = plyrs.getSksPos();
            if(index>player.getSksPos()){
                plyrs.setSksPos(index-1);
            }
        }
    }
    
    /**
     * Ändert den Kommentar-Block zum Raid.
     * @param comment neuer Kommentar-Block als String.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * Ändert einen Spieler mit der Id, falls es bereits einen Spieler mit der
     * Id im Kader gibt, ansonnsten wird er hinzugefügt.
     * @param player der Spieler.
     */
    public void setPlayer(Player player) {
        if(playersByID.containsKey(player.getId())){
            playersByName.clear();
            for(String id : playersByID.keySet()){
                Player current = playersByID.get(id);
                playersByName.put(current.getName(), current);
            }
        }else{
            playersByID.put(player.getId(), player);
            playersByName.put(player.getName(), player);
        }
    }
    
    /**
     * fügt den Ort des Raid's hinzu.
     * @param place neuer Ort des Raid's.
     */
    public void addPlace(Place place) {
        places.add(place);
    }
    /**
     * entfernt den Ort des Raid's, sofern vorhanden.
     * @param place Ort des Raid's.
     */
    public void removePlace(Place place) {
        places.remove(place);
    }
    
    public void setPlaces(ArrayList<Place> places){
        this.places.clear();
        this.places.addAll(places);
    }
    
    /**
     * Hier wird der Timestamp geändert.
     * Da der aber auch im Book geändert werden muss, sollte dort die passende
     * Funktion verwendet werden, die auf diese dann zugreift!
     * @param timestamp neues Datum
     */
    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
    
    /**
     * Nimmt die SKS-Änderungen für den zum Zeitpunkt passenden Loot vor.
     * @param timestamp Zeitpunkt des Loot's.
     */
    private void validateLoot(Loot loot) {
        playersBySKS.remove(loot.getPlayer());
        playersBySKS.addLast(loot.getPlayer());
    }
}