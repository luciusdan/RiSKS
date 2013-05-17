/*
 * Klasse zum speichern der Informationen eines Spieler's.
 * Erbt von Player.
 * name(geerbt) = Char Name des Spielers
 * role(geerbt) = Klasse des Spielers
 * SKS-Position = SKS-Position zu beginn des Raid's.
 * MainSpec = Hauptaufgabe des Spieler's im Raid: Tank, Heiler, DD oder Support
 * SecSpec = Seltenere Aufgabe des Spieler's im Raid
 * 
 * Version 2.0
 */
package Data;

import java.text.ParseException;
import java.util.Date;


/**
 * Objekt welches einen Spieler im Rift Kader repräsentiert.
 * Dabei werden folgende Informationen verwendet:
 * <p>-. Rolle: Cleric, Mage, Rouge oder Warrior
 * <p>-. Main- and SecSpec: Tank, DD, Heal oder Support
 * <p>-. Datum des Cader-Beitritts
 * @author Dirk
 */
public class Member extends Player{
    //-- Enum for Member -------------------------------------------------------
    public enum State{
        TEILG,ERSATZ,ABWESEND
    }
    public enum Spec{
        TANK,DD,HEAL,SUPPORT,NONE
    }
    
    //-- Variables for Player --------------------------------------------------
    private int sksPos;
    private State state;
    private Spec mainSpec;
    private Spec secSpec;
    private int raidCount;

    

    //-- Constructor for Member ------------------------------------------------
    /**
     * Konstructor
     * @param name Name des zuerstellenen Member's
     */
    public Member(String name) throws ParseException {
        super(name);
        this.mainSpec = Spec.DD;
        this.secSpec = Spec.NONE;
        this.sksPos = -1;
        this.state = State.ABWESEND;
    }
 
    //-- functions for Specs --------------------------------------------------- 

    /**
     * Gibt den MainSpec des Player's als Enum Spec zurück.
     * Wobei Spec.NONE nicht bei MainSpec vorkommen sollte.
     * <p> Enum Role: TANK,DD,HEAL,SUPPORT,[NONE]
     * @return MainSpec des Player's als Enum Spec
     */
    public Spec getMainSpec() {
        return mainSpec;
    }
    
    /**
     * Gibt den MainSpec des Player's als String zurück.
     * Wobei "No Specific nicht vorkommen sollte"
     * <p> Werte: Schaden, Tank, Heiler, Support, [no Specific]
     * @return MainSpec des Player's als String
     */
    public String getStringMainSpec() {
        switch(mainSpec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return "no Specific";
        }
    }
    
    /**
     * Ändert MainSpec des Player's zum übergebenen Enum Spec.
     * @param mainSpec der neue MainSpec des Player's
     */
    public void setsMainSpec(Spec mainSpec) {
        this.mainSpec = mainSpec;
    }
    
    /**
     * Ändert MainSpec des Player's zum übergebenen String.
     * Wobei Groß-/Kleinschreibung ignorriert wird.
     * <p> Werte: tank, heal, support, [alles andere wird als DD interpretiert]
     * @param mainSpec der neue MainSpec des Player's
     */
    public void setMainSpec(String mainSpec) {
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
    
    //-------------
    
    /**
     * Gibt den SecSpec des Player's als Enum Spec zurück.
     * <p> Enum Role: TANK,DD,HEAL,SUPPORT,NONE
     * @return SecSpec des Player's als Enum Spec
     */
    public Spec getSecondSpec() {
        return secSpec;
    }
    
    /**
     * Gibt den SecSpec des Player's als String zurück.
     * <p> Werte: Schaden, Tank, Heiler, Support, " "
     * @return SecSpec des Player's als String
     */
    public String getStringSecondSpec() {
        switch(secSpec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return " ";
        }
    }
    
    /**
     * Ändert SecSpec des Player's zum übergebenen Enum Spec.
     * @param secSpec der neue SecSpec des Player's
     */
    public void setSecondSpec(Spec secSpec) {
        this.secSpec = secSpec;
    }
    
    /**
     * Ändert SecSpec des Player's zum übergebenen String.
     * Wobei Groß-/Kleinschreibung ignorriert wird.
     * <p> Werte: dd, tank, heal, support, alles andere wird als NONE interpretiert
     * @param secSpec der neue SecSpec des Player's
     */
    public void setSecondSpec(String secSpec) {
        secSpec = secSpec.toLowerCase();
        switch(secSpec){
            case "schaden":
                this.secSpec = Spec.DD;
                break;
            case "tank":
                this.secSpec = Spec.TANK;
                break;
            case "heiler":
                this.secSpec = Spec.HEAL;
                break;
            case "support":
                this.secSpec = Spec.SUPPORT;
                break;
            default:
                this.secSpec = Spec.NONE;
                break;
        
        }
    }
    
    //-- functions for State ---------------------------------------------------
    
    
    /**
     * Gibt den Raid-Status des Spieler's als Enum State zurück.
     * <p> Werte: TEILG,ERSATZ,ABWESEND
     * @return Enum State des Spieler's
     */
    public State getState() {
        return state;
    }

    /**
     * Gibt den Raid-Status des Spieler's als String zurück.
     * <p> Werte: "teilgenommen", "ersatz" oder "abwesend"
     * @return State als String des Spieler's
     */
    public String getStringState(){
        switch(state){
            case TEILG :
                return "teilgenommen";
            case ERSATZ :
                return "ersatz";
            default  :
                return "abwesend";
            }
        }
    
    /**
     * Ändert Status des Player's zum übergebenen Enum State.
     * @param state der neue Status des Player's als Enum State
     */
    public void setState(State state) {
        this.state = state;
    }
    /**
     * Ändert Status des Player's zum übergebenen State.
     * @param state der neue Status des Player's als String
     */
    public void setState(String state) {
        switch(state){
            case "teilgenommen":
                this.state = State.TEILG;
                break;
            case "ersatz":
                this.state = State.ERSATZ;
                break;
            default  :
                this.state = State.ABWESEND;
                break;
            }
        }
    
    //-- functions for SKS -----------------------------------------------------
  
    /**
     * Gibt die anfängliche Position des Spieler's auf der SKS-Liste als
     * Integer zurück.
     * @return Position des Spieler's auf der SKS-Liste
     */
    public int getSksPos() {
        return sksPos;
    }

    /**
     * Ändert die anfängliche osition des Spieler's auf der SKS-Liste
     * zum übergebenen Wert.
     * @param sksPos neue SKS-Startposition
     */
    public void setSksPos(int sksPos) {
        this.sksPos = sksPos;
    }

    public int getRaidCount() {
        return raidCount;
    }

    public void setRaidCount(int raidCount) {
        this.raidCount = raidCount;
    }
}