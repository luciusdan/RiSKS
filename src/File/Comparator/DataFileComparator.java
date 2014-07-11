/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File.Comparator;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author Dirk
 */
public class DataFileComparator implements Comparator<File> { 
        @Override
        public int compare(File o1, File o2) {
            String a = o1.getName();
            String b = o2.getName();
            return a.compareTo(b);
        }
    }