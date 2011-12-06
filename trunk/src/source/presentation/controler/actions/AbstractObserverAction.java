/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public abstract class AbstractObserverAction extends AbstractAction implements ChangeListener{
    
    protected AbstractObserverAction(String title){
        super(title);
        Globals.getInstance().addObserverAction(this);
    }
    
    protected AbstractObserverAction(String title, Icon icon){
        super(title, icon);
        Globals.getInstance().addObserverAction(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setEnabled(isEnabledInState());
    }
    
    public abstract boolean isEnabledInState();
    
}
