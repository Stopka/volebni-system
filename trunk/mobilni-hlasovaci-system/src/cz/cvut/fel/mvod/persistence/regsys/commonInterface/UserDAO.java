/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface;


import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Role;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.User;
import java.sql.*;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author Lahvi
 */
public class UserDAO {

    private User actUser;
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    private ResultSet manactrset;
    private ResultSet actrset;
    private static UserDAO instance;
    private final String TABLE_NAME = "admin";

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private UserDAO() {
    }

    public void createUser(String login, String heslo, Role role) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            System.out.println(role.toString());
            int i = stmt.executeUpdate("insert into " + TABLE_NAME + " values "
                    + "(DEFAULT, '" +  heslo   + "', '" + role.toString().substring(0,4) + "', '" + login + "')");
            if (i == 0) {
                JDBCManager.writeLog("Chyba při vkládání nového uživatele do tabulky "
                        + "User. Vložením nebyl ovlivněn žádný rádek!");
                throw new SystemRegException("Chyba při vkládání nového uživatele "
                        + "do tabulky User. Vložením nebyl ovlivněn žádný rádek!");
            }
        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + "User. " + ex.getMessage());
        } finally {
            close();
        }

    }

    public void deleteUser(String login) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where login='" + login + "'");
            if (rset.next()) {
                String dL;
                if ((dL = rset.getString("login")).equals(login)) {
                    rset.deleteRow();
                    JDBCManager.writeLog("Z tabulky " + TABLE_NAME + " byl odstraněn uživatel s loginem: " + dL);
                    return;
                }
            }
            JDBCManager.writeLog("Chyba při odstraňování uživatele s loginem: "
                    + login + ". Požadovaný uživatel neexistuje!");
            throw new SystemRegException("Chyba při odstraňování uživatele s loginem: "
                    + login + ". Požadovaný uživatel neexistuje!");
        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba v dtb operaci při odstraňování uživatele!" + ex.getMessage());
            throw new SystemRegException("SQL chyba v dtb operaci při odstraňování uživatele" + ex.getMessage());
        } finally {
            close();
        }

    }

    public User getUser(String login) throws SystemRegException {
        actUser = null;
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME );
            while (rset.next()) {
                if (rset.getString("login").equals(login)) {
                    String password = rset.getString(2);
                    String role = rset.getString(3);
                    Role r = Role.ADMIN;
                    if (role.equals("admin")) {
                        r = Role.ADMIN;
                    } else if (role.equals("superadmin")) {
                        r = Role.SUPER_ADMIN;
                    } else if (role.equals("registr")) {
                        r = Role.REGISTRATOR;
                    }
                    String lgn = rset.getString(4);

                    actUser = new User(r, lgn, password);

                }
            }
            return actUser;
        } catch (SQLException ex) {
            return null;
        }
    }

    public void changeLogin(String oldLogin, String newLogin) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                if (rset.getString("login").equals(oldLogin)) {
                    if (oldLogin != null) {
                        rset.updateString(4, newLogin);
                        rset.updateRow();
                        JDBCManager.writeLog("Byl změněn login uživatele "
                                + oldLogin + ", na: " + newLogin);
                    }

                }
            }
        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        } finally {
            close();
        }
    }

    public void changeRole(String login, Role role) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where login=" + login);
            if (rset.next()) {
                if (rset.getString("login").equals(login)) {
                    if (role == Role.ADMIN) {
                        if (rset.getString("role").equals("ADMI")) {
                            JDBCManager.writeLog("Role uživatele "
                                    + login + ", nebyla změněna, cílová role se shoduje s původní ");
                        } else {
                            if (role != null) {
                                rset.updateString(3, "ADMI");
                                JDBCManager.writeLog("Role uživatele "
                                        + login + ", byla změněna na roli:  " + "ADMIN");
                            }
                        }

                    } else if (role == Role.SUPER_ADMIN) {
                        if (rset.getString("role").equals("SUPE")) {
                            JDBCManager.writeLog("Role uživatele "
                                    + login + ", nebyla změněna, cílová role se shoduje s původní ");
                        } else {
                            if (role != null) {
                                rset.updateString(3, "SUPE");
                                JDBCManager.writeLog("Role uživatele "
                                        + login + ", byla změněna na roli:  " + "SUPERADMIN");
                            }
                        }
                    } else if (role == Role.REGISTRATOR) {
                        if (rset.getString("role").equals("REGI")) {
                            JDBCManager.writeLog("Role uživatele "
                                    + login + ", nebyla změněna, cílová role se shoduje s původní ");
                        } else {
                            if (role != null) {
                                rset.updateString(3, "REGI");
                                JDBCManager.writeLog("Role uživatele "
                                        + login + ", byla změněna na roli:  " + "REGISTRATOR");
                            }
                        }
                    }

                }
            }
        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        } finally {
            close();
        }
    }

    public Collection<User> getUsers() throws SystemRegException {
        actUser = null;
        Collection<User> users = new ArrayList<User>();
        
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from admin");
            while (rset.next()) {
                long adminID = rset.getLong(1);
                String password = rset.getString(2);
                String role = rset.getString(3);
                Role r = Role.ADMIN;
                if (role.equals("ADMI")) {
                    r = Role.ADMIN;
                } else if (role.equals("SUPE")) {
                    r = Role.SUPER_ADMIN;
                } else if (role.equals("REGI")) {
                    r = Role.REGISTRATOR;
                }
                String lgn = rset.getString(4);

                actUser = new User(r, lgn, password);
                Statement manastmt = conn.createStatement();
                manactrset = manastmt.executeQuery("select * from admin_manages_action where Admin_id='" + adminID + "'");
                while(manactrset.next()){
                    if(manactrset.getLong("Admin_id")==adminID){
                        long actID = manactrset.getLong("Action_id");
                        Statement actstmt = conn.createStatement();
                        actrset = actstmt.executeQuery("select * from action where id='" + actID + "'");
                        while(actrset.next()){
                            long id = actID;
                            Timestamp start = actrset.getTimestamp("start");
                            Timestamp end = actrset.getTimestamp("end");
                            String place = actrset.getString("place");
                            String name = actrset.getString("name");
                            String desc = actrset.getString("description");
                            Calendar stcal = Calendar.getInstance();
                                stcal.setTimeInMillis(start.getTime());
                                Calendar endcal = Calendar.getInstance();
                                stcal.setTimeInMillis(end.getTime());
                            Action a = new Action(id, stcal, endcal, place, name, desc);
                            actUser.addAction(a.getID());
                        }
                        
                    }
                }
                users.add(actUser);
            }
            return users;
        } catch (SQLException ex) {
            return null;
        }
    }

    public void editPassword(String login, String password) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                if (password != null) {

                    if (rset.getString("login").equals(login)) {
                        rset.updateString(2, password);
                        rset.updateRow();
                        JDBCManager.writeLog("Heslo uživatele s loginem: "
                                + login + ", bylo změněno.");
                        
                    }
                } else {
                    JDBCManager.writeLog("Heslo je povinný parametr, nelze ho nechat nevyplněné.");
                    throw new SystemRegException("Heslo je povinný parametr, nelze ho nechat nevyplněné.");
                }
            }
            

        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        } finally {
            close();
        }
    }

    public Collection<Action> getActions(String login) throws SystemRegException {
        actUser = null;
        Collection<Long> actionIDs = new ArrayList<Long>();
        Collection<Action> actions = new ArrayList<Action>();
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME);

            while (rset.next()) {
                if (rset.getString("login").equals(login)) {
                    long admID = rset.getLong(1);
                    Statement manstm = conn.createStatement();
                    manactrset = manstm.executeQuery("select * from admin_manages_action where Admin_id=" + admID);
                    while (manactrset.next()) {
                        long actId = manactrset.getLong("Action_id");
                        actionIDs.add(actId);

                    }
                    Statement newmanstm = conn.createStatement();
                    manactrset = newmanstm.executeQuery("select * from action");
                    while (manactrset.next()) {
                        long thisActID = manactrset.getLong(1);
                        Iterator it = actionIDs.iterator();
                        while (it.hasNext()) {
                            long aID = (Long) it.next();

                            if (aID == thisActID) {
                                long id = manactrset.getLong(1);
                                Timestamp st = manactrset.getTimestamp("start");
                                Timestamp end = manactrset.getTimestamp("end");
                                String place = manactrset.getString("place");
                                String name = manactrset.getString("name");
                                String description = manactrset.getString("description");
                                Calendar stcal = Calendar.getInstance();
                                stcal.setTimeInMillis(st.getTime());
                                Calendar endcal = Calendar.getInstance();
                                stcal.setTimeInMillis(end.getTime());
                                Action act = new Action(id, stcal, endcal, place, name, description);
                                actions.add(act);
                            }
                        }
                    }


                }
            }
            return actions;
        } catch (SQLException ex) {
            return null;
        }
    }

    public void addAction(String login, long actionID) throws SystemRegException {
        try {
            boolean exist = false;
            long adminID = 0;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from admin");
            while (rset.next()) {
                if (rset.getString("login").equals(login)) {
                    exist = true;
                    adminID = rset.getLong("id");
                }
            }
            if (exist) {
                Statement stm = conn.createStatement();
                int i = stm.executeUpdate("insert into admin_manages_action values ('" + adminID + "', '" + actionID + "')");
                if (i == 0) {
                    JDBCManager.writeLog("Chyba při vkládání nového účastníka do tabulky "
                            + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                    throw new SystemRegException("Chyba při vkládání nového účastníka do tabulky "
                            + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                }
            } else {
                JDBCManager.writeLog("Chyba při přidávání účasti účastníkovi do tabulky "
                        + TABLE_NAME + ". Tabulka již tento záznam obsahuje!");
                throw new SystemRegException("Chyba při přidávání účasti účastníkovi do tabulky "
                        + TABLE_NAME + ". Tabulka již tento záznam obsahuje!");
            }


        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
        } finally {
            close();
        }
    }

    public void removeAction(String login, long actionID) throws SystemRegException {

        try {
            boolean exist = false;
            long adminID = 0;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from admin");
            while (rset.next()) {
                if (rset.getString("login").equals(login)) {
                    exist = true;
                    adminID = rset.getLong("id");
                }
            }
            if (exist) {
                rset = stmt.executeQuery("select * from admin_manages_action where Admin_id=" + adminID);
                while (rset.next()) {

                    long did;
                    if ((did = rset.getLong("Action_id")) == actionID) {
                        rset.deleteRow();
                        JDBCManager.writeLog("Z tabulky admin_manages_action byla odstraněna akce s ID: " + did);
                        return;
                    }

                }

            } else {
                JDBCManager.writeLog("Chyba při přidávání účasti adminovi do tabulky admin_manages_action.");
                throw new SystemRegException("Chyba při přidávání účasti adminovi do tabulky admin_manages_action.");
            }


        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
        } finally {
            close();
        }
    }

    private void close() {
        JDBCManager.closeResultSet(rset);
        JDBCManager.closeStatement(stmt);
        JDBCManager.closeConnection(conn);
        conn = null;
        stmt = null;
        rset = null;
    }
}
