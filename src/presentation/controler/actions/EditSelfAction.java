/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.EditUserDialog;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class EditSelfAction extends AbstractAction{

    private MainFrame owner;
    
    public EditSelfAction(){
        super("Upravit vlastní účet");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new EditUserDialog(MainFrame.getMainFrame()).setVisible(true);
    }
    
}
