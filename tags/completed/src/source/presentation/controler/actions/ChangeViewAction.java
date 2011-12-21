/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class ChangeViewAction extends AbstractAction{
    /*private static ChangeViewAction changeViewAction;
    
    public static ChangeViewAction getChangeViewAction(String title){
        if(changeViewAction == null) changeViewAction = new ChangeViewAction(title);
        return changeViewAction;
    }*/
    
    public ChangeViewAction(String title){
        super(title);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.changeAdminView();
        MainFrame.changeMenu();
    }
    
}
