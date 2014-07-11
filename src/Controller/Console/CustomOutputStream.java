/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Console;
 
import GUI.ErrorFrame;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
 
public class CustomOutputStream extends OutputStream {
    protected ErrorFrame ef;
    private OutputStream oldStream;
    protected Color color;
     
    public CustomOutputStream(OutputStream oldStream ,ErrorFrame ef) {
        this.ef = ef;
        this.oldStream = oldStream;
    }
     
    @Override
    public void write(int b) throws IOException {
        try {
            write((char)b,color, false);
        } catch (BadLocationException ex) {
            throw new IOException(ex.getMessage());
        }
        oldStream.write(b);
    }
    
    private void write(char c, Color color, Boolean bold) throws
            BadLocationException{
        
        String text = ""+c;
        JTextPane console = ef.getConsole();
        int start = console.getStyledDocument().getLength();
        
        if(color!=null||bold){
            Style style = console.addStyle("current", null);
            if(bold){
                StyleConstants.setBold(style, true);    
            }
            if(color!=null){
                StyleConstants.setForeground(style, color);    
            }
            console.getStyledDocument().insertString(start, text, style);
            console.getStyledDocument().setCharacterAttributes(start,
                    text.length(), style, false);
        }else{
            console.getStyledDocument().insertString(start, text, null);
        }
        console.setCaretPosition(console.getDocument().getLength());
    }
}
