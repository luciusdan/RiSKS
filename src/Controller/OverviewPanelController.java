/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Edit.PlayerEditController;
import Data.Book;
import Data.Player;
import Data.Player.RaidState;
import static Data.Player.RaidState.ABWESEND;
import static Data.Player.RaidState.ERSATZ;
import static Data.Player.RaidState.TEILG;
import Data.Raid;
import GUI.Overview.PlayerOverviewPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Dirk
 */
public class OverviewPanelController implements ControllerInterface {
    Book book;
    PlayerOverviewPanel op;
    Boolean filter;
    PlayerEditController pec;
    
    public OverviewPanelController(PlayerOverviewPanel overviewPanel, PlayerEditController mec, Book book) {
        this.pec = mec;
        this.book = book;
        this.op = overviewPanel;
        filter= true;
    setListener();
    }
    
    class MemberAcceptListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.acceptMember();
            updateView();
        }
    }
    class MemberAddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            pec.createMember();
        }
    }
    class MemberRemoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = op.getSelectedPlayerName();
            pec.removePlayer(name);
            updateView();        
        }
    }
    class MemberEditListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String memberName = op.getSelectedPlayerName();
            if(memberName!=null){
                Raid raid = book.getActiveRaid();
                pec.editMember(raid.getPlayerByName(memberName));
            }
        }
    }
    private void setListener(){
        pec.addAcceptController(this);
        op.addPlayerAddListener(new MemberAddListener());
        op.addPlayerEditListener(new MemberEditListener());
        op.addPlayerRemoveListener(new MemberRemoveListener());
        op.addPopupListener(new PopupListener());
        op.addPercentListener(new PercentListener());
        op.addPlayerTableListener(new MemberTableListener());
    }
    class MemberTableListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!filter){
                String name =op.getSelectedPlayerName();
                Raid raid = book.getRaid(book.getNewestRaidDate());
                op.setEditable(raid.getPlayerByName(name)!=null);
            }
        }
    }
    class PopupListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem obj=(JCheckBoxMenuItem) e.getSource();
            filter = obj.getState();
            updateView();
        }
    }
    
    class PercentListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateView();
        }
    }
    
    public void updateView(){
        Raid newestRaid = book.getRaid(book.getNewestRaidDate());
        HashMap<String,Object[]> map = new HashMap<>();
        int index = 0;
        for(Date date : book.getRaidDates()){
            Raid raid = book.getRaid(date);
            for(String id : raid.getPlayerIds()){
                if((!filter)||(newestRaid.getPlayerByID(id)!=null)){
                    Object[] iC;
                    if(map.containsKey(id)){
                        iC = map.get(id);
                    }else{
                        iC = new Object[4];
                        map.put(id, iC);
                        Player member = raid.getPlayerByID(id);
                        //TODO abfrage einfügen
                        iC[0] = member.getName();
                        iC[1] = member.getGuild().getName();
                        Integer[] indizies = {0,0,0};
                        iC[2] = indizies;
                        iC[3] = new RaidState[book.getRaidDates().size()];
                    }
                    Integer[] indizies = (Integer[]) iC[2];
                    Player.RaidState[] rs = (Player.RaidState[]) iC[3];

                    Player.RaidState state = raid.getPlayerByID(id).getState();
                    rs[index] = state;
                    switch(state){
                        case TEILG:
                            indizies[0]++;
                            break;
                        case ERSATZ:
                            indizies[1]++;
                            break;
                        case ABGEMELDET:
                        case ABWESEND:
                            indizies[2]++;
                            break;
                        default:
                            break;
                    }
                    iC[2] = indizies;
                    iC[3] = rs;
                }
            }
            index++;
        }
        
        //HashMap to Arrays
        Object[] content = new Object[map.size()];
        index = 0;
        for(Object[] iC : map.values()){
            content[index]= iC;
            index++;
        }
        Arrays.sort(content, new MemberComparator());
        op.setContent(content, book.getRaidDates());
    }
    
    class MemberComparator implements Comparator<Object> { 
        HashMap<String,Player> members;
        public MemberComparator(){
            super();
        }
        @Override
        public int compare(Object ob1, Object ob2) {
            Object[] array1 = (Object[]) ob1;
            Object[] array2 = (Object[]) ob2;
            String name1 = array1[0]!=null?(String) array1[0]:"";
            String name2 = array2[0]!=null?(String) array2[0]:"";
            String guild1 = array1[1]!=null?(String) array1[1]:"";
            String guild2 = array2[1]!=null?(String) array2[1]:"";
            
            int comp1 = guild1.compareTo(guild2);
            if(comp1==0){
                return name1.compareTo(name2);
            }else{
                return comp1;
            }
        }
    }
}
