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
        Globals.getInstance().refreshData();
    }

    public void refreshData(){
        if(startPanel != null)
        startPanel.refresh();
    }

    /**
     * Metoda zobrazí dialog s dotazem. Zprávu, na kterou se bude dialog dotazovat,
     * získává metoda pomicí parametru. Dialog bude obsahovat základní tlačítka
     * "Ano" a "Ne" a bude mít nadpis "Pozor!". Pokud chcete změnit tlačítka nebo
     * nadpis volejte metody {@link MainPanel#showOptionDialog(java.lang.String, java.lang.String[])},
     * popřípadně {@link MainPanel#showOptionDialog(java.lang.String, java.lang.String[], java.lang.String)}.
     * @param message Dialogem dotazovaná zpráva.
     * @return Vrací hodnotu {@link JOptionPane#YES_OPTION} pokud uživatel stiskne
     * tlačítka "Ano".
     */
    public static int showOptionDialog(String message){
        return showOptionDialog(message, null, null);
    }

    /**
     * Metoda zobrazí dialog s dotazem. Zprávu, na kterou se bude dialog dotazovat,
     * získává metoda pomicí parametru {@code String message}. Druhý parametr
     * {@code String options} metodě předává popisky tlačítek. Talčítko na pozici
     * {@code options[0]} je bráno jako potvrzovací.
     * @param message Dialogem dotazovaná zpráva.
     * @param options talčítka.
     * @return Vrací hodnotu {@link JOptionPane#YES_OPTION} pokud uživatel stiskne
     * tlačítka {@code options[0]}.
     */
    public static int showOptionDialog(String message, String[] options){
        return showOptionDialog(message, options, null);
    }

    /**
     * Metoda zobrazí dialog s dotazem. Zprávu, na kterou se bude dialog dotazovat,
     * získává metoda pomicí parametru {@code String message}. Druhý parametr
     * {@code String options} metodě předává popisky tlačítek. Talčítko na pozici
     * {@code options[0]} je bráno jako potvrzovací. Třetí parametr nastavuje nadpis
     * dialogu.
     * @param message Dialogem dotazovaná zpráva.
     * @param options talčítka.
     * @param tittle nadpis dialogu.
     * @return Vrací hodnotu {@link JOptionPane#YES_OPTION} pokud uživatel stiskne
     * tlačítka {@code options[0]}.
     */
    public static int showOptionDialog(String message, String[] options, String tittle){
        String messageTittle;
        if(tittle != null){
            messageTittle = tittle;
        } else {
             messageTittle  = "Pozor";
        }
        Object[] obj;
        if(options == null){
            obj = new Object[2];
            obj[0] = (Object)"Ano";
            obj[1] = (Object)"Ne";
        } else {
            obj = options;
        }
        return JOptionPane.showOptionDialog(MainFrame.getMainFrame(),
                message,
                messageTittle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, obj, obj[0]);
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
                super.setJMenuBar(MainFrameMenuBar.getSuperAdminMenu());
                startPanel = new AdminPanel();
                break;
            case ADMIN:
                super.setJMenuBar(MainFrameMenuBar.getAdminMenu());
                startPanel = new AdminPanel();
                break;
            case REGISTRATOR:
                super.setJMenuBar(MainFrameMenuBar.getRegistratorMenu());
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
        this.role = role;
        remove(startPanel);
        remove(getMenuBar());
        /*switch (role) {
            case SUPER_ADMIN:
                super.setJMenuBar(MainFrameMenuBar.getSuperAdminMenu());
                startPanel = new AdminPanel();
                break;
            case ADMIN:
                super.setJMenuBar(MainFrameMenuBar.getAdminMenu());
                add((startPanel = new AdminPanel()), BorderLayout.CENTER);
                break;
            case REGISTRATOR:
                super.setJMenuBar(MainFrameMenuBar.getRegistratorMenu());
                add((startPanel = new RegPanel()), BorderLayout.CENTER);
                break;
        }*/
        setView();
        add(startPanel, BorderLayout.CENTER);
        this.revalidate();
        startPanel.setOwner(this);
    }
    
    @Override
    public void dispose(){
        instance = null;
        super.dispose();
    }
    
    public static void changeMenu(){
        MainFrameMenuBar.changeAdminView();
    }
    
    public static void changeAdminView(){
        instance.startPanel.changePanel();
    }
}
