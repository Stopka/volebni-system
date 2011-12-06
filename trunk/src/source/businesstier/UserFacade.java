/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import core.SystemRegException;
import core.data_tier.JDBCManager;
import core.data_tier.UserDAO;
import core.data_tier.entities.Action;
import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.util.*;

/**
 * Třída poskytující business logiku pro práci s uživateli.
 * @author Lahvi
 */
public class UserFacade {
    
    private Collection<Long> ids;
    private Map<String, User> users;
    
    public UserFacade(){
        users = new TreeMap<String, User>();
        String[] logins = {"Lahvi", "Admin", "Reg", "Admin2", "Reg2"};
        String[] hesla = {"lahvi", "admin", "reg", "admin2", "reg2"};
        ids = new ArrayList<Long>();
        
        ids.add((long)1);
        ids.add((long)2);
        ids.add((long)3);
        ids.add((long)4);
        ids.add((long)5);
        createSuperAdmin(logins[0], hesla[0]);
        createAdmin(logins[1], hesla[1]);
        createRegistrator(logins[2], hesla[2]);
        createRegistrator(logins[4], hesla[4]);
        createAdmin(logins[3], hesla[3]);
    }
    /**
     * Vytváří nového uživatele s rolí SUPERADMIN.
     * @param login
     * @param password 
     */
    public void createSuperAdmin(String login, String password){
        User u = new User(Role.SUPER_ADMIN, login, password);
        u.addAllAction(ids);
        users.put(login, u);
    }
    /**
     * Vytváří nového uživatele s rolí ADMIN.
     * @param login
     * @param password 
     */
    public void createAdmin(String login, String password){
        User u = new User(Role.ADMIN, login, password);
        u.addAllAction(ids);
        users.put(login, u);
    }
    /**
     * Vytváří nového uživatele s rolí REGISTRATOR.
     * @param login
     * @param password 
     */
    public void createRegistrator(String login, String password){
        User u = new User(Role.REGISTRATOR, login, password);
        u.addAllAction(ids);
        users.put(login, u);
    }
    
    public void createUser(String login, String password, Role role, Collection<Long> actionIDs){
        User u  = new User(role, login, password);
        u.addAllAction(actionIDs);
        users.put(login, u);
    }
    /**
     * Vrací uživatle s daným loginem.
     * @param login
     * @return
     * @throws SystemRegException 
     */
    public User getUser(String login) throws SystemRegException{
        Collection<User> val = users.values();
        for (User user : val) {
            if(user.getLogin().equals(login)){
                return user;
            }
        }
        throw new SystemRegException("Účastník s loginem: " + login + " neexistuje!");
    }
    
    /**
     * Odstraňuje uživatele s daným loginem.
     * @param login 
     */
    public void deleteUser(String login){
        
    }
    
    public void addAction(String login, long addActionID) throws SystemRegException{
        User u = getUser(login);
        u.addAction(addActionID);
        
    }
    public void editUserParametrs(String login, String newLogin, String newPassword, Role newRole, Collection<Long> newActionIDs) throws SystemRegException{
        User u = getUser(login);
        u.setLogin(newLogin);
        u.setPassword(newPassword);
        u.setRole(newRole);
        //Zíkám současný list akcí.
        
        Collection<Long> parIDs = new ArrayList<Long>(u.getActions());
        //
        for (Long id : parIDs) {
          if(!newActionIDs.contains(id))  
              u.removeAction(id);
            System.out.println("id:" + id);
        }
        
        for (Long id : newActionIDs) {
            u.addAction(id);
        }
    }
    
    public void editLogin(String login, String newLogin, String newPassword) throws SystemRegException{
        User u = getUser(login);
        u.setLogin(newLogin);
        u.setPassword(newPassword);
    }
    /**
     * Vrací všechny uživatele.
     * @return 
     */
    public Collection<User> getUsers(){
        return users.values();
    }
    /**
     * Vrací kolekci akcí, na které má uživatel práve sahat.
     * @param login
     * @return
     * @throws SystemRegException 
     */
    public Collection<Long> getActions(String login) throws SystemRegException{
        User user = getUser(login);
        return user.getActions();
    }
    /**
     * Přidává úživateli s daným loginem právo na danou akci.
     * @param login
     * @param a
     * @throws SystemRegException 
     */
    public void addActions(String login, Collection<Long> actionIDs) throws SystemRegException{
        User user = getUser(login);
        user.addAllAction(actionIDs);
    }
    
    /**
     * Odstraní uživateli s daným loginem akci danou parametrem a.
     * @param login
     * @param a
     * @throws SystemRegException 
     */
    public void removeAction(String login, long removeID) throws SystemRegException{
        User user = getUser(login);
        user.removeAction(removeID);
    }
    /**
     * Vrátí všechny uživatele, kteří mají roli danou parametrem role.
     * @param role
     * @return 
     */
    public Collection<User> getUsers(Role role){
        Collection<User> res = new ArrayList<User>();
        Collection<Role> roles = new ArrayList<Role>();
        switch(role){
            case REGISTRATOR:
                return null;
            case ADMIN:
                roles.add(Role.REGISTRATOR);
                break;
            case SUPER_ADMIN:
                roles.add(Role.REGISTRATOR);
                roles.add(Role.ADMIN);
                break;
        }
        for (User user : users.values()) {
            if(roles.contains(user.getRole())){
                res.add(user);
            }
        }
        return res;
    }
}
