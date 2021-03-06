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

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Panel im MetaPanel RaidPanel zum ändern des Datum und der besuchten Orte des
 * Raids.
 * @author Dirk
 */
public class RaidEditPanel extends javax.swing.JPanel {

    /**
     * Konstruktor des RaidEditPanel
     */
    public RaidEditPanel() {
        initComponents();
        TableColumn col =inTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(65);
        col =inTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(465);
        
        
        col =outTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(65);
        col =outTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(465);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        placePanel = new javax.swing.JPanel();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        outPlacePanel = new javax.swing.JPanel();
        outScrollPane = new javax.swing.JScrollPane();
        outTable = new javax.swing.JTable();
        inPlacePanel = new javax.swing.JPanel();
        inScrollPane = new javax.swing.JScrollPane();
        inTable = new javax.swing.JTable();
        datePanel = new javax.swing.JPanel();
        calendar = new com.toedter.calendar.JCalendar();

        setPreferredSize(new java.awt.Dimension(790, 497));

        placePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Orte:"));

        upButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Arrow/Arrow-Up-icon.png"))); // NOI18N

        downButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Arrow/Arrow-Down-icon.png"))); // NOI18N

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Glyph/Glyph-Add-icon.png"))); // NOI18N

        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Glyph/Glyph-Remove.png"))); // NOI18N

        outPlacePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("nicht besuchte Orte")));

        outTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kürzel", "Ort"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        outTable.setDropMode(javax.swing.DropMode.ON_OR_INSERT);
        outTable.setShowHorizontalLines(false);
        outTable.getTableHeader().setReorderingAllowed(false);
        outScrollPane.setViewportView(outTable);

        org.jdesktop.layout.GroupLayout outPlacePanelLayout = new org.jdesktop.layout.GroupLayout(outPlacePanel);
        outPlacePanel.setLayout(outPlacePanelLayout);
        outPlacePanelLayout.setHorizontalGroup(
            outPlacePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(outScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
        );
        outPlacePanelLayout.setVerticalGroup(
            outPlacePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(outScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
        );

        inPlacePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("besuchte Orte:")));

        inTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kürzel", "Ort"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inTable.setDropMode(javax.swing.DropMode.ON_OR_INSERT);
        inTable.setShowHorizontalLines(false);
        inTable.getTableHeader().setReorderingAllowed(false);
        inScrollPane.setViewportView(inTable);

        org.jdesktop.layout.GroupLayout inPlacePanelLayout = new org.jdesktop.layout.GroupLayout(inPlacePanel);
        inPlacePanel.setLayout(inPlacePanelLayout);
        inPlacePanelLayout.setHorizontalGroup(
            inPlacePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(inScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        inPlacePanelLayout.setVerticalGroup(
            inPlacePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, inScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout placePanelLayout = new org.jdesktop.layout.GroupLayout(placePanel);
        placePanel.setLayout(placePanelLayout);
        placePanelLayout.setHorizontalGroup(
            placePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(placePanelLayout.createSequentialGroup()
                .add(placePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(outPlacePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(inPlacePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(placePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(downButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(upButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(addButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(removeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        placePanelLayout.setVerticalGroup(
            placePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, placePanelLayout.createSequentialGroup()
                .add(placePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(placePanelLayout.createSequentialGroup()
                        .add(inPlacePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(placePanelLayout.createSequentialGroup()
                        .add(48, 48, 48)
                        .add(upButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(downButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 109, Short.MAX_VALUE)))
                .add(placePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(placePanelLayout.createSequentialGroup()
                        .add(addButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeButton))
                    .add(outPlacePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(20, 20, 20))
        );

        datePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Datum:")));

        org.jdesktop.layout.GroupLayout datePanelLayout = new org.jdesktop.layout.GroupLayout(datePanel);
        datePanel.setLayout(datePanelLayout);
        datePanelLayout.setHorizontalGroup(
            datePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(calendar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
        );
        datePanelLayout.setVerticalGroup(
            datePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(calendar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(placePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(datePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(datePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(placePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private com.toedter.calendar.JCalendar calendar;
    private javax.swing.JPanel datePanel;
    private javax.swing.JButton downButton;
    private javax.swing.JPanel inPlacePanel;
    private javax.swing.JScrollPane inScrollPane;
    private javax.swing.JTable inTable;
    private javax.swing.JPanel outPlacePanel;
    private javax.swing.JScrollPane outScrollPane;
    private javax.swing.JTable outTable;
    private javax.swing.JPanel placePanel;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables

    
    public void addCalendarListener(PropertyChangeListener l){
        calendar.addPropertyChangeListener(l);
    }
    
    public void addPlaceAddListener(ActionListener l) {
        addButton.addActionListener(l);
    }
    
    public void addPlaceAddMouseListener(MouseListener l) {
        outTable.addMouseListener(l);
    }

    public void addPlaceDownListener(ActionListener l) {
        downButton.addActionListener(l);
    }

    public void addPlaceRemoveListener(ActionListener l) {
        removeButton.addActionListener(l);
    }

    public void addPlaceRemoveMouseListener(MouseListener l) {
        inTable.addMouseListener(l);
    }

    public void addPlaceUpListener(ActionListener l) {
        upButton.addActionListener(l);
    }

    /**
     * Gibt die ausgewählten Orte in der angegebenen Reihenfolge zurück.
     * @return
     */
    public LinkedList<String> getSelectedInPlaces() {
        int[] rowInts = inTable.getSelectedRows();
        LinkedList<String> rows = new LinkedList<>();
        for(int i : rowInts){
            String str = (String) inTable.getValueAt(i, 0);
            rows.add(str);
        }
        return rows;
    }

    /**
     * Gibt die nicht ausgewählten Orte zurück.
     * @return
     */
    public LinkedList<String> getSelectedOutPlaces() {
        int[] rowInts = outTable.getSelectedRows();
        LinkedList<String> rows = new LinkedList<>();
        for(int i : rowInts){
            String str = (String) outTable.getValueAt(i, 0);
            rows.add(str);
        }
        return rows;
    }
    
    /**
     * Setzt das Datum des JCalendars auf den übergebenen Werts und
     * zusätzlich den Spielraum der Datumauswahl des Kalenders.
     * @param date neues Datum des JCalenders
     * @param range min- und max- Date des JCalenders
     */
    public void setDate(Date date, Date[] range) {
        calendar.setMinSelectableDate(range[0]);
        calendar.setMaxSelectableDate(range[1]);
        calendar.setDate(date);
    }

    /**
     * Setzt die Raidorte in den in-&outTabelle 
     * {{Placekürzel, Placename}*}
     * @param inArray  
     * @param outArray
     */
    public void setRaidPlaces(String[][] inArray, String[][] outArray) {
        DefaultTableModel inDTM = (DefaultTableModel) inTable.getModel();
        DefaultTableModel outDTM = (DefaultTableModel) outTable.getModel();
        
        while(inDTM.getRowCount()>0){
            inDTM.removeRow(0);
        }
        
        while(outDTM.getRowCount()>0){
            outDTM.removeRow(0);
        }
        
        if(inArray!=null&&inArray.length>0){
            for(int i=0; i<inArray.length;i++){
                Object[] row = {inArray[i][0], inArray[i][1]};
                if(!inArray[i][0].equals("-")){
                    inDTM.addRow(row);
                }
            }
        }
        
        if(outArray!=null&&outArray.length>0){
            for(int i=0; i<outArray.length;i++){
                Object[] row = {outArray[i][0], outArray[i][1]};
                if(!outArray[i][0].equals("-")){
                    outDTM.addRow(row);
                }
            }
        }
    }
}
