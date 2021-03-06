/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Edit;

import Data.Player.Calling;
import Data.Player.Spec;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 *
 * @author Dirk
 */
public class PlayerEditFrame extends javax.swing.JFrame {

    private javax.swing.JTextField textField;
    /**
     * Creates new form NewJFrame
     */
    public PlayerEditFrame() {
        this.setLocationRelativeTo(null);
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Logo/icon.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roleButtonGroup = new javax.swing.ButtonGroup();
        mainSpecButtonGroup = new javax.swing.ButtonGroup();
        acceptButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        namePanel = new javax.swing.JPanel();
        nameComboBox = new javax.swing.JComboBox();
        guildPanel = new javax.swing.JPanel();
        guildComboBox = new javax.swing.JComboBox();
        rolePanel = new javax.swing.JPanel();
        warriorToggleButton = new javax.swing.JToggleButton();
        rogueToggleButton = new javax.swing.JToggleButton();
        clericToggleButton = new javax.swing.JToggleButton();
        mageToggleButton = new javax.swing.JToggleButton();
        specPanel = new javax.swing.JPanel();
        tankToggleButton = new javax.swing.JToggleButton();
        ddToggleButton = new javax.swing.JToggleButton();
        healToggleButton = new javax.swing.JToggleButton();
        supportToggleButton = new javax.swing.JToggleButton();
        secSupportToggleButton = new javax.swing.JToggleButton();
        secHealToggleButton = new javax.swing.JToggleButton();
        secDDToggleButton = new javax.swing.JToggleButton();
        secTankToggleButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        sksCheckBox = new javax.swing.JCheckBox();

        setTitle("Spieler bearbeiten");

        acceptButton.setText("OK");

        closeButton.setText("zurück");

        namePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Name:"));

        nameComboBox.setEditable(true);

        javax.swing.GroupLayout namePanelLayout = new javax.swing.GroupLayout(namePanel);
        namePanel.setLayout(namePanelLayout);
        namePanelLayout.setHorizontalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nameComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        namePanelLayout.setVerticalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, namePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(nameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        guildPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Gilde:"));

        javax.swing.GroupLayout guildPanelLayout = new javax.swing.GroupLayout(guildPanel);
        guildPanel.setLayout(guildPanelLayout);
        guildPanelLayout.setHorizontalGroup(
            guildPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(guildComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        guildPanelLayout.setVerticalGroup(
            guildPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guildPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(guildComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        rolePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Klasse:"));

        warriorToggleButton.setBackground(Data.Player.getCallingColor(Calling.WARRIOR));
        roleButtonGroup.add(warriorToggleButton);
        warriorToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_warrior_gray.png"))); // NOI18N
        warriorToggleButton.setToolTipText("zeige Krieger");
        warriorToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        warriorToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        warriorToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        warriorToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_warrior.png"))); // NOI18N

        rogueToggleButton.setBackground(Data.Player.getCallingColor(Calling.ROGUE));
        roleButtonGroup.add(rogueToggleButton);
        rogueToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_rogue_gray.png"))); // NOI18N
        rogueToggleButton.setToolTipText("zeige Schurken");
        rogueToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        rogueToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        rogueToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        rogueToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_rogue.png"))); // NOI18N

        clericToggleButton.setBackground(Data.Player.getCallingColor(Calling.CLERIC));
        roleButtonGroup.add(clericToggleButton);
        clericToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_cleric_gray.png"))); // NOI18N
        clericToggleButton.setSelected(true);
        clericToggleButton.setToolTipText("zeige Kleriker");
        clericToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        clericToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        clericToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        clericToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_cleric.png"))); // NOI18N

        mageToggleButton.setBackground(Data.Player.getCallingColor(Calling.MAGE));
        roleButtonGroup.add(mageToggleButton);
        mageToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_mage_gray.png"))); // NOI18N
        mageToggleButton.setToolTipText("zeige Magier");
        mageToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        mageToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        mageToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        mageToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Role/icon_mage.png"))); // NOI18N

        javax.swing.GroupLayout rolePanelLayout = new javax.swing.GroupLayout(rolePanel);
        rolePanel.setLayout(rolePanelLayout);
        rolePanelLayout.setHorizontalGroup(
            rolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rolePanelLayout.createSequentialGroup()
                .addComponent(warriorToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rogueToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clericToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mageToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        rolePanelLayout.setVerticalGroup(
            rolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rolePanelLayout.createSequentialGroup()
                .addGroup(rolePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(warriorToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rogueToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clericToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mageToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        specPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Rollen:"));

        tankToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        mainSpecButtonGroup.add(tankToggleButton);
        tankToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_tank_gray.png"))); // NOI18N
        tankToggleButton.setToolTipText("Primärrolle Tank");
        tankToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        tankToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        tankToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        tankToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_tank.png"))); // NOI18N
        tankToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tankToggleButtonStateChanged(evt);
            }
        });

        ddToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        mainSpecButtonGroup.add(ddToggleButton);
        ddToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_dd_gray.png"))); // NOI18N
        ddToggleButton.setSelected(true);
        ddToggleButton.setToolTipText("Primärrolle Schaden");
        ddToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        ddToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        ddToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        ddToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_dd.png"))); // NOI18N
        ddToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ddToggleButtonStateChanged(evt);
            }
        });

        healToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        mainSpecButtonGroup.add(healToggleButton);
        healToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_heal_gray.png"))); // NOI18N
        healToggleButton.setToolTipText("Primärrolle Heiler");
        healToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        healToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        healToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        healToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_heal.png"))); // NOI18N
        healToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                healToggleButtonStateChanged(evt);
            }
        });

        supportToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        mainSpecButtonGroup.add(supportToggleButton);
        supportToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_support_gray.png"))); // NOI18N
        supportToggleButton.setToolTipText("Primärrolle Supporter");
        supportToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        supportToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        supportToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        supportToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_support.png"))); // NOI18N
        supportToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                supportToggleButtonStateChanged(evt);
            }
        });

        secSupportToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        secSupportToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_support_gray.png"))); // NOI18N
        secSupportToggleButton.setToolTipText("Sekundärrolle Supporter");
        secSupportToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        secSupportToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        secSupportToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        secSupportToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_support.png"))); // NOI18N

        secHealToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        secHealToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_heal_gray.png"))); // NOI18N
        secHealToggleButton.setToolTipText("Sekundärrolle Heiler");
        secHealToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        secHealToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        secHealToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        secHealToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_heal.png"))); // NOI18N

        secDDToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        secDDToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_dd_gray.png"))); // NOI18N
        secDDToggleButton.setToolTipText("Sekundärrolle Schaden");
        secDDToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        secDDToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        secDDToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        secDDToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_dd.png"))); // NOI18N

        secTankToggleButton.setBackground(new java.awt.Color(191, 205, 219));
        secTankToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_tank_gray.png"))); // NOI18N
        secTankToggleButton.setToolTipText("Sekundärrolle Tank");
        secTankToggleButton.setMaximumSize(new java.awt.Dimension(35, 35));
        secTankToggleButton.setMinimumSize(new java.awt.Dimension(35, 35));
        secTankToggleButton.setPreferredSize(new java.awt.Dimension(35, 35));
        secTankToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Resource/Images/Spec/icon_tank.png"))); // NOI18N

        javax.swing.GroupLayout specPanelLayout = new javax.swing.GroupLayout(specPanel);
        specPanel.setLayout(specPanelLayout);
        specPanelLayout.setHorizontalGroup(
            specPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(specPanelLayout.createSequentialGroup()
                .addGroup(specPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(specPanelLayout.createSequentialGroup()
                        .addComponent(tankToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ddToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(healToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supportToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(specPanelLayout.createSequentialGroup()
                        .addComponent(secTankToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secDDToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secHealToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(secSupportToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        specPanelLayout.setVerticalGroup(
            specPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, specPanelLayout.createSequentialGroup()
                .addGroup(specPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tankToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ddToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(healToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supportToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(specPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secTankToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secDDToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secHealToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secSupportToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sksCheckBox.setSelected(true);
        sksCheckBox.setText("an das Ende der Sks wiederbeleben");
        sksCheckBox.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rolePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(guildPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sksCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(specPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(acceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rolePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(specPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(guildPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sksCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(closeButton)
                            .addComponent(acceptButton))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tankToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tankToggleButtonStateChanged
        secTankToggleButton.setEnabled(!tankToggleButton.isSelected());
        if(tankToggleButton.isSelected()){
            secTankToggleButton.setSelected(false);
        }
    }//GEN-LAST:event_tankToggleButtonStateChanged

    private void ddToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ddToggleButtonStateChanged
        secDDToggleButton.setEnabled(!ddToggleButton.isSelected());
        if(ddToggleButton.isSelected()){
            secDDToggleButton.setSelected(false);
        }
    }//GEN-LAST:event_ddToggleButtonStateChanged

    private void healToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_healToggleButtonStateChanged
        secHealToggleButton.setEnabled(!healToggleButton.isSelected());
        if(healToggleButton.isSelected()){
            secHealToggleButton.setSelected(false);
        }
    }//GEN-LAST:event_healToggleButtonStateChanged

    private void supportToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_supportToggleButtonStateChanged
         secSupportToggleButton.setEnabled(!supportToggleButton.isSelected());
        if(supportToggleButton.isSelected()){
            secSupportToggleButton.setSelected(false);
        }
    }//GEN-LAST:event_supportToggleButtonStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JToggleButton clericToggleButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JToggleButton ddToggleButton;
    private javax.swing.JComboBox guildComboBox;
    private javax.swing.JPanel guildPanel;
    private javax.swing.JToggleButton healToggleButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton mageToggleButton;
    private javax.swing.ButtonGroup mainSpecButtonGroup;
    private javax.swing.JComboBox nameComboBox;
    private javax.swing.JPanel namePanel;
    private javax.swing.JToggleButton rogueToggleButton;
    private javax.swing.ButtonGroup roleButtonGroup;
    private javax.swing.JPanel rolePanel;
    private javax.swing.JToggleButton secDDToggleButton;
    private javax.swing.JToggleButton secHealToggleButton;
    private javax.swing.JToggleButton secSupportToggleButton;
    private javax.swing.JToggleButton secTankToggleButton;
    private javax.swing.JCheckBox sksCheckBox;
    private javax.swing.JPanel specPanel;
    private javax.swing.JToggleButton supportToggleButton;
    private javax.swing.JToggleButton tankToggleButton;
    private javax.swing.JToggleButton warriorToggleButton;
    // End of variables declaration//GEN-END:variables
    public void addAcceptListener(ActionListener l){
        acceptButton.addActionListener(l);
        }
    
    public void addCloseButtonListener(ActionListener l){
        closeButton.addActionListener(l);
        }

    public void addGuildListener(ActionListener l){
        guildComboBox.addActionListener(l);
    }
    
    public void addMainSpecListener(ActionListener l){
        tankToggleButton.addActionListener(l);
        ddToggleButton.addActionListener(l);
        healToggleButton.addActionListener(l);
        supportToggleButton.addActionListener(l);
    }
    
    public void addNameListener(ActionListener l){
        nameComboBox.addActionListener(l);
    }
    
    public void addRoleListener(ActionListener l){
        warriorToggleButton.addActionListener(l);
        rogueToggleButton.addActionListener(l);
        clericToggleButton.addActionListener(l);
        mageToggleButton.addActionListener(l);
    }
    
    public void addSecSpecListener(ActionListener l){
        secTankToggleButton.addActionListener(l);
        secDDToggleButton.addActionListener(l);
        secHealToggleButton.addActionListener(l);
        secSupportToggleButton.addActionListener(l);
    }
    public void setGuildList(String[] guildNames){
        guildComboBox.removeAllItems();
        for(String name : guildNames){
            guildComboBox.addItem(name);
        }
    }

    public void setMemberList(LinkedList<String> oldNames) {
        nameComboBox.removeAllItems();
        for(String name : oldNames){
            nameComboBox.addItem(name);
        }
        if(nameComboBox.getItemCount()>0){
            nameComboBox.setSelectedIndex(0);
        }
    }

    public void setContent(String guildName, Calling role, Spec mainSpec, LinkedList<Spec> secondSpecs) {
        guildComboBox.setSelectedItem(guildName);
        switch(role){
            case WARRIOR:
                warriorToggleButton.setSelected(true);
                break;
            case ROGUE:
                rogueToggleButton.setSelected(true);
                break;
            case MAGE:
                mageToggleButton.setSelected(true);
                break;
            default:
                clericToggleButton.setSelected(true);
                break;
        }
        switch(mainSpec){
            case HEAL:
                healToggleButton.setSelected(true);
                break;
            case TANK:
                tankToggleButton.setSelected(true);
                break;
            case SUPPORT:
                supportToggleButton.setSelected(true);
                break;
            default:
                ddToggleButton.setSelected(true);
                break;
        }
        
        secTankToggleButton.setSelected(false);
        secDDToggleButton.setSelected(false);
        secHealToggleButton.setSelected(false);
        secSupportToggleButton.setSelected(false);
        
        if(secondSpecs.contains(Spec.DD)){
                secDDToggleButton.setSelected(true);
        }
        if(secondSpecs.contains(Spec.HEAL)){
                secHealToggleButton.setSelected(true);
        }
        if(secondSpecs.contains(Spec.TANK)){
                secTankToggleButton.setSelected(true);
        }
        if(secondSpecs.contains(Spec.SUPPORT)){
                secSupportToggleButton.setSelected(true);
        }
    }
    
    public String getSelectedMemberName(){
        return (String) nameComboBox.getSelectedItem();
    }
    
    public String getSelectedGuildName(){
        return (String) guildComboBox.getSelectedItem();
    }
    
    public Calling getSelectedRole(){
        if(warriorToggleButton.isSelected()){
            return Calling.WARRIOR;
        }else if(rogueToggleButton.isSelected()){
            return Calling.ROGUE;
        }else if(mageToggleButton.isSelected()){
            return Calling.MAGE;
        }else{
            return Calling.CLERIC;
        }
    }
    
    public Spec getSelectedMainSpec(){
        if(healToggleButton.isSelected()){
            return Spec.HEAL;
        }else if(tankToggleButton.isSelected()){
            return Spec.TANK;
        }else if(supportToggleButton.isSelected()){
            return Spec.SUPPORT;
        }else{
            return Spec.DD;
        }
    }
    
    public LinkedList<Spec> getSelectedSecSpecs(){
        LinkedList<Spec> specs = new LinkedList<>();
        if(secDDToggleButton.isSelected()){
            specs.add(Spec.DD);
        }
        if(secHealToggleButton.isSelected()){
            specs.add(Spec.HEAL);
        }
        if(secTankToggleButton.isSelected()){
            specs.add(Spec.TANK);
        }
        if(secSupportToggleButton.isSelected()){
            specs.add(Spec.SUPPORT);
        }
        return specs;
    }

    public boolean getResMode() {
       return sksCheckBox.isSelected();
    }

    public void setNameColor(Color color) {
        nameComboBox.setBackground(color);
    }

}
