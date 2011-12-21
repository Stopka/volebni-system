/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.data_tier.entities.Participant;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.Globals;
import presentation.MainFrame;
import presentation.dialogs.ParticipantEvidenceDialog;


/**
 *
 * @author Lahvi
 */
public class PresenceAction extends AbstractObserverAction{
    
    private static PresenceAction presenceAction;
    
    public static PresenceAction getPresenceAction(){
        if(presenceAction == null) presenceAction = new PresenceAction();
        return presenceAction;
    }
    private PresenceAction(){
        super("Evidence prezence");
    }
    @Override
    public boolean isEnabledInState() {
        Participant selectedP = Globals.getInstance().getSelectedParticipant();
        if(selectedP != null && selectedP.isCompleteReg()){
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Participant p = Globals.getInstance().getSelectedParticipant();
        long actionID = Globals.getInstance().getSelectedActionID();
        new ParticipantEvidenceDialog(p, actionID).setVisible(true);
        Globals.getInstance().refreshData();
    }
    
}
