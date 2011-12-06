/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.Globals;
import presentation.dialogs.UserDialog;

/**
 *
 * @author Lahvi
 */
public class CreateUserAction extends AbstractAction{

    private static CreateUserAction instance;
    
    public static CreateUserAction getInstance(){
        if(instance == null) instance = new CreateUserAction();
        return instance;
    }
    
    public CreateUserAction(){
        super("Vytvořit nového uživatele");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new UserDialog().setVisible(true);
        Globals.getInstance().refreshData();
    }
    
}
