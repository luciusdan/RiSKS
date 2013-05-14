/*
 * Class zum speichern von dirketen Informationen der Spieler.
 * name = Char Name des Spielers
 * role = Klasse des Spielers
 * MainSpec = da nur für main Rolle Spec nötig: Main Rolle (Tank/Heiler/DD/Sup
 */
package Data;

import java.util.Date;

/**
 *
 * @author Dirk
 */
public class Player {
    public enum Role{
        CLERIC,MAGE,ROUGE,WARRIOR
    }
    public enum Spec{
        TANK,DD,HEAL,SUPPORT,NONE
    }
    
    
    public Player(String name) {
        this.name = name;
        this.role = Role.CLERIC;
        this.mainSpec = Spec.DD;
        this.secSpec = Spec.NONE;
        this.joining = new Date();
    }
    
    public Player(String name, Date joining) {
        this.name = name;
        this.role = Role.CLERIC;
        this.mainSpec = Spec.DD;
        this.secSpec = Spec.NONE;
        this.joining = joining;
    }
    
    private String name;
    private Role role;
    private Spec mainSpec;
    private Spec secSpec;
    private Date joining;

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
    public String getStringRole() {
        switch(role){
            case CLERIC : return "Kleriker";
            case MAGE   : return "Magier";
            case ROUGE  : return "Schurke";
            case WARRIOR: return "Krieger";
            default: return "no Specific";
        }
    }

    public Spec getMainSpec() {
        return mainSpec;
    }
    
    public Spec getSecondSpec() {
        return secSpec;
    }
    public String getStringMainSpec() {
        switch(mainSpec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return "no Specific";
        }
    }
    public String getStringSecondSpec() {
        switch(secSpec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return " ";
        }
    }

    public Date getJoining() {
        return joining;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setClass(Role role) {
        this.role = role;
    }
    public void setClass(String role) {
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
    
    public void setMainSpec(Spec mainSpec) {
        this.mainSpec = mainSpec;
    }
    
    public void setSecondSpec(Spec secSpec) {
        this.secSpec = secSpec;
    }
    public void setMainSpec(String mainSpec) {
        mainSpec = mainSpec.toLowerCase();
        switch(mainSpec){
            case "tank":
                this.mainSpec = Spec.TANK;
                break;
            case "heal":
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
    
    public void setSecondSpec(String secSpec) {
        secSpec = secSpec.toLowerCase();
        switch(secSpec){
            case "dd":
                this.secSpec = Spec.DD;
                break;
            case "tank":
                this.secSpec = Spec.TANK;
                break;
            case "heal":
                this.secSpec = Spec.HEAL;
                break;
            case "sup":
                this.secSpec = Spec.SUPPORT;
                break;
            default:
                this.secSpec = Spec.NONE;
                break;
        
        }
    }
    
    @Override
    public String toString(){
        String out = "Player( ";
        out+=name+" )[ ";
        out+=role+" | ";
        out+=getStringMainSpec()+" ; ";
        out+=getStringSecondSpec()+" )";
        return out;
    }
}
