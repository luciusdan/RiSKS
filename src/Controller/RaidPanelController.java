/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Edit.PlayerEditController;
import Controller.Raid.*;
import Data.Book;
import Data.Player;
import Data.Player.RaidState;
import Data.Raid;
import Data.Raid.Place;
import GUI.Raid.RaidPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

    /**
     *
     * @author Dirk
     */
public class RaidPanelController implements ControllerInterface{
    private static final Logger logger = LogManager.getRootLogger();
    RaidPanel rP;
    Book book;
    
    ServingController lpc;
    SKSPanelController spc;
    SelectRaidController srpc;
    SelectPlayerController sppc;

    public RaidPanelController(RaidPanel rp, PlayerEditController mec, Book book){
        this.book = book;
        this.rP = rp;
        
        this.srpc = new SelectRaidController(rp.getRaidEditPanel() , book);
        this.sppc = new SelectPlayerController(rp.getSelectPlayerPanel(), mec  , book);
        this.lpc = new ServingController(rp.getLootPanel() , mec , book);
        this.spc = new SKSPanelController(rp.getSKSPanel(), mec ,book);
        
        addListener();
    }

    private void addListener(){
        rP.addCommentListener(new CommentListener());
        rP.addSelectListener(new SelectListener());
        rP.addSliderListener(new SliderListener());
        
        srpc.addCalendarListener(new CalendarListener());
        srpc.addPlaceAddListener(new PlaceAddListener());
        srpc.addPlaceUpListener(new PlaceUpListener());
        srpc.addPlaceDownListener(new PlaceDownListener());
        srpc.addPlaceRemoveListener(new PlaceRemoveListener());
        srpc.addPlaceAddMouseListener(new PlaceAddMouseListener());
        srpc.addPlaceRemoveMouseListener(new PlaceRemoveMouseListener());
    }
    
    public void setCreateButtonListener(ActionListener l){
        rP.addCreateButtonListener(l);
    }
    
    public void setRemoveButtonListener(ActionListener l){
        rP.addRemoveButtonListener(l);
    }
  
    private class SliderListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            updateView();
        }
    }
    
    private class CommentListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            JTextArea area = (JTextArea) e.getSource();
            book.getActiveRaid().setComment(area.getText());
        }
    }
    
    private class SelectListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            Date date = rP.getSelect();
            logger.info("Raid "+DateFormater.raidDateFormat.format(date)+" ausgewählt.");
            if(date!=null){
                book.setActiveRaid(date);
                updateView();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    private void placeAddEvent(){
        ArrayList<Place> places = srpc.getSelectedOutPlaces();
            Raid raid = book.getActiveRaid();
            for(Place place:places ){
                raid.addPlace(place);
            }
                    updateView();
    }
    private void placeRemoveEvent(){
        ArrayList<Place> places = srpc.getSelectedInPlaces();
            Raid raid = book.getActiveRaid();
            for(Place place:places ){
                raid.removePlace(place);
            }
                    updateView();
    }
    class PlaceAddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            placeAddEvent();
        }
    }
    class PlaceRemoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            placeRemoveEvent();
        }
    }
    
    class PlaceUpListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Raid raid = book.getActiveRaid();
            ArrayList<Place> places =raid.getPlaces();
            ArrayList<Place> sel = srpc.getSelectedInPlaces();
            for(Place place : sel){
                    for(int i=0;i<places.size()-1;i++){
                        if(places.get(i+1).equals(place)){
                            Place old = places.get(i);
                            if(!sel.contains(old)){
                                Place shift = places.get(i+1);
                                places.remove(i+1);
                                places.add(i, shift);
                            }
                        }
                    }
            }
            raid.setPlaces(places);
                    updateView();
        }
    }
    class PlaceDownListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Raid raid = book.getActiveRaid();
            ArrayList<Place> places =raid.getPlaces();
            ArrayList<Place> sel = srpc.getSelectedInPlaces();
            for(Place place : sel){
                    for(int i=places.size()-1;i>0;i--){
                        if(places.get(i-1).equals(place)){
                            Place old = places.get(i);
                            if(!sel.contains(old)){
                                Place shift = places.get(i-1);
                                places.remove(i-1);
                                places.add(i, shift);
                            }
                        }
                    }
            }
            raid.setPlaces(places);
                    updateView();
        }
    }
    
    class PlaceAddMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getClickCount()>1){
                placeAddEvent();
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    
    }
    class PlaceRemoveMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getClickCount()>1){
                placeRemoveEvent();
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    
    }

    class CalendarListener implements PropertyChangeListener{

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Raid raid = book.getActiveRaid();
            Date[] range = book.getValidDateRange(raid);
            Date date = null;
            if(evt.getNewValue() instanceof Date){
                date = ((Date)evt.getNewValue());
            }else if(evt.getNewValue() instanceof Calendar){
                date = ((Calendar)evt.getNewValue()).getTime();
            }
            
            if(date!=null){
                if((range[1]==null||(!date.after(range[1])))&&(range[0]==null||(!date.before(range[0])))){
                    book.changeRaidDate(raid, date);
                    book.setActiveRaid(date);
                    updateView();
                }
            }
        }
    }
    
    public void updateView(){
        LinkedList<Date> dates = book.getRaidDates();
        Object[][] datas = new Object[dates.size()][3];
        
        
        for(int i=0;i<dates.size();i++){
            Raid showRaid = book.getRaid(dates.get(dates.size()-1-i));
            ArrayList<Place> places = showRaid.getPlaces();
            String str = "";
            if(places.isEmpty()){
                str= "-";
            }else{
                int size = places.size();
                for(int j=0;j<size;j++){
                    Place pl=places.get(j);
                    str += Raid.getPlaceAlias(pl);
                    if(j<size-1){
                        str+="/";
                    }
                }
            }
            datas[i][0]= str;
            
            int[] cnt = {0,0};
            for(String id: showRaid.getPlayerIds()){
                Player player = showRaid.getPlayerByID(id);
                if(player.getState().equals(RaidState.TEILG)){
                    cnt[0]++;
                }else if(player.getState().equals(RaidState.ERSATZ)){
                    cnt[1]++;
                }
                
            }
            String countCell = "("+cnt[0]+"/"+cnt[1]+")";
            
            datas[i][1]= countCell;
            datas[i][2]= showRaid.getDate();
        }
        rP.setContent(book.getActiveRaid().getDate(), datas, book.getActiveRaid().getComment());
        switch(rP.getTab()){
            case 0:
                srpc.updateView();
                break;
            case 1:
                sppc.updateView();
                break;
            case 2:
                lpc.updateView();
                break;
            default:
                spc.updateView();
        }
    }
}

