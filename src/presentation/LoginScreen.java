/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import core.SystemRegException;
import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
/**
 *
 * @author Lahvi
 */
public class LoginScreen extends JFrame {

    private JLabel _login, _password;
    private JTextField login, password;
    private JPanel top;
    private JButton okButton;

    public LoginScreen() {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e){
            System.exit(1);
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setTitle("Vítejte v registračním systému");
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout());
        top = new JPanel(new GridLayout(2, 2, 0, 0));
        _login = new JLabel("Login: ");
        _password = new JLabel("Heslo: ");
        login = new JTextField();
        password = new JPasswordField();

        top.add(_login);
        top.add(login);
        top.add(_password);
        top.add(password);

        okButton = new JButton(new AbstractAction("Přihlásit se") {

            @Override
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
        okButton.setPreferredSize(new Dimension(350, 50));
        getRootPane().setDefaultButton(okButton);

        con.add(top, BorderLayout.CENTER);
        con.add(okButton, BorderLayout.PAGE_END);
        this.pack();
        setSize(350, 130);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();      int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        setLocation(x, y);
        setResizable(false);
        setVisible(true);
    }

    public void loginAction() {
        try {
            String log = login.getText();
            String pass = password.getText();
            
            //User a = Globals.getInstance().getUserOps().getUser(log);
            User a = new User(Role.ADMIN, log, pass);
            
            if(a.getPassword().equals("900511") && a.getLogin().equals("Lahvi")){
                this.dispose();
                MainFrame.getMainFrame(a);
            } else {
                throw new SystemRegException("Spatne heslo!");
            }
        } catch (SystemRegException ex) {
            /*JOptionPane.showMessageDialog(this, "Zkuste zadat heslo a login znovu!", 
                    "Přihlášení se nezdařilo", JOptionPane.ERROR_MESSAGE);*/
            Globals.showErr(this, new SystemRegException("Chybné heslo nebo login. Zkuste ho zadat znovu."));
            login.setText("");
            password.setText("");
        }       
    }
}
