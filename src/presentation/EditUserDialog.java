/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Lahvi
 */
public class EditUserDialog extends JDialog{
    private JTextField login;
    private JTextField pass;
    private JButton okButton, backButton;
    private MainFrame owner;
    
    public EditUserDialog(MainFrame owner){
        super(owner, "Editace vlastních údajů", true);
        this.owner = owner;
        User u = owner.getUser();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container conPane = getContentPane();
        conPane.setLayout(new BorderLayout());
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
                    //showConfirmPasswordDialog(owner, User);
                    if(heslo.equals(EditUserDialog.this.owner.getUser().getPassword())){
                        heslo = null;
                    }
                    if(log.equals(EditUserDialog.this.owner.getUser().getPassword())){
                        log = null;
                    }
                    if(log != null && heslo != null)
                        Globals.getInstance().getUserOps().editLogin(log, heslo);
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
        main.add(okButton); main.add(backButton);
        this.add(main, BorderLayout.CENTER);
        this.add(desc, BorderLayout.PAGE_START);
        setPreferredSize(new Dimension(410, 140));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        // Move the window
        setLocation(x, y);
        pack();
    }
}
