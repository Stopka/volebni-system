/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.data_tier.entities.Participant;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import presentation.Globals;
import presentation.MainFrame;
import presentation.dialogs.ParticipantEvidenceDialog;

/**
 *
 * @author Lahvi
 */
public class CompleteRegAction extends AbstractObserverAction{
    private static CompleteRegAction completeRegAction;
    
    private CompleteRegAction(){
        super("Dokončit registraci");
    }
    
    @Override
    public boolean isEnabledInState() {
        Participant selectedP = Globals.getInstance().getSelectedParticipant();
        if(selectedP != null && !selectedP.isCompleteReg()){
            return true;
        }
        return false;
    }
    
    public static CompleteRegAction getCompleteRegAction(){
        if(completeRegAction == null) completeRegAction = new CompleteRegAction();
        return completeRegAction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Participant p = Globals.getInstance().getSelectedParticipant();
        long actionID = Globals.getInstance().getSelectedActionID();
        new ParticipantEvidenceDialog(p, actionID).setVisible(true);
        Globals.getInstance().refreshData();
    }
    
}
