/*
 * Class zum speichern von dirketen Informationen der Spieler.
 * name = Char Name des Spielers
 * role = Klasse des Spielers
 * MainSpec = da nur für main Rolle Spec nötig: Main Rolle (Tank/Heiler/DD/Sup
 */
package Data;

/**
 *
 * @author Dirk
 */
public class Player {
    public enum Role{
        CLERIC,MAGE,ROUGE,WARRIOR
    }
    public enum Spec{
        TANK,DD,HEAL,SUPPORT
    }
    
    
    public Player(String name, Role role, Spec MainSpec) {
        this.name = name;
        this.role = role;
        this.mainSpec = MainSpec;
    }
    public Player(String name) {
        this.name = name;
        this.role = Role.CLERIC;
        this.mainSpec = Spec.DD;
    }
    
    
    private String name;
    private Role role;
    private Spec mainSpec;

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
    public String getStringMainSpec() {
        switch(mainSpec){
            case DD : return "Schaden";
            case TANK   : return "Tank";
            case HEAL  : return "Heiler";
            case SUPPORT: return "Support";
            default: return "no Specific";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClass(Role role) {
        this.role = role;
    }
    public void setClass(String role) {
        role = role.toLowerCase();
        if(role.equals("magier")||role.equals("mage")){
            this.role = Role.MAGE;
        }else if(role.equals("schurke")||role.equals("rouge")){
            this.role = Role.ROUGE;
        }else if(role.equals("krieger")||role.equals("warrior")){
            this.role = Role.ROUGE;
        }else if(role.equals("kleriker")||role.equals("cleric")){
            this.role = Role.CLERIC;
        }
    }
    
    public void setMainSpec(Spec MainSpec) {
        this.mainSpec = MainSpec;
    }
    public void setMainSpec(String mainSpec) {
        mainSpec = mainSpec.toLowerCase();
        if(mainSpec.equals("dd")){
            this.mainSpec = Spec.DD;
        }else if(mainSpec.equals("tank")){
            this.mainSpec = Spec.TANK;
        }else if(mainSpec.equals("heal")||mainSpec.equals("heiler")){
            this.mainSpec = Spec.HEAL;
        }else if(mainSpec.equals("sup")||mainSpec.equals("support")){
            this.mainSpec = Spec.SUPPORT;
        }
    }    
    
    
}
