/*
 * Klasse zum speichern von direkten Informationen der Spieler.
 * name = Char Name des Spielers
 * role = Klasse des Spielers
 * 
 * Version 2.0
 */
package Data;


/**
 * Objekt welches einen Spieler aus Rift repräsentiert.
 * Dabei werden folgende Informationen verwendet:
 * <p>-. Rolle: Cleric, Mage, Rouge oder Warrior
 * <p>-. Datum des Cader-Beitritts
 * @author Dirk
 */
public class Player {
    //-- Enum for Player -------------------------------------------------------
    public enum Role{
        CLERIC,MAGE,ROUGE,WARRIOR
    }
    
    //-- Variables for Player --------------------------------------------------
    private String name;
    private Role role;

    //-- Constructor for Player ------------------------------------------------
    /**
     * Konstructor
     * @param name Name des zuerstellenen Player
     */
    public Player(String name) {
        this.name = name;
        this.role = Role.CLERIC;
    }

    //-- functions for Name ----------------------------------------------------
    /**
     * Gibt den Namen des Player's als String zurück
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Ändert den Namen des Player's zu dem übergebenen String
     * @param name the new Name for the Player
     */
    public void setName(String name) {
        this.name = name;
    }
    
    //-- functions for Role ----------------------------------------------------
    /**
     * Gibt die Rolle des Player's als Enum Role zurück.
     * <p> Enum Role: CLERIC,MAGE,ROUGE,WARRIOR
     * @return Enum Role des Player's
     */
    public Role getRole() {
        return role;
    }
    
    /**
     * Gibt die Rolle des Player's als String zurück.
     * <p> Werte: Kleriker, Magier, Schurke, Krieger, no Specific
     * @return Rolle als String des Player's
     */
    public String getStringRole() {
        switch(role){
            case CLERIC : return "Kleriker";
            case MAGE   : return "Magier";
            case ROUGE  : return "Schurke";
            case WARRIOR: return "Krieger";
            default: return "no Specific";
        }
    }
    
    /**
     * Ändert die Rolle zur übergebenen Enum Role
     * @param role neue Rolle des Player's als Enum Role
     */
    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * Ändert die Rolle zur übergebenen Rolle
     * Wobei Groß-/Kleinschreibung ignorriert wird.
     * <p> Werte: magier, schurke, krieger,kleriker
     * <p> Oder: mage, rouge, warrior, cleric
     * @param role neue Rolle des Player's als String
     */
    public void setRole(String role) {
        role = role.toLowerCase();
        switch (role) {
            case "magier":
            case "mage":
                this.role = Role.MAGE;
                break;
            case "schurke":
            case "rouge":
                this.role = Role.ROUGE;
                break;
            case "krieger":
            case "warrior":
                this.role = Role.WARRIOR;
                break;
            case "kleriker":
            case "cleric":
                this.role = Role.CLERIC;
                break;
        }
    }
    //-- functions for Specs --------------------------------------------------- 

    /**
     * Gibt das Beitrittsdatum des Player's zum Raidkader als Date zurück.
     * @return Datum des Raidbeitritt's des Player's
     */
}