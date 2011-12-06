/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentation.dialogs.ParticipantDialog;
import java.awt.event.ActionEvent;
import presentation.Globals;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class EditParticipantAction extends AbstractObserverAction{
    private static EditParticipantAction editParticipant;
    public static EditParticipantAction getEditParticipant(){
        if(editParticipant == null) editParticipant = new EditParticipantAction();
        return editParticipant;
    }
    
    private EditParticipantAction(){
        super("Upravit účastníka");
    }
    
    @Override
    public boolean isEnabledInState() {
        Participant p = Globals.getInstance().getSelectedParticipant();
        if(p == null){
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Participant p = Globals.getInstance().getSelectedParticipant();
            new ParticipantDialog(p).setVisible(true);
            Globals.getInstance().refreshData();
        } catch (SystemRegException ex) {
            Globals.showErr(null, ex);
        }
    }
    
}
