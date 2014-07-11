/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Console;

import GUI.ErrorFrame;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import javax.swing.JTextPane;

/**
 *
 * @author Dirk
 */
public class ErrorController {
  ErrorFrame ef;
  HashMap<String,OutputStream> streams;
    
    public ErrorController(){
        ef = new ErrorFrame();
        final PrintStream errStream = new PrintStream(new ErrStream(System.err, ef));
        final PrintStream outStream = new PrintStream(new CustomOutputStream(System.out, ef));
        System.setOut(outStream);
        System.setErr(errStream);
        
        streams = new HashMap<>();
        streams.put("output", outStream);
        streams.put("error", errStream);
    }
    
    public void setVisible() {
        ef.setVisible(true);
    }

    public JTextPane getPane() {
        return ef.getConsole();
    }
}
