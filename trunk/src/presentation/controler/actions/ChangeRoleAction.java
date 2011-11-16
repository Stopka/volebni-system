/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import presentation.ChangeRoleDialog;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class ChangeRoleAction extends AbstractAction{

    private MainFrame owner;
    public ChangeRoleAction(MainFrame owner){
        super("Přepnout Roli");
        if(owner.getUser().getRole() == Role.REGISTRATOR){
            this.setEnabled(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        owner = MainFrame.getMainFrame();
        new ChangeRoleDialog(owner).setVisible(true);
    }
    
}
