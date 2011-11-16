/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package businesstier;

import core.SystemRegException;
import core.data_tier.JDBCManager;
import core.data_tier.UserDAO;
import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.util.*;

/**
 *
 * @author Lahvi
 */
public class UserFacade {
    
    private Map<String, User> users;
    
    public UserFacade(){
        users = new TreeMap<String, User>();
        String[] logins = {"Lahvi", "Admin", "Reg"};
        String[] hesla = {"lahvi", "admin", "debil"};
        createSuperAdmin(logins[0], hesla[0]);
        createAdmin(logins[1], hesla[1]);
        createRegistrator(logins[2], hesla[2]);
    }
    
    public void createSuperAdmin(String login, String password){
        User u = new User(Role.SUPER_ADMIN, login, password);
        users.put(login, u);
    }
    
    public void createAdmin(String login, String password){
        User u = new User(Role.ADMIN, login, password);
        users.put(login, u);
    }
    
    public void createRegistrator(String login, String password){
        User u = new User(Role.REGISTRATOR, login, password);
        users.put(login, u);
    }
    
    public User getUser(String login) throws SystemRegException{
        Collection<User> val = users.values();
        for (User user : val) {
            if(user.getLogin().equals(login)){
                return user;
            }
        }
        throw new SystemRegException("Účastník s loginem: " + login + " neexistuje!");
    }
    
    public void deleteUser(String login){
        
    }
    
    public void editLogin(String login, String oldLogin) throws SystemRegException{
        User u = getUser(oldLogin);
        u.setLogin(login);
        
    }
    
    public void editPassword(String login, String password) throws SystemRegException{
        User u = getUser(login);
        u.setPassword(password);
        
    }
    
    public void changeRole(String login, Role newRole) throws SystemRegException{
        User u = getUser(login);
        u.setRole(newRole);
        
    }
    
    public Collection<User> getUsers(){
        return users.values();
    }
}
