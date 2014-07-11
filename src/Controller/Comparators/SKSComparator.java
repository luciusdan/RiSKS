/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Comparators;

import Data.Player;
import java.util.Comparator;

/**
 *
 * @author Dirk
 */
public class SKSComparator implements Comparator<Player> {

        @Override
        public int compare(Player a, Player b) {
            double sksA = a.getSksPos();
            double sksB = b.getSksPos();

            if (sksA < sksB) {
                return -1;
            }
            if (sksA > sksB) {
                return 1;
            }

            return 0;
        }
    }
