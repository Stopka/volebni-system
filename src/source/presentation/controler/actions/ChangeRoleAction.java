/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.dialogs.ChangeRoleDialog;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class ChangeRoleAction extends AbstractAction{

    private MainFrame owner;
    public ChangeRoleAction(){
        super("Přepnout Roli");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        owner = MainFrame.getMainFrame();
        new ChangeRoleDialog().setVisible(true);
    }
    
}
