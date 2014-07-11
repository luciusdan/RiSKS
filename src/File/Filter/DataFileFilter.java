/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File.Filter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Dirk
 */
public class DataFileFilter implements java.io.FileFilter {
        SimpleDateFormat sdf;
        public DataFileFilter(SimpleDateFormat sdf){
            super();
            this.sdf = sdf;
        }
        @Override
        public boolean accept(File f) {
                if (f.isDirectory()) {
                    return false;
                }
                String name = f.getName().toLowerCase();
                if(name.endsWith("xls")){
                    try {
                        String sub = name.substring(5, name.length()-4);
                        sdf.parse(sub);
                        return true;
                    } catch (ParseException ex) {
                        //ist einfach keine Data File
                        return false;
                    }
                }else{
                    return false;
                }
        }
    }