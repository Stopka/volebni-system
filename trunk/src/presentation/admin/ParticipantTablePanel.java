/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import core.data_tier.entities.Participant;
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
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class ParticipantTablePanel extends JPanel {

    private JTable parTable;
    private ParTableModel model;
    private MainFrame owner;
    private JButton presentButton, nonPresentButton, allButton;
    private JButton editButton, newButton, deleteButton, importButton;

    public ParticipantTablePanel(MainFrame owner) {
        setLayout(new BorderLayout());
        model = new ParTableModel();
        parTable = new JTable(model);
        add(parTable.getTableHeader(), BorderLayout.PAGE_START);
        add(parTable, BorderLayout.CENTER);
        parTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editAction();
                }
            }
        });
        presentButton = new JButton(new AbstractAction("Zobrazit přítomné") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        nonPresentButton = new JButton(new AbstractAction("Zobrazit nepřítomné") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        allButton = new JButton(new AbstractAction("Zobrazit všechny") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        editButton = new JButton(new AbstractAction("Editovat") {

            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });

        deleteButton = new JButton(new AbstractAction("Odstranit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        newButton = new JButton(new AbstractAction("Nový") {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateParDialog(MainFrame.getMainFrame()).setVisible(true);
            }
        });

        importButton = new JButton(new AbstractAction("Importovat") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        JPanel bottomPanel = new JPanel(new GridLayout());
        bottomPanel.add(allButton);
        bottomPanel.add(presentButton);
        bottomPanel.add(nonPresentButton);
        add(bottomPanel, BorderLayout.PAGE_END);

        JPanel rightPanel = new JPanel(new GridLayout(4, 1));

        rightPanel.add(newButton);
        rightPanel.add(importButton);
        rightPanel.add(editButton);
        rightPanel.add(deleteButton);

        add(rightPanel, BorderLayout.LINE_END);
    }

    private void editAction() {
        int r = parTable.getSelectedRow();
        if (r > -1) {
            Participant p = model.getBook(r);
            new CreateParDialog(MainFrame.getMainFrame(), p).setVisible(true);
        }
    }
}
