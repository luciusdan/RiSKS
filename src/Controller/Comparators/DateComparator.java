/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Comparators;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Dirk
 */
public class DateComparator implements Comparator<Date> {

        @Override
        public int compare(Date a, Date b) {
            return a.compareTo(b);
        }
    }
