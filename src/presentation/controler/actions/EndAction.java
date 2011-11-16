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
public class EndAction extends AbstractAction{
    public EndAction(){
        super("Zavřít aplikaci");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getMainFrame().dispose();
    }
    
}
