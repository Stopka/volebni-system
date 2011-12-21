/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.controler.actions;

import core.SystemRegException;
import core.data_tier.entities.Action;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import presentation.Globals;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class DeleteActionAction extends AbstractObserverAction {

    private static DeleteActionAction instance;

    public static DeleteActionAction getInstance() {
        if (instance == null) {
            instance = new DeleteActionAction();
        }
        return instance;
    }

    private DeleteActionAction() {
        super("Odstranit akci");
    }

    @Override
    public boolean isEnabledInState() {
        return !(Globals.getInstance().getSelectedAction() == null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Action selAc = Globals.getInstance().getSelectedAction();
            int res = MainFrame.showOptionDialog("Opravdu chcete danou akci odstranit?");
            if (res == JOptionPane.YES_OPTION) {
                Globals.getInstance().getActionOps().deleteAction(selAc.getID());
                Globals.getInstance().refreshData();
            } 
        } catch (SystemRegException ex) {
            Globals.showErr(null, ex);
        }
    }
}
