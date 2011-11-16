/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import businesstier.ActionFacade;
import businesstier.ParticipantFacade;
import core.data_tier.entities.Participant;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListDataListener;
import presentation.Globals;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class CreateParDialog extends JDialog {

    private JButton okButton, backButton;
    private JTextField login, passwd, address, email, name, id;
    private JComboBox<core.data_tier.entities.Action> actionsBox;
    private Participant p;
    private ActionFacade aOps;
    private ParticipantFacade pOps;
    private boolean isActions = false;
    private MainFrame owner;

    public CreateParDialog(MainFrame owner) {
        super(owner, "Vytvořit účastníka", true);
        aOps = Globals.getInstance().getActionOps();
        pOps = Globals.getInstance().getParticipantOps();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container conPane = getContentPane();
        conPane.setLayout(new GridLayout(8, 2));
        JLabel _action = new JLabel("Vebrte akci");
        JLabel _login = new JLabel("Login");
        login = new JTextField();
        JLabel _passwd = new JLabel("Heslo");
        passwd = new JPasswordField();
        JLabel _address = new JLabel("Adresa");
        address = new JTextField();
        JLabel _email = new JLabel("E-mail");
        email = new JTextField();
        JLabel _name = new JLabel("Jméno");
        name = new JTextField();
        JLabel _id = new JLabel("Číslo OP");
        id = new JTextField();
        actionsBox = new JComboBox();
        setComboBoxModel();
        if (actionsBox.getItemCount() != 0) {
            isActions = true;
        }

        okButton = new JButton(new AbstractAction("OK") {

            @Override
            public void actionPerformed(ActionEvent e) {
                okAction();
            }
        });

        backButton = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                CreateParDialog.this.dispose();
            }
        });
        passwd.setEnabled(false);
        login.setEnabled(false);
        add(_action);
        add(actionsBox);
        add(_name);
        add(name);
        add(_id);
        add(id);
        add(_login);
        add(login);
        add(_passwd);
        add(passwd);
        add(_email);
        add(email);
        add(_address);
        add(address);
        add(okButton);
        add(backButton);

        setMinimumSize(new Dimension(500, 300));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        // Move the window
        setLocation(x, y);
        if (!isActions) {
            setAllDisabled();
        }
        getRootPane().setDefaultButton(okButton);
    }

    public CreateParDialog(MainFrame owner, Participant p) {
        this(owner);
        setTitle("Editovat účastníka");
        this.p = p;
        this.setTextFields();
        if (p.isCompleteReg()) {
            login.setEnabled(true);
            passwd.setEnabled(true);
        }
        id.setEnabled(false);
        if (!isActions) {
            setAllDisabled();
        }
    }

    private void setTextFields() {
        login.setText(p.getLogin());
        passwd.setText(p.getPassword());
        address.setText(p.getAddress());
        email.setText(p.getEmail());
        name.setText(p.getName());
        id.setText("" + p.getpersonalCardID());

    }

    private void okAction() {
        String pName = name.getText();
        String pEmail = email.getText();
        String pAddress = address.getText();
        int pID = Integer.parseInt(id.getText());
        if (p == null) {
            pOps.createParticipant(pName, pAddress, pEmail, pID);
        } else {
            String pLogin = login.getText();
            String pPswd = passwd.getText();
            if (pLogin.equals(p.getLogin())) {
                pLogin = null;
            }
            if (pPswd.equals(p.getPassword())) {
                pPswd = null;
            }
            if (pLogin == null && pPswd == null) {
                System.out.println("needit logins");
            } else {
                System.out.println("pOps.editLogins(pLogin, pPswd)");
            }
            if (pName.equals(p.getName())) {
                pName = null;
            }
            if (pAddress.equals(p.getAddress())) {
                pAddress = null;
            }
            if (pEmail.equals(p.getEmail())) {
                pEmail = null;
            }
            if (pName == null && pAddress == null && pEmail == null) {
                System.out.println("needit udaje");
            } else {
                System.out.println("pOps.editParametrs(pName, pEmail, pAddress)");
            }
        }
        this.dispose();
    }

    private void setComboBoxModel() {
        Collection<core.data_tier.entities.Action> acts = aOps.getActions();
        for (core.data_tier.entities.Action action : acts) {
            actionsBox.addItem(action);
        }
    }

    private void setAllDisabled() {
        actionsBox.setEnabled(false);
        name.setEnabled(false);
        login.setEnabled(false);
        passwd.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);
        id.setEnabled(false);
        okButton.setEnabled(false);
    }
}
