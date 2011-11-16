/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.MainFrame;
import presentation.registrator.PresenceDialog;

/**
 *
 * @author Lahvi
 */
public class PresenceEvidenceAction extends AbstractAction{
    
    private MainFrame owner;
    
    public PresenceEvidenceAction(){
        super("Evidence presence");
        this.owner = MainFrame.getMainFrame();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        new PresenceDialog(MainFrame.getMainFrame()).setVisible(true);
    }
    
}
