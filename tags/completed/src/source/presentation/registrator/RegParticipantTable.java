/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.registrator;

import presentation.dialogs.ParticipantEvidenceDialog;
import core.data_tier.entities.Participant;
import presentation.Globals;
import presentation.ParticipantTablePanel;

/**
 *
 * @author Lahvi
 */
public class RegParticipantTable extends ParticipantTablePanel {

    @Override
    protected void doubleClickAction() {
        int tabRow = parTable.getSelectedRow();
        int r = parTable.convertRowIndexToModel(tabRow);
        if (r > -1 && r < model.getRowCount()) {
            Participant p = model.getParticipant(r);
            new ParticipantEvidenceDialog(p, Globals.getInstance().getSelectedActionID()).setVisible(true);
            Globals.getInstance().refreshData();
        }
    }
}
