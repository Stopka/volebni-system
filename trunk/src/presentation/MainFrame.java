/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.awt.*;
import javax.swing.*;
import presentation.admin.AdminPanel;
import presentation.registrator.RegPanel;

/**
 *
 * @author Lahvi
 */
public class MainFrame extends JFrame {

    private AbstractMainFramePanel startPanel;
    private User user;
    private Role role;
    
    private static MainFrame instance;
    
    public static MainFrame getMainFrame(User user){
        if(instance == null){
            instance = new MainFrame(user);
        }
        return instance;
    }
    
    public static MainFrame getMainFrame(){
        return instance;
    }

    private MainFrame(User user) {
        this.user = user;
        role = user.getRole();
        init();
    }

    

    private void init() {
        Container conPane = getContentPane();
        conPane.setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setView();
        String title = "Registrační systém: Přihlášen uživatel: " + user.getLogin()
                + " |  Role: " + startPanel.getRole();
        conPane.add(startPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(900, 700));
        pack();

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        setLocation(x, y);
        //setResizable(false);
        setTitle(title);
        setVisible(true);
        startPanel.setOwner(this);
    }

    private void setView() {
        switch (role) {
            case SUPER_ADMIN:
                //super.setJMenuBar(new SuperAdminMenu());
                super.setJMenuBar(MainFrameMenuBar.getRegistratorMenu(this));
                startPanel = new RegPanel();
                break;
            case ADMIN:
                super.setJMenuBar(MainFrameMenuBar.getAdminMenu(this));
                startPanel = new AdminPanel();
                break;
            case REGISTRATOR:
                super.setJMenuBar(MainFrameMenuBar.getRegistratorMenu(this));
                startPanel = new RegPanel();
                break;
        }
    }

    public Role getActRole() {
        return role;
    }

    public User getUser() {
        return user;
    }

    public void setView(Role role) {
        remove(startPanel);
        remove(getMenuBar());
        switch (role) {
            case SUPER_ADMIN:
                //super.setJMenuBar(new SuperAdminMenu());
                super.setJMenuBar(MainFrameMenuBar.getRegistratorMenu(this));
                startPanel = new RegPanel();
                break;
            case ADMIN:
                super.setJMenuBar(MainFrameMenuBar.getAdminMenu(this));
                add((startPanel = new AdminPanel()), BorderLayout.CENTER);
                break;
            case REGISTRATOR:

                super.setJMenuBar(MainFrameMenuBar.getRegistratorMenu(this));
                add((startPanel = new RegPanel()), BorderLayout.CENTER);
                break;
        }
        setVisible(false);
        setVisible(true);
        this.repaint();
        startPanel.setOwner(this);
    }
    
    @Override
    public void dispose(){
        instance = null;
        super.dispose();
    }
    
    
}
