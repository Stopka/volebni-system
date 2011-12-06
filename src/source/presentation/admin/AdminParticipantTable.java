/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import presentation.Globals;
import presentation.dialogs.ParticipantDialog;
import presentation.ParticipantTablePanel;
import presentation.controler.actions.CompleteRegAction;
import presentation.controler.actions.DeleteAction;
import presentation.controler.actions.EditParticipantAction;
import presentation.controler.actions.EndAction;
import presentation.controler.actions.PresenceAction;

/**
 *
 * @author Lahvi
 */
public class AdminParticipantTable extends ParticipantTablePanel {

    public AdminParticipantTable(){
        super();
        parTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                int r = parTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < parTable.getRowCount()) {
                    parTable.setRowSelectionInterval(r, r);
                } else {
                    parTable.clearSelection();
                }

                int rowindex = parTable.getSelectedRow();
                if (rowindex < 0 ) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createPopupMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
    @Override
    protected void doubleClickAction() {
        int tabRow = parTable.getSelectedRow();
        int r = parTable.convertRowIndexToModel(tabRow);
        if (r > -1) {
            try {
                Participant p = model.getParticipant(r);
                new ParticipantDialog(p).setVisible(true);
                model.fireTableDataChanged();
            } catch (SystemRegException ex) {
                Globals.showErr(this, ex);
            }
        }
    }

    private JPopupMenu createPopupMenu() {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem(CompleteRegAction.getCompleteRegAction());
        popup.add(menuItem);
        menuItem = new JMenuItem(PresenceAction.getPresenceAction());
        popup.add(menuItem);
        menuItem = new JMenuItem(DeleteAction.getDeleteAction());
        popup.add(menuItem);
        menuItem = new JMenuItem(EditParticipantAction.getEditParticipant());
        popup.add(menuItem);
        return popup;
    }
}
