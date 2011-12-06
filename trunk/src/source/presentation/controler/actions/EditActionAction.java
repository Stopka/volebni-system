/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.data_tier.entities.Action;
import java.awt.event.ActionEvent;
import presentation.Globals;
import presentation.dialogs.CreateActionDialog;

/**
 *
 * @author Lahvi
 */
public class EditActionAction extends AbstractObserverAction{

    private static EditActionAction instance;
    
    public static EditActionAction getInstance(){
        if(instance == null) instance = new EditActionAction();
        return instance;
    }
    
    private EditActionAction(){
        super("Upravit akci");
    }
    @Override
    public boolean isEnabledInState() {
        return (Globals.getInstance().getSelectedAction() != null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Action selAc = Globals.getInstance().getSelectedAction();
        new CreateActionDialog(selAc).setVisible(true);
        Globals.getInstance().refreshData();
    }
    
}
