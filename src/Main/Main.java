package Main;
/*
 * RiftSKS ist ein Programm zum führen eines Raidkaders mit der Absicht
 * das SKS-Verfahren bei der Lootverteilung anzuwenden.
 * Copyright (C) 2013  Dirk Evers
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */

import Controller.Console.ErrorController;
import Controller.MainController;
import GUI.WaitScreen;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dirk
 */
public class Main {
    /**
     * main-Funktion des Programmes
     * Außerdem wird das Look-and-Feel festgelegt.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ErrorController ec = new ErrorController();
                Logger logger = LogManager.getRootLogger();
                
// ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
// PatternLayout layout = new PatternLayout("%d{HH:mm:ss:SSS} %-5p(%C): %m%n");
                
                try {
                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
                } catch (Exception e) {
                    logger.warn("Fehler beim setzten des Look&Feel's auf Nimbus", e);
                    JFrame frame = new JFrame("DateErrorDialog");
                    JOptionPane.showMessageDialog(
                            frame,
                            "Fehler beim laden des LookAndFeel\n" +
                                e.getLocalizedMessage(),
                            "Fehler beim laden des LookAndFeel!",
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                
                logger.debug("Starte Ladescreen.");
                WaitScreen ws = new WaitScreen();
                ws.setVisible(true);
                MainController mainController = new MainController(ws,ec);
            }
        });
    }
}
