/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import presentation.MainFrame;
import presentation.registrator.CompleteRegDialog;

/**
 *
 * @author Lahvi
 */
public class CompleteRegAction extends AbstractAction{

    private MainFrame owner;
    
    public CompleteRegAction(){
        super("Dokončit Registraci");
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        new CompleteRegDialog(MainFrame.getMainFrame()).setVisible(true);
    }
    
}
