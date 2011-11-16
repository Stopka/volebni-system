/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.LoginScreen;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class LogoutAction extends AbstractAction{

    private MainFrame owner;
    public LogoutAction(){
        super("Odhlásit se");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getMainFrame().dispose();
        new LoginScreen().setVisible(true);
    }
    
}
