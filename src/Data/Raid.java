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
    private HashMap<String, Member> membersByName;
    LinkedList<Member> membersBySKS;
    HashMap<Date, Loot> lootList;

    public Raid(Date timestamp, Cader cader) {
        this(timestamp, cader, false);
    }

    public Raid(Date timestamp, Cader cader, boolean bool) {
        this.timestamp = timestamp;
        this.comment = "";

        this.membersByName = cader.getMembersByName();
        this.membersBySKS = new LinkedList<>();
        this.lootList = new HashMap<>();


        if (bool) {
            getLastSKS(cader);
        }else{
            cader.setTimestamp(timestamp);
        }
    }
    //-- functions for Timestamp ----------------------------------------------- 

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    //-- functions for Comments ------------------------------------------------ 
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //-- functions for Loot ----------------------------------------------------
    public Loot getLoot(Date timestamp) {
        return lootList.get(timestamp);
    }

    public void addLoot(Loot loot) {
        this.lootList.put(loot.getTimestamp(), loot);
        validateLoot(loot);
    }

    public void removeLoot(Date timestamp) {
        this.lootList.remove(timestamp);
    }

    public Date[] getlootDates() {
        return lootList.keySet().toArray(new Date[lootList.size()]);
    }

    private void validateLoot(Loot loot) {
        membersBySKS.remove(loot.getMember());
        membersBySKS.addLast(loot.getMember());
    }

    //-- functions for Members -------------------------------------------------
    public String[] getMembersNames() {
        return membersByName.keySet().toArray(new String[membersByName.size()]);
    }

    public LinkedList<Member> getFiltertMembersNames() {
        String[] names = membersByName.keySet().toArray(new String[membersByName.size()]);
        LinkedList<Member> newMembers = new LinkedList<>();
        for (int i = 0; i < names.length; i++) {
            Member member = membersByName.get(names[i]);
            if (member.getState().equals(State.TEILG)) {
                newMembers.add(member);
            }
        }
        Comparator<Member> comparator = new nameComparator();
        java.util.Collections.sort(newMembers, comparator);
        return newMembers;
    }

    public Member getMemberByName(String name) {
        return membersByName.get(name);
    }

    public Member getMemberBySKS(int place) {
        return membersBySKS.get(place);
    }

    public void addMember(Member member) {
        membersByName.put(member.getName(), member);
    }

    //-- functions for SKS -----------------------------------------------------
    public int getSKSListLength() {
        return membersBySKS.size();
    }

    public void MoveMemberBySKS(int place) {
        membersBySKS.addLast(membersBySKS.remove(place));
    }

    public int getSKSPos(Member member) {
        return membersBySKS.indexOf(member);
    }

    private void resetSKS() {
        Comparator<Member> comparator = new SKSComparator();
        java.util.Collections.sort(membersBySKS, comparator);
    }

    public void recalcSKS() {
        String[] names = getMembersNames();
        membersBySKS.clear();
        for (int i = 0; i < names.length; i++) {
            Member member = membersByName.get(names[i]);
            if (!member.getState().equals(State.ABWESEND)) {
                membersBySKS.add(member);
            }
        }
        resetSKS();
        Date[] dates = getlootDates();
        Comparator<Date> comparator = new DateComparator();
        java.util.Arrays.sort(dates, comparator);
        for (int i = 0; i < dates.length; i++) {
            validateLoot(lootList.get(dates[i]));
        }
    }

    public LinkedList<Member> getFullSKS() {
        //finish local SKS -----------------------------------------------------
        recalcSKS();
        //get DataPointers -----------------------------------------------------
        String[] names = getMembersNames();
        Member[] newSKS = new Member[names.length];
        //loop over all Members ------------------------------------------------
        for (int i = 0; i < names.length; i++) {
            Member member = getMemberByName(names[i]);
            if (member.getState().equals(State.ABWESEND)) {
                newSKS[member.getSksPos() - 1] = member;
            }
        }
        int localSKS = 0; //localSKSPointer
        for (int i = 0; i < names.length; i++) {
            if (newSKS[i] == null) {
                newSKS[i] = getMemberBySKS(localSKS);
                localSKS++;
            }
        }

        for (int i = 0; i < names.length; i++) {
            Member member = newSKS[i];
            if (member == null) {
            } else {
            }
        }
        System.out.println("]");
        return new LinkedList<>(Arrays.asList(newSKS));
    }

    private void getLastSKS(Cader cader) {
        FileHandler fileHandler = new FileHandler(cader);
        Date lastDate = fileHandler.getLastRaidDate();
        if (lastDate == null) {
            //TODO
        } else {
            Raid lastRaid = fileHandler.readRaid(lastDate);
            LinkedList<Member> oldMembers = lastRaid.getFullSKS();
            for (int i = 0; i < oldMembers.size(); i++) {
                Member newMember = membersByName.get(oldMembers.get(i).getName());
                newMember.setSksPos(i + 1);
                newMember.setState(State.ABWESEND);
                if(newMember.getState().equals(State.TEILG)){
                    newMember.setRaidCount((newMember.getRaidCount()+1));
                }

            }
            resetSKS();
        }
    }
    //--comperatoren -------------------------------------------------------

    private class SKSComparator implements Comparator<Member> {

        @Override
        public int compare(Member a, Member b) {
            double sksA = a.getSksPos();
            double sksB = b.getSksPos();

            if (sksA < sksB) {
                return -1;
            }
            if (sksA > sksB) {
                return 1;
            }

            return 0;
        }
    }

    private class nameComparator implements Comparator<Member> {

        @Override
        public int compare(Member a, Member b) {
            String nameA = a.getName();
            String nameB = b.getName();
            return nameA.compareTo(nameB);
        }
    }

    private class DateComparator implements Comparator<Date> {

        @Override
        public int compare(Date a, Date b) {
            return a.compareTo(b);
        }
    }
}
