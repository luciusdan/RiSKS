//TODO
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.Cader;

import Data.Member;

/**
 *
 * @author Dirk
 */
public class CreatePlayerFrame extends javax.swing.JFrame {


    /**
     * Creates new form createPlayerFrame
     */
    public CreatePlayerFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roleBG = new javax.swing.ButtonGroup();
        specBG = new javax.swing.ButtonGroup();
        supRB1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        nameField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        warriorRB = new javax.swing.JRadioButton();
        rougeRB = new javax.swing.JRadioButton();
        clericRB = new javax.swing.JRadioButton();
        mageRB = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        tankRB = new javax.swing.JRadioButton();
        ddRB = new javax.swing.JRadioButton();
        healRB = new javax.swing.JRadioButton();
        supRB = new javax.swing.JRadioButton();
        finButton = new javax.swing.JButton();
        abortButton = new javax.swing.JButton();

        specBG.add(supRB1);
        supRB1.setText("Support");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameField.setText("hier Namen eingeben");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameField)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        roleBG.add(warriorRB);
        warriorRB.setText("Krieger");

        roleBG.add(rougeRB);
        rougeRB.setText("Schurke");

        roleBG.add(clericRB);
        clericRB.setSelected(true);
        clericRB.setText("Kleriker");

        roleBG.add(mageRB);
        mageRB.setText("Magier");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rougeRB)
                    .addComponent(clericRB)
                    .addComponent(mageRB)
                    .addComponent(warriorRB))
                .addGap(0, 121, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(warriorRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rougeRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clericRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mageRB))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        specBG.add(tankRB);
        tankRB.setText("Tank");

        specBG.add(ddRB);
        ddRB.setSelected(true);
        ddRB.setText("Schaden");

        specBG.add(healRB);
        healRB.setText("Heiler");

        specBG.add(supRB);
        supRB.setText("Support");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tankRB)
                    .addComponent(ddRB)
                    .addComponent(healRB)
                    .addComponent(supRB))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(tankRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ddRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(healRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supRB))
        );

        finButton.setText("O.K.");

        abortButton.setText("Abbrechen");
        abortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abortButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(finButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abortButton)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finButton)
                    .addComponent(abortButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abortButtonActionPerformed
       this.setVisible(false);
    }//GEN-LAST:event_abortButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abortButton;
    private javax.swing.JRadioButton clericRB;
    private javax.swing.JRadioButton ddRB;
    private javax.swing.JButton finButton;
    private javax.swing.JRadioButton healRB;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton mageRB;
    private javax.swing.JTextField nameField;
    private javax.swing.ButtonGroup roleBG;
    private javax.swing.JRadioButton rougeRB;
    private javax.swing.ButtonGroup specBG;
    private javax.swing.JRadioButton supRB;
    private javax.swing.JRadioButton supRB1;
    private javax.swing.JRadioButton tankRB;
    private javax.swing.JRadioButton warriorRB;
    // End of variables declaration//GEN-END:variables

    public void newPlayer(){
        nameField.setText(" hier Namen eingeben");
        roleBG.setSelected(clericRB.getModel(), true);
        specBG.setSelected(ddRB.getModel(), true);
        this.setVisible(true);
    }
    public void editPlayer(Member player){
        nameField.setText(player.getName());
        switch(player.getRole()){
            case WARRIOR:
                roleBG.setSelected(warriorRB.getModel(), true);
                break;
            case ROUGE:
                roleBG.setSelected(rougeRB.getModel(), true);
                break;
            case CLERIC:
                roleBG.setSelected(clericRB.getModel(), true);
                break;
            case MAGE:
                roleBG.setSelected(mageRB.getModel(), true);
                break;
        }
        switch(player.getMainSpec()){
            case TANK:
                specBG.setSelected(tankRB.getModel(), true);
                break;
            case DD:
                specBG.setSelected(ddRB.getModel(), true);
                break;
            case HEAL:
                specBG.setSelected(healRB.getModel(), true);
                break;
            case SUPPORT:
                specBG.setSelected(supRB.getModel(), true);
                break;
        }
        this.setVisible(true);
    }
}
