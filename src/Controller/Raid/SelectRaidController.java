/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Raid;

import Controller.ControllerInterface;
import Data.Book;
import Data.Raid;
import Data.Raid.Place;
import GUI.Raid.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class SelectRaidController implements ControllerInterface {
    private RaidEditPanel rp;
    private Book book;
    private Place[] places;
    public SelectRaidController(RaidEditPanel rp, Book book){
        this.book = book;
        this.rp = rp;
        
        places = Raid.Place.values();
        String[][] pl = new String[places.length][2];
        for(int i=0;i<places.length;i++){
            Place place = places[i];
            pl[i][0] = Raid.getPlaceAlias(place);
            pl[i][0] = Raid.getPlaceName(place);
        }
        rp.setRaidPlaces(null,pl);
    }
    
    
    public void addCalendarListener(PropertyChangeListener l){
        rp.addCalendarListener(l);
    }

    public void addPlaceAddListener(ActionListener l) {
        rp.addPlaceAddListener(l);
    }

    public void addPlaceRemoveListener(ActionListener l) {
        rp.addPlaceRemoveListener(l);
    }

    public void addPlaceUpListener(ActionListener l) {
        rp.addPlaceUpListener(l);
    }

    public void addPlaceDownListener(ActionListener l) {
        rp.addPlaceDownListener(l);
    }
    
    public void updateView(){
        Raid raid = book.getActiveRaid();
        rp.setDate(raid.getDate(), book.getValidDateRange(raid));
        
        ArrayList<Place> in = raid.getPlaces();
        ArrayList<Place> out = new ArrayList<>();
        for(Place p:places){
            if(!in.contains(p)){
                out.add(p);
            }
        }
        String[][] inArray = new String[in.size()][2];
        for(int i=0;i<in.size();i++){
            Place p = in.get(i);
            inArray[i][0]= Raid.getPlaceAlias(p);
            inArray[i][1]= Raid.getPlaceName(p);
        }
        String[][] outArray = new String[out.size()][2];
        for(int i=0;i<out.size();i++){
            Place p = out.get(i);
            outArray[i][0]= Raid.getPlaceAlias(p);
            outArray[i][1]= Raid.getPlaceName(p);
        }
        rp.setRaidPlaces(inArray, outArray);
    }

    public ArrayList<Place> getSelectedInPlaces() {
        LinkedList<String> names = rp.getSelectedInPlaces();
        return convertToPlaces(names);
    }

    public ArrayList<Place> getSelectedOutPlaces() {
        LinkedList<String> names = rp.getSelectedOutPlaces();
        return convertToPlaces(names);
    }
    
    private ArrayList<Place> convertToPlaces(LinkedList<String> names){
        Place[] result = new Place[names.size()];
        int i=0;
        for(String str : names){
            result[i] = Raid.getPlace(str);
            
            i++;
        }
        return new ArrayList<>(Arrays.asList(result));
    }

    public void addPlaceAddMouseListener(MouseListener l) {
        rp.addPlaceAddMouseListener(l);
    }

    public void addPlaceRemoveMouseListener(MouseListener l) {
        rp.addPlaceRemoveMouseListener(l);
    }
}
