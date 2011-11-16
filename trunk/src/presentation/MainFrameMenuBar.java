/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import presentation.admin.CreateParDialog;
import presentation.controler.actions.ChangeRoleAction;
import presentation.controler.actions.CompleteRegAction;
import presentation.controler.actions.EditSelfAction;
import presentation.controler.actions.EndAction;
import presentation.controler.actions.LogoutAction;
import presentation.controler.actions.PresenceEvidenceAction;

/**
 *
 * @author Lahvi
 */
public class MainFrameMenuBar extends JMenuBar{
    private JMenu file;
    private  JMenu edit;
    private  JMenu view;
    private JMenu newMenu;
    private MainFrame owner;
    private static MainFrameMenuBar instance;

    public static MainFrameMenuBar getRegistratorMenu(MainFrame owner) {
        instance = new MainFrameMenuBar(owner);
        return instance;
    }
    
    public static MainFrameMenuBar getAdminMenu(MainFrame owner){
        getRegistratorMenu(owner);
        instance.addAdminMenus();
        return instance;
    }
    
    public static MainFrameMenuBar getSuperAdminMenu(MainFrame owner){
        getRegistratorMenu(owner);
        instance.addAdminMenus();
        instance.addSuperAdminMenus();
        return instance;
    }
    
   
    private MainFrameMenuBar(MainFrame owner){
        this.owner = owner;
        file = new JMenu("Soubor");
        edit = new JMenu("Editace");
        view = new JMenu("Zobrazit");
        
        file.add(new ChangeRoleAction(owner));
        file.add(new LogoutAction());
        file.add(new JSeparator());
        file.add(new EndAction());
        
        edit.add(new EditSelfAction());
        edit.add(new JSeparator());
        edit.add(new CompleteRegAction());
        edit.add(new PresenceEvidenceAction());
        
        add(file);
        add(edit);
    }
    
    private void addAdminMenus(){
        newMenu = new JMenu("Nový");
        newMenu.add(new AbstractAction("Nový účastník") {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateParDialog(owner).setVisible(true);
            }
        });
        newMenu.add("Nová akce");
        newMenu.add("Nový registrátor");
        file.add(new JSeparator());
        file.add(newMenu);
        file.add("Import");
        edit.add(new JSeparator());
        edit.add("Upravit registrátora");
        edit.add("Upravit akci");
        view.add("Zobrazit účastníky");
        view.add("Zobrazit akce");
        add(view);
    }
    
    private void addSuperAdminMenus(){
        newMenu.add(new JSeparator());
        newMenu.add("Nový registrátor");
        edit.add(new JSeparator());
        edit.add("Upravit admina");
    }
}
