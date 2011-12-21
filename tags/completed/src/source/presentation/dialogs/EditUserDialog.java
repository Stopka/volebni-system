/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import core.SystemRegException;
import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import presentation.Globals;
import presentation.MainFrame;

/**
 * Dialog, který bude umožňovat uživateli editovat vlastní údaje. Mohl by být 
 * rozšířen pro editaci libovolného uživatele.
 * @author Lahvi
 */
public class EditUserDialog extends AbstractDialog{

    private JTextField login;
    private JTextField pass;
    private JButton okButton, backButton;
    private User u;

    public EditUserDialog() {
        super();
        setTitle("Upravit vlastní účet");
        u = Globals.getInstance().getLogedUser();
        init();
    }


    @Override
    protected void init() {
        setLayout(new BorderLayout());
        JPanel main = new JPanel(new GridLayout(3, 2, 4, 5));
        login = new JTextField(u.getLogin());
        pass = new JPasswordField(u.getPassword());
        JLabel _login = new JLabel("Nový login:");
        JLabel _pass = new JLabel("Nové heslo:");
        JLabel desc = new JLabel(owner.getUser().toString());
        okButton = new JButton(new AbstractAction("Potvrdit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String heslo = pass.getText();
                    String log = login.getText();
                    int option = MainFrame.showOptionDialog("Opravdu chcete uložit změny?");
                    if (option == JOptionPane.YES_OPTION) {
                        if (heslo.equals(EditUserDialog.this.owner.getUser().getPassword())) {
                            heslo = null;
                        }
                        if (log.equals(EditUserDialog.this.owner.getUser().getPassword())) {
                            log = null;
                        }
                        if (log != null && heslo != null) {
                            Globals.getInstance().getUserOps().editLogin(u.getLogin(), log, heslo);
                        }
                    }
                } catch (SystemRegException ex) {
                    Globals.showErr(EditUserDialog.this, ex);
                }
            }
        });

        backButton = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                EditUserDialog.this.dispose();
            }
        });

        main.add(_login);
        main.add(login);
        main.add(_pass);
        main.add(pass);
        main.add(okButton);
        main.add(backButton);
        add(main, BorderLayout.CENTER);
        add(desc, BorderLayout.PAGE_START);
        setSize(new Dimension(410, 140));
        setCenterPos();
    }
}
