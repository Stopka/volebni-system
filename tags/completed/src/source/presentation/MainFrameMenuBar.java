/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import presentation.controler.actions.ChangeRoleAction;
import presentation.controler.actions.ChangeViewAction;
import presentation.controler.actions.CompleteRegAction;
import presentation.controler.actions.CreateActionAction;
import presentation.controler.actions.CreateUserAction;
import presentation.controler.actions.DeleteAction;
import presentation.controler.actions.DeleteActionAction;
import presentation.controler.actions.EditActionAction;
import presentation.controler.actions.EditParticipantAction;
import presentation.controler.actions.EditSelfAction;
import presentation.controler.actions.EditUserAction;
import presentation.controler.actions.EndAction;
import presentation.controler.actions.LogoutAction;
import presentation.controler.actions.PresenceAction;
import presentation.dialogs.UserDialog;

/**
 *
 * @author Lahvi
 */
public class MainFrameMenuBar extends JMenuBar {
    private JMenuItem[] adminAction;
    private JMenuItem[] adminParticipant;
    private JMenuItem viewItem;
    private JMenu file;
    private JMenu edit;
    private JMenu view;
    private JMenu newMenu;
    private boolean participantView;
    private static MainFrameMenuBar instance;

    public static MainFrameMenuBar getRegistratorMenu() {
        instance = new MainFrameMenuBar();
        return instance;
    }

    public static MainFrameMenuBar getAdminMenu() {
        getRegistratorMenu();
        instance.addAdminMenus();
        return instance;
    }

    public static MainFrameMenuBar getSuperAdminMenu() {
        getRegistratorMenu();
        instance.addAdminMenus();
        instance.addSuperAdminMenus();
        return instance;
    }

    private MainFrameMenuBar() {
        
        file = new JMenu("Soubor");
        edit = new JMenu("Editace");
        view = new JMenu("Zobrazit");

        file.add(new ChangeRoleAction());
        file.add(new LogoutAction());
        file.add(new JSeparator());
        file.add(new EndAction());

        edit.add(new EditSelfAction());
        edit.add(new JSeparator());
        edit.add(CompleteRegAction.getCompleteRegAction());
        edit.add(PresenceAction.getPresenceAction());

        add(file);
        add(edit);
    }

    private void addAdminMenus() {
        
        newMenu = new JMenu("Nový");
        newMenu.add("Nový účastník");
        newMenu.add(CreateActionAction.getInstance());
        newMenu.add("Nový registrátor");

        file.add(new JSeparator());
        file.add(newMenu);
        file.add("Import");

        edit.add(new JSeparator());
        edit.add("Upravit registrátora");
        edit.add("Odstranit registrátora");
        edit.add(new JSeparator());
        viewItem = new JMenuItem(new ChangeViewAction("Zobrazit akce"));
        view.add(viewItem);
        createParticipantActions();
        //view.add(new ChangeViewAction("Zobrazit účastníky"));
        
        add(view);
    }

    private void addSuperAdminMenus() {
        newMenu.add(new JSeparator());
        newMenu.add(CreateUserAction.getInstance());
        edit.add(new JSeparator());
        edit.add(EditUserAction.getInstance());
    }

    public static void changeAdminView() {
        if(instance.participantView){
            instance.createAdminActions();
        } else {
            instance.createParticipantActions();
        }
    }
    
    private void createAdminActions(){
        JMenuItem editA = new JMenuItem(EditActionAction.getInstance());
        JMenuItem deleteA = new JMenuItem(DeleteActionAction.getInstance());
        
        adminAction = new JMenuItem[2];
        adminAction[0] = editA; adminAction[1] = deleteA;
        
        if(adminParticipant != null){
            for (int i = 0; i < adminAction.length; i++) {
                edit.remove(adminParticipant[i]);
                edit.add(adminAction[i]);
            }
        } else {
            edit.add(adminAction[0]);
            edit.add(adminAction[1]);
        }
        viewItem.setText("Zobrazit účastníky");
        participantView = false;
    }
    
    private void createParticipantActions(){
        JMenuItem editP = new JMenuItem(EditParticipantAction.getEditParticipant());
        JMenuItem deleteP = new JMenuItem(DeleteAction.getDeleteAction());
        
        adminParticipant = new JMenuItem[2];
        adminParticipant[0] = editP; adminParticipant[1] = deleteP;
        
        if(adminAction != null){
            for (int i = 0; i < adminParticipant.length; i++) {
                edit.remove(adminAction[i]);
                edit.add(adminParticipant[i]);
            }
        }else {
            edit.add(adminParticipant[0]);
            edit.add(adminParticipant[1]);
        }
        viewItem.setText("Zobrazit akce");
        participantView = true;
    }
}
