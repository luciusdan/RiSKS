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

import java.util.Date;

/**
 * Objekt welches einen Loot repräsentiert.
 * Dabei wird eine Beziehung zwischen
 * <p> einem Player
 * <p> einer Uhrzeit
 * <p> einer Bezeichnung
 * gezogen.
 * @author Dirk
 */
public class Loot {
    //Datums Format für die Anzeige des Loot's.
    
    public enum LootState{
        RAID,ELSE
    }
    //Spieler der den Loot erhalten hat.
    private Player player;
    //Datum zu dem der Spieler den Loot erhalten hat.
    private Date timestamp;
    //Der Name unter dem der Spieler den Loot erhalten hat.
    private String description;

    private LootState state;
    
    /**
     * Konstruktor für ein Loot-Objekt.
     * @param timestamp Uhrzeit der Lootvergabe.
     * @param player Spieler der den Loot erhalten hat.
     * @param description Bezeichnung des Loots zur Vergabe.
     */
    public Loot(Date timestamp,Player player, String description) {
        this.timestamp = timestamp;
        this.player = player;
        this.description = description;
        this.state = LootState.RAID;
    }
    
    /**
     * Gibt die Beziechnung des Loot's zurück.
     * @return die Bezeichnung des Loot's.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gibt den Spieler der den Loot erhalten hat zurück.
     * @return Spieler des Loot's.
     */
    public Player getPlayer() {
        return player;
    }
    
    
    /**
     * Gibt die Uhrzeit der Lootvergabe zurück.
     * @return Uhrzeit des Loot's.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Ändert die Beschreibung des Loots.
     * @param description neue Beschreibung des Loot's.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Ändert den Spieler der den Loot erhalten hat.
     * @param player neuer Spieler.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Ändert die Uhrzeit der Lootvergabe.
     * @param Timestamp neue Uhrzeit der Lootvergabe.
     */
    public void setTimestamp(Date Timestamp) {
        this.timestamp = Timestamp;
    }

    public LootState getState() {
        return state;
    }

    public void setState(LootState state) {
        this.state = state;
    }
    
    public static LootState stringToState(String stateString){
        String str = stateString.toLowerCase();
        switch(str){
            case "raid":
                return LootState.RAID;
            default:
                return LootState.ELSE;
        }
    }
    
    public static String StateToString(LootState state){
        switch(state){
            case RAID:
                return "RAID";
            default:
                return "";
        }
    }
}
