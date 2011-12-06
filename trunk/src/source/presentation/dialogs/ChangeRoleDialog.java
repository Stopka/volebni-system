/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import presentation.Globals;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class ChangeRoleDialog extends AbstractDialog {

    private JComboBox<String> roleBox;
    private User user;
    private Role role;
    private JLabel errMessage;
    private JButton actionButton;
    private JButton backButton;
    private MainFrame owner;

    public ChangeRoleDialog() {
        super();
        setTitle("Změna role");
        owner = MainFrame.getMainFrame();
        this.user = Globals.getInstance().getLogedUser();
        this.role = owner.getActRole();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container conPane = getContentPane();
        conPane.setLayout(new BorderLayout());

        roleBox = new JComboBox<String>();
        roleBox.setEnabled(false);

        switch (user.getRole()) {
            case REGISTRATOR:
                break;
            case ADMIN:
                roleBox.addItem("Registrátor");
                roleBox.addItem("Administrátor");
                roleBox.setEnabled(true);
                break;
            case SUPER_ADMIN:
                roleBox.addItem("Registrátor");
                roleBox.addItem("Administrátor");
                roleBox.addItem("Super Administrátor");
                roleBox.setEnabled(true);
                break;
        }
        actionButton = new JButton(new AbstractAction("Přepnout") {

            @Override
            public void actionPerformed(ActionEvent e) {
                String chooseRole = (String) roleBox.getSelectedItem();
                Role actRole = null;
                if (chooseRole.equals("Registrátor")) {
                    actRole = Role.REGISTRATOR;
                }
                if (chooseRole.equals("Administrátor")) {
                    actRole = Role.ADMIN;
                }
                if (chooseRole.equals("Super Administrátor")) {
                    actRole = Role.SUPER_ADMIN;
                }
                ChangeRoleDialog.this.dispose();
                ChangeRoleDialog.this.owner.setView(actRole);
                
            }
        });

        backButton = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeRoleDialog.this.dispose();
            }
        });

        JPanel downPanel = new JPanel(new FlowLayout());
        downPanel.add(actionButton);
        downPanel.add(backButton);
        this.add(downPanel, BorderLayout.PAGE_END);
        this.add(roleBox, BorderLayout.CENTER);
        pack();
        setCenterPos();
    }

    @Override
    protected void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
