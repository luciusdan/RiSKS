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

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;



/**
 * Objekt welches einen Spieler im Rift Kader repräsentiert.
 * Dabei werden folgende Informationen verwendet:
 * <p>-. Rolle: Cleric, Mage, rogue oder Warrior
 * <p>-. Main- and SecSpec: Tank, DD, Heal oder Support
 * <p>-. Datum des Kader-Beitritts
 * @author Dirk
 */
public class Player{
    //Format für das Erstellen einer ID.
    private static SimpleDateFormat IDFormat =
            new SimpleDateFormat("D_Y_Hms_S"); 



    //Enum für den Spieler
    public enum RaidState{
        TEILG,ERSATZ,ABWESEND,ABGEMELDET,GELOESCHT
    }
    public enum Spec{
        TANK,DD,HEAL,SUPPORT
    }
    public enum Calling{
        CLERIC,MAGE,ROGUE,WARRIOR
    }
    
    //Variablen für den Spieler
    private String id;
    private String name;
    private Guild guild;
    private Calling calling;
    private Spec mainSpec;
    private LinkedList<Spec> secSpecs;
    
    private RaidState state;
    private int sksPos;
    private int raidCount;

    
    /**
     * Konstructor ohne Angaben
     */
    public Player(){
        this(IDFormat.format(new Date()));
    }
    /**
     * Konstructor mit ID
     * @param id Id des zuerstellenen Player's
     */
    public Player(String id){
        this.id = id;
        this.name = "nobody";
        this.guild = null;
        this.calling = Calling.CLERIC;
        this.mainSpec = Spec.DD;
        this.secSpecs = new LinkedList<>();
        this.state = RaidState.ABWESEND;
        this.sksPos = -1;
        this.raidCount =0;
    }
    /**
     * Konstructor erstellt einen Spieler basierent auf einen Anderen.
     * @param player älterer Player
     */
    public Player( Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.guild = player.getGuild();
        
        this.calling = player.getCalling();
        this.mainSpec = player.getSpecMain();
        this.secSpecs = player.getSpecsSecond();
        
        this.sksPos = player.getSksPos();
        this.state = player.getState();
        this.raidCount = player.getRaidCount();
        
    }
    
    
    /**
     * Gibt eine String Bezeichnung der Klasse aus.
     * @param calling Klasse die übersetzt werden soll. 
     * @return String mit der Bezeichnung der Klasse.
     */
    public static String callingToString(Calling calling) {
        switch(calling){
            case CLERIC : return "Kleriker";
            case MAGE   : return "Magier";
            case ROGUE  : return "Schurke";
            case WARRIOR: return "Krieger";
            default: return "no Specific";
        }
    }
    
    /**
     * Gibt die Rolle des Player's als Enum Calling zurück.
     * <p> Enum Calling: CLERIC,MAGE,ROGUE,WARRIOR
     * @return Enum Calling des Player's
     */
    public Calling getCalling() {
        return calling;
    }
    
    /**
     * Gibt die Farbe zur Passenden Klasse zurück
     * @param calling Enum für die Klasse.
     * <p> Enum Calling: CLERIC,MAGE,ROGUE,WARRIOR
     * @return die passende Farbe (CLERIC|Grüm,MAGE|Violett,ROGUE|Gelborange,WARRIOR|Rot)
     * 
     */
    public static Color getCallingColor(Calling calling){
        switch(calling){
            case CLERIC : return Color.GREEN.darker();
            case MAGE   : return new Color(153, 0,153);
            case ROGUE  : return Color.ORANGE.darker();
            case WARRIOR: return new Color(191,0,0);
            default: return new Color(0,0,0);
        }
    }
    
    /**
     * Gibt die Gilde des Spielers zurück.
     * @return Gilde des Spielers.
     */
    public Guild getGuild() {
        return guild;
    }
    
    /**
     * Gibt die Id des Player's als String zurück
     * @return
     */
    public String getId() {
        return id;
    }
 
    /**
     * Gibt den Namen des Player's als String zurück
     * @return
     */
    public String getName() {
        return name;
    }

    public int getRaidCount() {
        return raidCount;
    }
    
    /**
     * Gibt die Rolle des Player's als String zurück.
     * <p> Werte: Kleriker, Magier, Schurke, Krieger, no Specific
     * @return Rolle als String des Player's
     */
    public String getCallingString() {
        switch(calling){
            case CLERIC : return "Kleriker";
            case MAGE   : return "Magier";
            case ROGUE  : return "Schurke";
            case WARRIOR: return "Krieger";
            default: return "no Specific";
        }
    }
    
    /**
     * Gibt die anfängliche Position des Spieler's auf der SKS-Liste als
     * Integer zurück.
     * @return Position des Spieler's auf der SKS-Liste
     */
    public int getSksPos() {
        return sksPos;
    }
    
    /**
     * Gibt den MainSpec des Player's als Enum Spec zurück.
     * Wobei Spec.NONE nicht bei MainSpec vorkommen sollte.
     * <p> Enum Spec: TANK,DD,HEAL,SUPPORT,[NONE]
     * @return MainSpec des Player's als Enum Spec
     */
    public Spec getSpecMain() {
        return mainSpec;
    }
    
    /**
     * Gibt den MainSpec des Player's als String zurück.
     * Wobei "No Specific nicht vorkommen sollte"
     * <p> Werte: Schaden, Tank, Heiler, Support, [no Specific]
     * @return MainSpec des Player's als String
     */
    public String getSpecMainString() {
        switch(mainSpec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return "no Specific";
        }
    }
    
    /**
     * Gibt den SecSpec des Player's als Enum Spec zurück.
     * <p> Enum Spec: TANK,DD,HEAL,SUPPORT,NONE
     * @return SecSpec des Player's als Enum Spec
     */
    public LinkedList<Spec> getSpecsSecond() {
        return (LinkedList<Spec>) secSpecs.clone();
    }
    
    /**
     * Gibt den Raid-Status des Spieler's als Enum State zurück.
     * <p> Werte: TEILG,ERSATZ,ABWESEND
     * @return Enum State des Spieler's
     */
    public RaidState getState() {
        return state;
    }

    /**
     * Gibt den Raid-Status des Spieler's als String zurück.
     * <p> Werte: "teilgenommen", "ersatz" oder "abwesend"
     * @return State als String des Spieler's
     */
    public String getStateString(){
        switch(state){
            case TEILG :
                return "teilgenommen";
            case ERSATZ :
                return "ersatz";
            case ABGEMELDET :
                return "abgemeldet";
            default  :
                return "abwesend";
            }
    }
    
    
    /**
     * Ändert die Rolle zur übergebenen Enum Calling
     * @param Calling neue Rolle des Player's als Enum Calling
     */
    public void setCalling(Calling calling) {
        this.calling = calling;
    }
    
    /**
     * Ändert die Rolle zur übergebenen Rolle
     * Wobei Groß-/Kleinschreibung ignorriert wird.
     * <p> Werte: magier, schurke, krieger,kleriker
     * <p> Oder: mage, rogue, warrior, cleric
     * @param calling neue Rolle des Player's als String
     */
    public void setCalling(String calling) {
        calling = calling.toLowerCase();
        switch (calling) {
            case "magier":
            case "mage":
                this.calling = Calling.MAGE;
                break;
            case "schurke":
            case "rogue":
                this.calling = Calling.ROGUE;
                break;
            case "krieger":
            case "warrior":
                this.calling = Calling.WARRIOR;
                break;
            case "kleriker":
            case "cleric":
                this.calling = Calling.CLERIC;
                break;
        }
    }
    
    /**
     * Ändert die Gilde des Spielers.
     * @param guild die neue Gilde des Spielers.
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
    }
    
    /**
     * Ändert den Namen des Player's zu dem übergebenen String
     * @param name the new Name for the Player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ändert die Anzahl der bisher teilgenommenen Raid's.
     * @param raidCount die neue Anzahl der teilgenommenen Raid's.
     */
    public void setRaidCount(int raidCount) {
        this.raidCount = raidCount;
    }

    /**
     * Ändert die anfängliche osition des Spieler's auf der SKS-Liste
     * zum übergebenen Wert.
     * @param sksPos neue SKS-Startposition
     */
    public void setSksPos(int sksPos) {
        this.sksPos = sksPos;
    }
    
    /**
     * Ändert die Hauptaufgabe des Player's zum übergebenen Enum Spec.
     * @param mainSpec der neue MainSpec des Player's
     */
    public void setSpecMain(Spec mainSpec) {
        this.mainSpec = mainSpec;
    }
    
    /**
     * Ändert MainSpec des Player's zum übergebenen String.
     * Wobei Groß-/Kleinschreibung ignorriert wird.
     * <p> Werte: tank, heal, support, [alles andere wird als DD interpretiert]
     * @param mainSpec der neue MainSpec des Player's
     */
    public void setSpecMain(String mainSpec) {
        mainSpec = mainSpec.toLowerCase();
        switch(mainSpec){
            case "tank":
                this.mainSpec = Spec.TANK;
                break;
            case "heiler":
                this.mainSpec = Spec.HEAL;
                break;
            case "support":
                this.mainSpec = Spec.SUPPORT;
                break;
            default:
                this.mainSpec = Spec.DD;
                break;
        
        }
    }
    
    /**
     * Ändert SecSpec des Player's zum den übergebenen Enum Spec's.
     * @param specs die neue SecSpec's des Player's
     */
    public void setSpecsSecond(LinkedList<Spec> specs) {
        this.secSpecs = (LinkedList<Spec>) specs.clone();
    }
    
    public void addSpecSecond(Spec spec) {
        secSpecs.add(spec);
    }
    public void addSpecSecond(String secSpec) {
        secSpec = secSpec.toLowerCase();
        switch(secSpec){
            case "schaden":
                this.secSpecs.add(Spec.DD);
                break;
            case "tank":
                this.secSpecs.add(Spec.TANK);
                break;
            case "heiler":
                this.secSpecs.add(Spec.HEAL);
                break;
            case "support":
                this.secSpecs.add(Spec.SUPPORT);
                break;
            default:
                break;
        
        }
    }
    
    /**
     * Ändert Status des Player's zum übergebenen Enum State.
     * @param state der neue Status des Player's als Enum State
     */
    public void setState(RaidState state) {
        this.state = state;
    }
    /**
     * Ändert Status des Player's zum übergebenen State.
     * @param state der neue Status des Player's als String
     */
    public void setState(String state) {
        switch(state){
            case "teilgenommen":
                this.state = RaidState.TEILG;
                break;
            case "ersatz":
                this.state = RaidState.ERSATZ;
                break;
            case "abgemeldet":
                this.state = RaidState.ABGEMELDET;
                break;
            default  :
                this.state = RaidState.ABWESEND;
                break;
        }
    }
    
    /**
     * Gibt eine String Bezeichnung der Rolle aus.
     * @param spec Rolle die übersetzt werden soll. 
     * @return String mit der Bezeichnung der Rolle.
     */
    public static String specToString(Spec spec) {
        switch(spec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return "";
        }
    }
    
    public static Spec StringToSpec(String name) {
        switch(name){
            case "Schaden": return Spec.DD;
            case "Tank"   : return Spec.TANK;
            case "Heiler" : return Spec.HEAL;
            case "Support": return Spec.SUPPORT;
            default: return null;
        }
    }
}