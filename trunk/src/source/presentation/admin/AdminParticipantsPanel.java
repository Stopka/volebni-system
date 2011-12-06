/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import presentation.ParticipantTableModel;
import core.data_tier.entities.Participant;
import presentation.dialogs.ParticipantDialog;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.JTableHeader;
import presentation.Globals;
import presentation.MainFrame;
import presentation.RefresablePanel;
import presentation.controler.actions.DeleteAction;
import presentation.controler.actions.EditParticipantAction;

/**
 *
 * @author Lahvi
 */
public class AdminParticipantsPanel extends RefresablePanel {

    private AdminParticipantTable tablePanel;
    private JButton editButton, newButton, deleteButton, importButton;

    public AdminParticipantsPanel() {
        super(new BorderLayout());
        tablePanel = new AdminParticipantTable();
       

        editButton = new JButton(EditParticipantAction.getEditParticipant());

        deleteButton = new JButton(DeleteAction.getDeleteAction());

        newButton = new JButton(new AbstractAction("Nový") {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ParticipantDialog().setVisible(true);
                Globals.getInstance().refreshData();
            }
        });

        importButton = new JButton(new AbstractAction("Importovat") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        

        JPanel rightPanel = new JPanel(new GridLayout(4, 1));

        rightPanel.add(newButton);
        rightPanel.add(importButton);
        rightPanel.add(editButton);
        rightPanel.add(deleteButton);
        add(tablePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.LINE_END);
    }

    @Override
    public void refresh() {
        tablePanel.refresh();
    }

    
}
