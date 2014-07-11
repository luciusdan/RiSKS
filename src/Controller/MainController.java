/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Console.ErrorController;
import Controller.Threading.ControllerThread;
import GUI.MainFrame;
import GUI.WaitScreen;

/**
 *
 * @author Dirk
 */
public class MainController {


    public MainController(final WaitScreen ws, final ErrorController ec) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mf = new MainFrame();
                java.awt.EventQueue.invokeLater(new ControllerThread(mf,ws,ec));
            }
        });
    }
}
