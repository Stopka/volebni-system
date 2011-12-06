/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import core.SystemRegException;
import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public class UserDialog extends AbstractDialog {

    private final Role[] items = {Role.REGISTRATOR, Role.ADMIN, Role.SUPER_ADMIN};
    private User u, logedUser;
    private JTextField loginField, passwordField;
    private JButton actionsBtn, backBtn, okBtn;
    private Collection<Long> allActions, selectedActions;
    private JComboBox<Role> roleComboBox;
    private String login, password;
    private Role selectedRole;

    public UserDialog() {
        super();
        setTitle("Nový účastník");
        logedUser = Globals.getInstance().getLogedUser();
        allActions = new ArrayList<Long>(logedUser.getActions());
        selectedActions = new ArrayList<Long>();
        init();
    }

    public UserDialog(User u) {
        this();
        this.u = u;
        setTitle("Upravit účastníka");
        actionsBtn.setText("Upravit práva na akci");
        setEditValues();
    }

    @Override
    protected void init() {
        loginField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<Role>(items);
        roleComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = roleComboBox.getSelectedIndex();
                if (selectedIndex < 0 || selectedIndex > items.length) {
                    selectedRole = null;
                } else {
                    selectedRole = items[selectedIndex];
                }
            }
        });
        roleComboBox.setSelectedIndex(0);
        selectedRole = items[0];
        actionsBtn = new JButton(new AbstractAction("Přidat práva na akci") {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ChooseActionDialog(allActions, selectedActions).setVisible(true);
            }
        });
        okBtn = new JButton(new AbstractAction("Vytvořit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setValues();
                    if(u == null){
                        createAction();
                    } else {
                        editAction();
                    }
                    dispose();
                } catch (SystemRegException ex) {
                    Globals.showErr(UserDialog.this, ex);
                }
            }
        });

        backBtn = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JLabel _login = new JLabel("Login:");
        JLabel _pass = new JLabel("Heslo:");
        JLabel _role = new JLabel("Role:");
        JLabel _akce = new JLabel(); 
        JPanel lblPanel = new JPanel(new GridLayout(4, 1));
        JPanel valuePanel = new JPanel(new GridLayout(4, 1));
        JPanel btnPanel = new JPanel(new GridLayout(1,2));
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        setLayout(new BorderLayout());
        lblPanel.add(_login); lblPanel.add(_pass);
        lblPanel.add(_role); lblPanel.add(_akce);
        valuePanel.add(loginField); valuePanel.add(passwordField);
        valuePanel.add(roleComboBox); valuePanel.add(actionsBtn);
        btnPanel.add(backBtn); btnPanel.add(okBtn);
        topPanel.add(lblPanel, BorderLayout.LINE_START);
        topPanel.add(valuePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
        setSize(400, 200);
        setCenterPos();
    }

    private void setCollections() {
        if (u != null) {
            Collection<Long> editUserActions = u.getActions();
            for (Long id : editUserActions) {
                if (allActions.contains(id)) {
                    allActions.remove(id);
                    selectedActions.add(id);
                }
            }
        }
    }
    
    private void setEditValues(){
        login = u.getLogin(); loginField.setText(login);
        password = u.getPassword(); passwordField.setText(password);
        roleComboBox.setSelectedItem(u.getRole());
        setCollections();
    }
    private void setValues() throws SystemRegException {
        login = loginField.getText();
        password = passwordField.getText();
        if (login.isEmpty() || password.isEmpty() || selectedRole == null) {
            throw new SystemRegException("Musíte vyplnit přihlašovací údaje a "
                    + "vybrat uživatelskou roli!");
        }
        if (selectedActions.isEmpty()) {
            throw new SystemRegException("Užviatel musí mít přístup aslespoň k jedné akci");
        }
    }

    private void createAction() throws SystemRegException {
        Globals.getInstance().getUserOps().createUser(login, password, selectedRole, selectedActions);
    }
    
    private void editAction() throws SystemRegException{
        Globals.getInstance().getUserOps().editUserParametrs(u.getLogin(), login, password, selectedRole, selectedActions);
    }
}
