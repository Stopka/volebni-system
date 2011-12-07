/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Lahvi
 * @version 1.0
 * @created 31-10-2011 0:11:30
 */
public class User {

    private String login;
    private String psswd;
    private Role role;
    private Collection<Long> actions;

    public User(Role role, String login, String password) {
        this.role = role;
        this.login = login;
        this.psswd = password;
        actions = new ArrayList<Long>();
    }
    public void setRole(Role role){
        this.role = role;
    }
    
    public Role getRole(){
        return this.role;
    }
    /**
     * Metoda nastavuje nové přihlašovací jméno uživatele.
     * @param login Nové přihlašovací jméno.
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * Metoda vrací přihlašovací jméno uživatele.
     * @return Přihlašovací jméno uživatele.
     */
    public String getLogin() {
        return this.login;
    }
    /**
     * Metoda nastavuje nové přihlašovací heslo uživatele.
     * @param password Nové heslo.
     */
    public void setPassword(String password) {
        this.psswd = password;
    }
    /**
     * Metoda vrací heslo uživatele.
     * @return Helso uživatele.
     */
    public String getPassword() {
        return this.psswd;
    }
    @Override
    public String toString(){
        return login + " Role: " + role; 
    }
    
    public void addAction(long actionID){
        actions.add(actionID);
    }
    
    public void removeAction(long actionID){
        actions.remove(actionID);
    }
    
    public Collection<Long> getActions(){
        return actions;
    }
    
    public void addAllAction(Collection<Long> actionIDs){
        this.actions.addAll(actionIDs);
    }
}
