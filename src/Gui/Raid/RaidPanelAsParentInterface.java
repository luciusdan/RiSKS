/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.Raid;

import Data.Raid;

/**
 *
 * @author Dirk
 */
public class RaidPanelAsParentInterface {
    RaidPanel rp;

    public RaidPanelAsParentInterface(RaidPanel rp) {
        this.rp = rp;
    }
    
    public void drawMember(){
        rp.drawMembers();
    }
    
    public void drawLoot(){
        rp.drawLoot();
    }
    
    public void drawAll(){
        rp.drawAll();
    }
    
    public void setRaid(Raid raid){
        rp.setRaid(raid);
    }
}
