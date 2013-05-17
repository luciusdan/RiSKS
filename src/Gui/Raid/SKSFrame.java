/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.Raid;

import Data.Member;
import Data.Member.State;
import Data.Raid;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Dirk
 */
public class SKSFrame extends javax.swing.JFrame {

    /**
     * Creates new form SKSFrame
     */
    public SKSFrame(Raid raid) {
        initComponents();
        LinkedList<Member> members = raid.getFullSKS();
        
        Object[] newRow;
        DefaultTableModel raidModel = (DefaultTableModel)memberTable.getModel();
        TableColumn col =memberTable.getColumnModel().getColumn(0);
        col.setMaxWidth(30);
        col.setResizable(false);
        for (int i=0;i<members.size();i++){
            Member member = members.get(i);
            newRow = new Object[6];
            newRow[0]= i+1;
            newRow[1]= member.getName();
            newRow[2]= member.getStringRole();
            newRow[3]= member.getStringMainSpec();
            newRow[4]= member.getStringSecondSpec();
            newRow[5]= member.getRaidCount()+(member.getState().equals(State.TEILG)?1:0);
            raidModel.addRow(newRow);
        }
        closeButton.setSelected(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        underground = new javax.swing.JScrollPane();
        memberTable = new javax.swing.JTable();

        setTitle("SKS-Liste");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        closeButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                closeButtonKeyPressed(evt);
            }
        });

        memberTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Name", "Klasse", "Main Rolle", "Second Rolle", "SKS-Raid's"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        memberTable.setFocusable(false);
        memberTable.getTableHeader().setReorderingAllowed(false);
        underground.setViewportView(memberTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(underground, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(underground, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(closeButton))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void closeButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_closeButtonKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER|| evt.getKeyCode()==KeyEvent.VK_SPACE){
        this.setVisible(false);
        this.dispose();
        }
    }//GEN-LAST:event_closeButtonKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTable memberTable;
    private javax.swing.JScrollPane underground;
    // End of variables declaration//GEN-END:variables
}
