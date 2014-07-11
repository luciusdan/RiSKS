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
package GUI.Raid;

import javax.swing.DefaultListSelectionModel;

/**
 * ListSelectionModel, das setSelectionInterval lahmlegt und somit die übliche
 * Auswahl in einer Tabelle blockiert, jedoch weiterhin mit setSelection
 * ermöglicht.
 * @author Dirk
 */
public class CustomListSelectionModel extends DefaultListSelectionModel{
        public CustomListSelectionModel() {
            super();
        }
        
        public void setSelection(int index){
            super.clearSelection();
            super.setSelectionInterval(index, index);
        }
        
        @Override
        public void setSelectionInterval(int index0, int index1){
            //DO NOTHING
        }
    }
