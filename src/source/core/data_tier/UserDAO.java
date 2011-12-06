/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier;

import core.SystemRegException;
import core.data_tier.entities.User;
import core.data_tier.entities.Role;
import java.sql.*;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Lahvi
 */
public class UserDAO {
    
    private User actUser;
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    private static UserDAO instance;

    public static UserDAO getInstance() {
        if(instance == null)
            instance = new UserDAO();
        return instance;
    }

    private UserDAO() {            
    }

    public void createUser(String login, String heslo, Role role) throws SystemRegException{
        try{
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            int i = stmt.executeUpdate("insert into USERS values ('"+login+"', '"+heslo+"', '"+role.toString()+"')");
            if(i == 0){
                JDBCManager.writeLog("Chyba při vkládání nového uživatele do tabulky "
                        + "User. Vložením nebyl ovlivněn žádný rádek!");
                throw new SystemRegException("Chyba při vkládání nového uživatele "
                        + "do tabulky User. Vložením nebyl ovlivněn žádný rádek!");
            }
        } catch (SQLException ex){
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                        + "User. " + ex.getMessage());
        } 
    }

    public void deleteUser(String login) throws SystemRegException {       
    }

    public User getUser(String login) throws SystemRegException {        
        return null;
    }
    
    public void changeLogin(String oldLogin, String newLogin) throws SystemRegException{
        
    }
    
    public void changeRole(String login, Role role) throws SystemRegException{       
    }
}
