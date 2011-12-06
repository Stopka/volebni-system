/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.data_tier.entities.User;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import presentation.Globals;
import presentation.MainFrame;
import sun.awt.GlobalCursorManager;

/**
 *
 * @author Lahvi
 */
public class DeleteUserAction extends AbstractObserverAction{
    private static DeleteUserAction instance;
    
    public static DeleteUserAction getInstance(){
        if(instance == null) instance = new DeleteUserAction();
        return instance;
    }
    
    private DeleteUserAction(){
        super("Odstranit uživatele");
    }
    @Override
    public boolean isEnabledInState() {
        return (Globals.getInstance().getSelectedUser() != null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User selectedUser = Globals.getInstance().getSelectedUser();
        int res = MainFrame.showOptionDialog("Opravdu chcete uživatele odstranit?");
        if(res == JOptionPane.YES_OPTION){
            Globals.getInstance().getUserOps().deleteUser(selectedUser.getLogin());
            Globals.getInstance().refreshData();
        }
            
    }
    
}
