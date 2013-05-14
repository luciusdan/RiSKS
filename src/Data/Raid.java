/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Data.Member.State;
import InOut.FileHandler;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class Raid {
    private Date timestamp;
    private String comment;
    
    private HashMap<String,Member> membersByName;
    LinkedList<Member> membersBySKS;
    HashMap<Date,Loot> lootList;

    public Raid(Date timestamp, Cader cader) {
        this(timestamp, cader, false);
    }

    public Raid(Date timestamp, Cader cader,boolean bool) {
        this.timestamp = timestamp;
        this.comment="";
        
        this.membersByName= new HashMap<>();
        this.membersBySKS= new LinkedList<>();
        this.lootList = new HashMap<>();
        
        HashMap<String,Player> players = cader.getCader();
        String[] names = players.keySet().toArray(new String[players.size()]);
        for (int i=0;i<names.length;i++){
            Member newRaider =  new Member(players.get(names[i]));
            this.membersByName.put(names[i],newRaider);
        }
        if(bool){
            getLastSKS(cader);
        }
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Loot getLoot(Date timestamp) {
        return lootList.get(timestamp);
    }

    public void addLoot(Loot loot) {
        this.lootList.put(loot.getTimestamp(),loot);
        validateLoot(loot);
    }
    
    public void removeLoot(Date timestamp) {
        this.lootList.remove(timestamp);
    }
    
    public Date[] getlootDates() {
        return lootList.keySet().toArray(new Date[lootList.size()]);
    }
        
    public String[] getMembersNames() {
        return membersByName.keySet().toArray(new String[membersByName.size()]);
    }
    
    public LinkedList<Member> getfiltertMembersNames() {
        String[] names = membersByName.keySet().toArray(new String[membersByName.size()]);
        LinkedList<Member> newMembers= new LinkedList<>();
        for(int i =0;i<names.length;i++){
            Member member = membersByName.get(names[i]);
            if(member.getState().equals(State.TEILG)){
                newMembers.add(member);
            }
        }
        
        Comparator<Member> comparator = new nameComparator();
        java.util.Collections.sort( newMembers, comparator );
        return newMembers;
    }
    
    public Member getMemberByName(String name) {
        return membersByName.get(name);
    }
    
    public Member getMemberBySKS(int place){
        return membersBySKS.get(place);
    }
    
    public int getSKSListLength(){
        return membersBySKS.size();
    }
    
    public void MoveMemberBySKS(int place){
        membersBySKS.addLast(membersBySKS.remove(place));
    }
    
    public int getSKSPos(Member member){
        return membersBySKS.indexOf(member);
    }
    
    private void resetSKS(){
        Comparator<Member> comparator = new SKSComparator();
        java.util.Collections.sort( membersBySKS, comparator );
    }

    public void recalcSKS() {
        String[] names = getMembersNames();
        membersBySKS.clear();
        for(int i=0;i<names.length;i++){
            Member member = membersByName.get(names[i]);
            if(!member.getState().equals(State.ABWESEND)){
                membersBySKS.add(member);
            }
        }
        resetSKS();
        Date[] dates = getlootDates();
        Comparator<Date> comparator = new DateComparator();
        java.util.Arrays.sort( dates, comparator );
        for(int i=0;i<dates.length;i++){
            validateLoot(lootList.get(dates[i]));
        }
    }
    
    private void validateLoot(Loot loot){
        membersBySKS.remove(loot.getMember());
        membersBySKS.addLast(loot.getMember());
    }

    public LinkedList<Member> getFullSKS() {
        //finish local SKS -----------------------------------------------------
        recalcSKS();
        //get DataPointers -----------------------------------------------------
        String[] names = getMembersNames();
        Member[] newSKS = new Member[names.length];
        //loop over all Members ------------------------------------------------
        for(int i=0;i<names.length;i++){
            Member member = getMemberByName(names[i]);
            if(member.getState().equals(State.ABWESEND)){
                System.out.println("Add Stable ["+i+"]"+member.getSksPos()+" | "+member.getPlayer().getName());
                newSKS[member.getSksPos()-1] = member;
            }
        }
        int localSKS =0; //localSKSPointer
        for(int i=0;i<names.length;i++){
            if(newSKS[i]==null){
                newSKS[i]= getMemberBySKS(localSKS);
                localSKS++;
            }
        }
            
        System.out.println("----------------------------------");
        System.out.print("NewSKSList:  [ ");
        for(int i=0;i<names.length;i++){
            Member member = newSKS[i];
            if(member==null){
                System.out.print("Null |");
            }else{
                System.out.print(member.getPlayer().getName()+" |");
            }
        }
        System.out.println("]");
        return new LinkedList<>(Arrays.asList(newSKS));
    }

    private class DateComparator implements Comparator<Date>{
        @Override
        public int compare( Date a, Date b ) {
            return a.compareTo(b);
        }
    }
    /*public LinkedList<Member> getFullSKS() {
        //finish local SKS -----------------------------------------------------
        recalcSKS();
        //get DataPointers -----------------------------------------------------
        String[] names = getMembersNames();
        Member[] newSKS = new Member[names.length];
        int localSKS =0; //localSKSPointer
        //loop over all Members ------------------------------------------------
        for(int i=0;i<names.length;i++){
            Member member = getMemberByName(names[i]);
            if(member.getState().equals(State.ABWESEND)){
                System.out.println("Add Stable ["+i+"]"+member.getSksPos()+" | "+member.getPlayer().getName());
                newSKS[member.getSksPos()-1] = member;
            }else{
                Member newMember= getMemberBySKS(localSKS);
                System.out.println("Add new ["+i+"]"+localSKS+" | "+newMember.getPlayer().getName());
                newSKS[i]= newMember;
                localSKS++;
            }
        }
        System.out.println("----------------------------------");
        System.out.print("NewSKSList:  [ ");
        for(int i=0;i<names.length;i++){
            Member member = newSKS[i];
            if(member==null){
                System.out.print("Null |");
            }else{
                System.out.print(member.getPlayer().getName()+" |");
            }
        }
        System.out.println("]");
        return new LinkedList<>(Arrays.asList(newSKS));
    }

    private class DateComparator implements Comparator<Date>{
        @Override
        public int compare( Date a, Date b ) {
            return a.compareTo(b);
        }
    }//*/
    
    private class SKSComparator implements Comparator<Member>{
        @Override
        public int compare( Member a, Member b ) {
            double sksA = a.getSksPos();
            double sksB = b.getSksPos();

            if( sksA < sksB )
                return -1;
            if( sksA > sksB )
                return 1;

            return 0;
        }
    }

    private class nameComparator implements Comparator<Member>{
        @Override
        public int compare( Member a, Member b ) {
            String nameA = a.getPlayer().getName();
            String nameB = b.getPlayer().getName();
            return nameA.compareTo(nameB);
        }
    }

    private class joiningComparator implements Comparator<Member>{
        @Override
        public int compare( Member a, Member b ) {
            Date valA = a.getPlayer().getJoining();
            Date valB = b.getPlayer().getJoining();
            return valA.compareTo(valB);
        }
    }
    
    private void getLastSKS(Cader cader){
        FileHandler rfh = new FileHandler(cader);
        Date lastDate = rfh.getLastRaidDate();
        if(lastDate==null){
            Comparator<Member> comparator = new joiningComparator();
            java.util.Collections.sort( membersBySKS, comparator );
            for(int i=0;i<membersBySKS.size();i++){
                membersBySKS.get(i).setSksPos(i);
            }
        }else{
            Raid lastRaid = rfh.readRaid(lastDate);
            String[] names = membersByName.keySet().toArray(new String[membersByName.size()]);
            LinkedList<Member> oldMembers = lastRaid.getFullSKS();
            for(int i=0;i<oldMembers.size();i++){
                Member newMember = membersByName.get(oldMembers.get(i).getPlayer().getName());
                newMember.setSksPos(i+1);
            }
            resetSKS();
        }
    }
}
