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
package GUI.Overview;

import Controller.DateFormater;
import Data.Player;
import Data.Player.RaidState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Dirk
 */
public class PlayerOverviewPanel extends javax.swing.JPanel {
    HashMap<RaidState,Color> stateColor;
    /**
     * Creates new form OverviewPanel
     */
    public PlayerOverviewPanel() {
        initComponents();
        stateColor = new HashMap<>();
        addStandardColors();
        setPopupColors();
        TableColumn col =playerTable.getColumnModel().getColumn(0);
        int size = 70;
        col.setPreferredWidth(size);
        col =playerTable.getColumnModel().getColumn(1);
        size = 110;
        col.setPreferredWidth(size);
        col =playerTable.getColumnModel().getColumn(2);
        size = 40;
        col.setPreferredWidth(size);
        col =playerTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(size);
        col =playerTable.getColumnModel().getColumn(4);
        col.setPreferredWidth(size);
        playerDataTable.setDefaultRenderer( Object.class, new ColoredTableCellRenderer() );
        playerScrollPane.setVerticalScrollBar(playerDataScrollPane.getVerticalScrollBar());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        createPlayerButton = new javax.swing.JMenuItem();
        changePlayerButton = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JPopupMenu.Separator();
        removePlayerButton = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JPopupMenu.Separator();
        separator3 = new javax.swing.JPopupMenu.Separator();
        hideButton = new javax.swing.JCheckBoxMenuItem();
        percentButton = new javax.swing.JCheckBoxMenuItem();
        ColorPopupMenu = new javax.swing.JPopupMenu();
        thereColorMenuButton = new javax.swing.JMenuItem();
        standbyColorMenuButton = new javax.swing.JMenuItem();
        awayColorMenuButton = new javax.swing.JMenuItem();
        offColorMenuButton = new javax.swing.JMenuItem();
        deadColorMenuButton = new javax.swing.JMenuItem();
        jColorChooser1 = new javax.swing.JColorChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        playerDataScrollPane = new javax.swing.JScrollPane();
        playerDataTable = new javax.swing.JTable();
        playerScrollPane = new javax.swing.JScrollPane();
        playerTable = new javax.swing.JTable();

        createPlayerButton.setText("Spieler hinzufügen");
        popupMenu.add(createPlayerButton);

        changePlayerButton.setText("Spieler ändern");
        popupMenu.add(changePlayerButton);
        popupMenu.add(separator1);

        removePlayerButton.setText("Spieler entfernen");
        removePlayerButton.setToolTipText("");
        popupMenu.add(removePlayerButton);
        popupMenu.add(separator2);
        popupMenu.add(separator3);

        hideButton.setSelected(true);
        hideButton.setText("zeige nur Aktive Spieler");
        popupMenu.add(hideButton);

        percentButton.setSelected(true);
        percentButton.setText("in Prozent");
        popupMenu.add(percentButton);

        thereColorMenuButton.setText("Anwesend");
        thereColorMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thereColorMenuButtonActionPerformed(evt);
            }
        });
        ColorPopupMenu.add(thereColorMenuButton);

        standbyColorMenuButton.setText("Ersatz");
        ColorPopupMenu.add(standbyColorMenuButton);

        awayColorMenuButton.setText("Abwesend");
        ColorPopupMenu.add(awayColorMenuButton);

        offColorMenuButton.setText("Abgemeldet");
        ColorPopupMenu.add(offColorMenuButton);

        deadColorMenuButton.setText("nicht Vorhanden");
        ColorPopupMenu.add(deadColorMenuButton);

        setNextFocusableComponent(this);
        setPreferredSize(new java.awt.Dimension(1151, 546));

        jSplitPane1.setDividerLocation(300);

        playerDataScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        playerDataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        playerDataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        playerDataTable.setCellSelectionEnabled(true);
        playerDataTable.setComponentPopupMenu(ColorPopupMenu);
        playerDataTable.setEnabled(false);
        playerDataTable.setSelectionBackground(new java.awt.Color(50, 153, 255));
        playerDataTable.getTableHeader().setReorderingAllowed(false);
        playerDataScrollPane.setViewportView(playerDataTable);

        jSplitPane1.setRightComponent(playerDataScrollPane);

        playerScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        playerScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        playerScrollPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                playerScrollPanePropertyChange(evt);
            }
        });

        playerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Gilde", "Anw.", "Ers.", "Abw."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playerTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        playerTable.setComponentPopupMenu(popupMenu);
        playerTable.setNextFocusableComponent(playerTable);
        playerTable.getTableHeader().setReorderingAllowed(false);
        playerScrollPane.setViewportView(playerTable);

        jSplitPane1.setLeftComponent(playerScrollPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1141, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void playerScrollPanePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_playerScrollPanePropertyChange

    }//GEN-LAST:event_playerScrollPanePropertyChange

    private void thereColorMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thereColorMenuButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thereColorMenuButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu ColorPopupMenu;
    private javax.swing.JMenuItem awayColorMenuButton;
    private javax.swing.JMenuItem changePlayerButton;
    private javax.swing.JMenuItem createPlayerButton;
    private javax.swing.JMenuItem deadColorMenuButton;
    private javax.swing.JCheckBoxMenuItem hideButton;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenuItem offColorMenuButton;
    private javax.swing.JCheckBoxMenuItem percentButton;
    private javax.swing.JScrollPane playerDataScrollPane;
    private javax.swing.JTable playerDataTable;
    private javax.swing.JScrollPane playerScrollPane;
    private javax.swing.JTable playerTable;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem removePlayerButton;
    private javax.swing.JPopupMenu.Separator separator1;
    private javax.swing.JPopupMenu.Separator separator2;
    private javax.swing.JPopupMenu.Separator separator3;
    private javax.swing.JMenuItem standbyColorMenuButton;
    private javax.swing.JMenuItem thereColorMenuButton;
    // End of variables declaration//GEN-END:variables
    
    public void addPercentListener(ActionListener l){
    this.percentButton.addActionListener(l);
    }
    
    public void addPlayerAddListener(ActionListener l) {
        createPlayerButton.addActionListener(l);
    }
    
    public void addPlayerEditListener(ActionListener l) {
        changePlayerButton.addActionListener(l);
    }

    public void addPlayerRemoveListener(ActionListener l) {
        removePlayerButton.addActionListener(l);
    }
    
    public void addPlayerTableListener(ListSelectionListener l){
        playerTable.getSelectionModel().addListSelectionListener(l);
    }
    
    public void addPopupListener(ActionListener l){
    hideButton.addActionListener(l);
    }

    /**
     * Gibt den ausgewählten Spielernamen wieder.
     * @return
     */
    public String getSelectedPlayerName() {
        JTable table = playerTable;
        int c = table.getColumn("Name").getModelIndex();
        int r = table.getSelectedRow();
        if(r>=0){
                return (String)table.getValueAt(r, c);
            }else{
                return null;
            }
    }
    
     /**
     * Setzt den Inhalt des Panels mit den übergebenen Daten.
     * @param in Daten für die Tabellen.
     * @param dates Raiddaten.
     */
    public void setContent(Object[] in, LinkedList<Date> dates) {
        Object[] content = in;//filterContent(datas, book);
        
        DefaultTableModel ptm = (DefaultTableModel) playerTable.getModel();
        while(ptm.getRowCount()>0){
            ptm.removeRow(0);
        }

        String[] col = new String[dates.size()];
        Color[][] data = new Color[content.length][col.length];
        for(int i=0;i<col.length;i++){
            Date date = dates.get(col.length-i-1);
            col[i] = DateFormater.simpleRaidDateFormat.format(date);
        }
         for(int i=0;i<content.length;i++){
             Object[] iC = (Object[]) content[i];
             String name = (String) iC[0];
             String guild = (String) iC[1];
             Integer[] indizies = (Integer[]) iC[2];
             Player.RaidState[] states = (Player.RaidState[]) iC[3];
             
             Object[] infoRow  = new Object[5];
             
             infoRow[0] = name;
             infoRow[1] = guild;
             if(percentButton.getState()){
                 int max = indizies[0]+indizies[1]+indizies[2];
                 int per0 = (100*indizies[0]/max);
                 int per1 = (100*indizies[1]/max);
                 int per2 = (100*indizies[2]/max);
                 infoRow[2] = per0+"%";
                 infoRow[3] = per1+"%";
                 infoRow[4] = per2+"%";
             }else{
                 infoRow[2] = indizies[0]+"";
                 infoRow[3] = indizies[1]+"";
                 infoRow[4] = indizies[2]+"";
             }
             
             
             ptm.addRow(infoRow);
             
             for(int j=0;j<col.length;j++){
             //for(int j=col.length-1;j>=0;j--){
                 RaidState state = states[col.length-j-1];
                 if(state!=null){
                     data[i][j] = stateColor.get(state);
                 }else{
                            data[i][j]= stateColor.get(RaidState.GELOESCHT);
                 }
             }
             
        DefaultTableModel pdtm = new DefaultTableModel(data, col);
        playerDataTable.setModel(pdtm);
        }
        
        TableColumnModel tcm = playerDataTable.getColumnModel();
        for (int i=0; i<tcm.getColumnCount();i++){
            TableColumn tc =tcm.getColumn(i);
            tc.setPreferredWidth(50);
            
        }
    }
    
    /**
     * Sperrt/entsperrt die Funktionen,welche die SKS-Liste beeinflussen,
     * abhängig vom übergebenen Wert.
     * @param bool sollen die Funktionen gesperrt werden
     */
    public void setEditable(boolean bool){
        changePlayerButton.setEnabled(bool);
        removePlayerButton.setEnabled(bool);
    }
    
    /**
     * CellRenderer zum darstellen der Farben als Farbe.
     */
    class ColoredTableCellRenderer extends DefaultTableCellRenderer{
        @Override
        public void setValue( Object value ){
            if (value instanceof Color){
                Color color = (Color)value;
                this.setBorder(BorderFactory.createLineBorder(Color.gray));
                setBackground(color);
                setText("");
            }
            else{
                setBackground(Color.WHITE);
                super.setValue( value );
            }
        }
    }
    
    public void setColors(HashMap <RaidState,Color> map){
        stateColor = map;
        addStandardColors();
        setPopupColors();
    }
    
    private void addStandardColors(){
        stateColor = new HashMap<>();
        if((stateColor.get(RaidState.TEILG))==null){
            stateColor.put(RaidState.TEILG, Color.GREEN);
        }
        if((stateColor.get(RaidState.ERSATZ))==null){
            stateColor.put(RaidState.ERSATZ, Color.YELLOW);
        }
        if((stateColor.get(RaidState.ABGEMELDET))==null){
            stateColor.put(RaidState.ABGEMELDET, Color.WHITE);
        }
        if((stateColor.get(RaidState.ABWESEND))==null){
            stateColor.put(RaidState.ABWESEND, new Color(255,76,76));
        }
        if((stateColor.get(RaidState.GELOESCHT))==null){
            stateColor.put(RaidState.GELOESCHT, Color.LIGHT_GRAY);
        }
    }
    
    private void setPopupColors(){
        int size = 20;
        
        for(RaidState state :stateColor.keySet()){
            JMenuItem item = getColorMenuItem(state);
            if(item!=null){
                BufferedImage image = new BufferedImage(size, size,BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = image.createGraphics();
                g.setColor(stateColor.get(state));
                g.fillRect(0, 0, size, size);
                item.setIcon(new ImageIcon(image));
            }
        }
    }
    
    private JMenuItem getColorMenuItem(RaidState raidState){
        switch(raidState){
            case TEILG:
                return thereColorMenuButton;
            case ERSATZ:
                return standbyColorMenuButton;
            case ABGEMELDET:
                return offColorMenuButton;
            case ABWESEND:
                return awayColorMenuButton;
            case GELOESCHT:
                return deadColorMenuButton;
            default:
                return null;
        }
    }
}
