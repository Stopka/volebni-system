/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.Globals;
import presentation.dialogs.CreateActionDialog;

/**
 *
 * @author Lahvi
 */
public class CreateActionAction extends AbstractAction{

    private static CreateActionAction instance;
    
    public static CreateActionAction getInstance(){
        if(instance == null) instance = new CreateActionAction();
        return instance;
    }
    
    private CreateActionAction(){
        super("Nová akce");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new CreateActionDialog().setVisible(true);
        Globals.getInstance().refreshData();
    }
    
}
