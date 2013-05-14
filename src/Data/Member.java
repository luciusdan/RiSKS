/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class Member{
    public enum State{
        TEILG,ERSATZ,ABWESEND
    }
    
    private Player player;
    private int sksPos;
    private State state;
    private LinkedList<Loot> lootList;
    
    public Member(Player player){
        this.player = player;
        this.sksPos = -1;
        this.state = State.ABWESEND;
        this.lootList = new LinkedList<>();
    }
    
    public Member(Player player, int pos, State state) {
        this.player = player;
        this.sksPos = pos;
        this.state = state;
        this.lootList = new LinkedList<>();
    }
    
    public Member(Player player, int pos, State state, LinkedList<Loot> loot) {
        this.player = player;
        this.sksPos = pos;
        this.state = state;
        this.lootList = loot;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSksPos() {
        return sksPos;
    }

    public void setSksPos(int sksPos) {
        this.sksPos = sksPos;
    }

    public LinkedList<Loot> getLoot() {
        return lootList;
    }

    public void setLoot(LinkedList<Loot> loot) {
        this.lootList = loot;
    }

    public State getState() {
        return state;
    }

    public String getStateString(){
        switch(state){
            case TEILG :
                return "teilgenommen";
            case ERSATZ :
                return "ersatz";
            default  :
                return "abwesend";
            }
        }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public void addLoot(Loot loot){
        lootList.add(loot);
    }
}
