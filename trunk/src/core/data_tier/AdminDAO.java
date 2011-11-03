/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier;

import core.data_tier.entities.Admin;
import core.data_tier.entities.Role;
import java.util.*;

/**
 *
 * @author Lahvi
 */
public class AdminDAO {

    private Map<String, Admin> users;
    private Admin actAdmin;

    public AdminDAO() {
        //V parametru asi bude ziskani pripojeni z nejakyho connection poolu
        users = new HashMap<String, Admin>();
        //ted se do HashMapy nactou tabulky z databaze
    }

    public void createAdmin(String login, String heslo, Role role) {
        actAdmin = new Admin(role, login, heslo);
        users.put(login, actAdmin);
        //zapis do databaze
        //'insert into ADMINS values (login, 1, 1);
    }

    public void deleteAdmin(String login) throws Exception {
        actAdmin = users.remove(login);
        if (actAdmin == null) {
            throw new Exception("Uživatel nejde odstranit, protože login: " + login
                    + "neexistuje!");
        }
        //vymaže uživatele s daným loginem z databáze
    }

    public Admin getAdmin(String login) throws Exception {
        actAdmin = users.get(login);
        if (actAdmin == null) {
            System.out.println("Nenalzen admin s loginem: " + login);
            throw new Exception("Nenalzen admin s loginem: " + login);
        }
        return actAdmin;
    }
    
    public void changeLogin(String oldLogin, String newLogin) throws Exception{
        actAdmin = getAdmin(oldLogin);
        actAdmin.setLogin(newLogin);
        users.remove(oldLogin);
        users.put(newLogin, actAdmin);
    }
    
    public void changePassword(String login, String password) throws Exception{
        actAdmin = getAdmin(login);
        actAdmin.setPassword(password);
        users.remove(login);
        users.put(login, actAdmin);
    }
    
    public void changeRole(String login, Role role) throws Exception{
        actAdmin = getAdmin(login);
        actAdmin.setRole(role);
        users.remove(login);
        users.put(login, actAdmin);
    }
}
