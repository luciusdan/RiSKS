/*
 * Klasse zum speichern der Spieler Referenzen.
 * HashMap zum Referenzieren über den Namen des Spieler's.
 * HashMap zum Referenzieren über den Beitritt's Datum des Spieler's.
 * Version 2.0
 */
package Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Objekt welches den Kader repräsentiert.
 * Dabei werden die Spieler mit Namen und Datum Referenziert.
 * @author Dirk
 */
public class Cader {
    public static SimpleDateFormat showDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    //-- Variables for Cader ---------------------------------------------------
    private HashMap<String,Member> membersByName;
    private Date timestamp;

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    //-- Constructor for Cader -------------------------------------------------
    /**
     * Konstructor
     */
    public Cader() {
        membersByName = new HashMap<>();
        //timestamp = fileHandler.readCader(membersByName, membersByDate); //anscheinend nicht nötig
    }
    
    /**
     * Gibt den zum Namen passenden Spieler zurück.
     * Wobei null zurückgegeben wird, falls er nicht gefunden werden kann.
     * @param name Referenz-Namen zum Spieler
     * @return gibt den gesuchten Spieler zurück, falls vorhanden
     */
    public Member getMember(String name) {
        return membersByName.get(name);
    }

    public HashMap<String, Member> getMembersByName() {
        return membersByName;
    }
    
    public String[] getMemberNames(){
        return membersByName.keySet().toArray(new String[membersByName.size()]);
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
