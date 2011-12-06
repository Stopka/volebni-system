/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businesstier.*;
import core.SystemRegException;
import core.data_tier.entities.Action;
import core.data_tier.entities.Participant;
import core.data_tier.entities.User;
import java.awt.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Lahvi
 */
public class Globals {

    private static Globals globals;
    private ActionFacade actionOps;
    private ParticipantFacade participantOps;
    private UserFacade userOps;
    private Participant selectedParticipant; //účastní vybraný v tabulce účastníků
    private Action selectedAction; //akce vybraná při editování v tabulce akcí
    private User selectedUser; //uživatel vybraný v listu uživatelů
    private User logedUser; //přihlášený uživatel
    private long selectedActionID;  //akce vybraná listu akcí v tabulce účastníků
    private Set<ChangeListener> registeredActions;

    private Globals() {
        actionOps = new ActionFacade();
        participantOps = new ParticipantFacade();
        userOps = new UserFacade();
        registeredActions = new HashSet<ChangeListener>();
        
    }
    /**
     * Registruje novou funkci
     * @param action g
     */
    public void addObserverAction(ChangeListener action){
        registeredActions.add(action);
    }
    /**
     * Aktualizuje view
     */
    public void refreshData(){
        if(MainFrame.getMainFrame() != null){
            MainFrame.getMainFrame().refreshData();
        }
        fireStateChange();
    }
    /**
     * Zavolá metodu stateChange zaregistrovaných akcí
     */
    public void fireStateChange(){
        for (ChangeListener listener : registeredActions) {
            listener.stateChanged(null);
        }
    }
    /**
     * Vrací účastníka vybraného v tabulce účastníků.
     * @return 
     */
    public Participant getSelectedParticipant(){
        return this.selectedParticipant;
        
    }
    /**
     * Vrací akci vybranou v tabulce akcí.
     * @return 
     */
    public Action getSelectedAction(){
        return this.selectedAction;
    }
    /**
     * Vrací vybraného uživatele v listu akcí. 
     * @return 
     */
    public User getSelectedUser(){
        return this.selectedUser;
    }
    /**
     * Nastuvje vybranou akci.
     * @param selectedAction 
     */
    public void setSelectedAction(Action selectedAction) {
        this.selectedAction = selectedAction;
    }
    /**
     * Naství vybraného účastníka v tabulce účastníků. Volá se při každé změně
     * vybraného indexu.
     * @param selectedParticipant 
     */
    public void setSelectedParticipant(Participant selectedParticipant) {
        this.selectedParticipant = selectedParticipant;
        fireStateChange();
    }
    /**
     * Nastvuje vybraného uživatele v listu uživatelů.
     * @param selectedUser 
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    /**
     * Nastavuje přihlášeného uživatele.
     * @param logedUser 
     */
    public void setLogedUser(User logedUser){
        this.logedUser = logedUser;
    }
    /**
     * Vrací přihlášeného uživatele.
     * @return 
     */
    public User getLogedUser(){
        return logedUser;
    }
    /**
     * Vrací aktuálně vybrané ID akce, pro které se budou zobrazovat účastníci.
     * @return 
     */
    public long getSelectedActionID() {
        return selectedActionID;
    }
    
    /**
     * Nastuvuje ID aktuálně vybrané akce, pro ktrou se budou zobrazovat účastníci
     * v tabulce účastníků.
     * @param selectedActionID 
     */
    public void setSelectedActionID(long selectedActionID) {
        this.selectedActionID = selectedActionID;
        Globals.getInstance().refreshData();
    }
    
    public static Globals getInstance() {
        if (globals == null) {
            globals = new Globals();
        }
        return globals;
    }

    public ActionFacade getActionOps() {
        return actionOps;
    }

    public ParticipantFacade getParticipantOps() {
        return participantOps;
    }

    public UserFacade getUserOps() {
        return userOps;
    }
    
    

    public static void showErr(Component owner, Exception ex) {
        JOptionPane.showMessageDialog(owner, ex.getMessage(), "Nastala chyba!", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) throws SystemRegException, UnsupportedEncodingException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        Globals gl = Globals.getInstance();
        LoginScreen s = new LoginScreen();
    }
}
