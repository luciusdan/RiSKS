/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Console;

import GUI.ErrorFrame;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Dirk
 */
public class ErrStream extends CustomOutputStream{
    public ErrStream(PrintStream oldStream ,ErrorFrame ef){
        super(oldStream, ef);
        this.color = Color.red;
    }
    
    @Override
    public void write(int b) throws IOException {
        super.write(b);
        ef.setVisible(true);
    }
}
