/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface;

import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Additional;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Participant;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.utils.Generator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Lahvi
 * 
 */
public class ParticipantDAO {

    private static ParticipantDAO instance;
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    private ResultSet additset;
    private ResultSet actionrset;
    private ResultSet presrset;
    private Participant tempPart;
    private final String TABLE_NAME = "user";

    public static ParticipantDAO getInstance() {
        if (instance == null) {
            instance = new ParticipantDAO();
        }
        return instance;
    }

    private ParticipantDAO() {
    }

    public void createParticipant(String fName, String lName, String email, long personalID, long actionID, Additional adInfo) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();

            int i = stmt.executeUpdate("insert into " + TABLE_NAME + " values "
                    + "(DEFAULT, '" + personalID + "', '" + fName + "', '" + lName + "', '" + null
                    + "', '" + null + "', '" + email + "')");
            if (i == 0) {
                JDBCManager.writeLog("Chyba při vkládání nového účastníka do tabulky "
                        + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                throw new SystemRegException("Chyba při vkládání nového účastníka do tabulky "
                        + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
            }
            Statement partstm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            long userID = -1;
            rset = partstm.executeQuery("select * from user where IDCard=" + personalID);
            if (rset.next()) {
                userID = rset.getLong("id");
            }


            if (adInfo != null) {
                Map<String, String> mapa = adInfo.getAdditionalParams();
                Iterator iterator = mapa.keySet().iterator();

                while (iterator.hasNext()) {
                    String key = iterator.next().toString();
                    String value = mapa.get(key).toString();

                    Statement additstmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    int j = additstmt.executeUpdate("insert into additional_info values "
                            + "(DEFAULT, '" + key + "', '" + value + "', '" + userID + "')");
                    if (j == 0) {
                        JDBCManager.writeLog("Chyba při vkládání nového účastníka do tabulky "
                                + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                        throw new SystemRegException("Chyba při vkládání nového účastníka do tabulky "
                                + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                    }
                }
            }


            Statement partstmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            int k = partstmt.executeUpdate("insert into participation values "
                    + "(DEFAULT, '" + actionID + "', '" + userID + "')");
            if (k == 0) {
                JDBCManager.writeLog("Chyba při vkládání nového účastníka do tabulky "
                        + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                throw new SystemRegException("Chyba při vkládání nového účastníka do tabulky "
                        + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
            }




        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
        } finally {
            close();
        }

    }

    public Participant getParticipant(long personalID) throws SystemRegException {
        tempPart = null;
        try {
            Collection<Long> aID = new ArrayList<Long>();
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from USER where id=" + personalID);
            Statement additstmt = conn.createStatement();
            additset = additstmt.executeQuery("select * from additional_info where User_id=" + personalID);
            Statement actionstmt = conn.createStatement();
            actionrset = actionstmt.executeQuery("select * from participation where User_id=" + personalID);
            while (rset.next()) {

                boolean cmpltReg = false;
                long pid = rset.getLong(1);
                long idCard = rset.getLong(2);
                String pName = rset.getString(3);
                String pSurname = rset.getString(4);
                String pLogin = null;
                if (rset.getString("login") != null) {
                    pLogin = rset.getString(5);
                    cmpltReg = true;
                }
                String pPass = null;
                if (rset.getString("password") != null) {
                    pPass = rset.getString(6);
                }
                String email = rset.getString(7);

                Additional addit = new Additional();
                while (additset.next()) {
                    if (additset.getInt("User_id") == personalID) {

                        String adName = additset.getString("key");
                        String adVal = additset.getString("value");
                        addit.addAdditonalParam(adName, adVal);
                    }
                }

                while (actionrset.next()) {
                    if (actionrset.getInt("User_id") == personalID) {
                        long actID = actionrset.getInt("Action_id");
                        aID.add(actID);
                    }
                }







                tempPart = new Participant(pName, pSurname, email, idCard, pid, addit, aID);
                tempPart.setLogin(pLogin);
                tempPart.setPassword(pPass);
                if (cmpltReg) {
                    tempPart.setCompleteReg();
                }


                return tempPart;




            }
            JDBCManager.writeLog("Chyba při hledání účastníka s ID: "
                    + personalID + ". Požadovaný účastník neexistuje!");
            throw new SystemRegException("Chyba při hledání účastníka s ID: "
                    + personalID + ". Požadovaný účastník neexistuje!");



        } catch (SQLException ex) {
            return null;
        }
    }

    public Participant getParticipant(String login) throws SystemRegException {
        tempPart = null;
        try {
            Collection<Long> aID = null;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from USER where login=" + login);

            while (rset.next()) {
                if (rset.getString("login").equals(login)) {
                    boolean cmpltReg = false;
                    long pid = rset.getLong(1);
                    long idCard = rset.getLong(2);
                    String pName = rset.getString(3);
                    String pSurname = rset.getString(4);
                    String pLogin = null;
                    if (rset.getString("login") != null) {
                        pLogin = rset.getString(5);
                        cmpltReg = true;
                    }
                    String pPass = null;
                    if (rset.getString("password") != null) {
                        pPass = rset.getString(6);
                    }
                    String email = rset.getString(7);
                    additset = stmt.executeQuery("select * from additional_info where User_id=" + pid);
                    actionrset = stmt.executeQuery("select * from participation where User_id=" + pid);

                    Additional addit = new Additional();
                    while (additset.next()) {
                        if (additset.getInt("User_id") == pid) {

                            String adName = additset.getString("key");
                            String adVal = additset.getString("value");
                            addit.addAdditonalParam(adName, adVal);
                        }
                    }
                    while (actionrset.next()) {
                        if (actionrset.getInt("User_id") == pid) {
                            long actID = actionrset.getInt("Action_id");
                            aID.add(actID);
                        }
                    }




                    tempPart = new Participant(pName, pSurname, email, idCard, pid, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);
                    if (cmpltReg) {
                        tempPart.setCompleteReg();
                    }



                    return tempPart;

                }

            }

            JDBCManager.writeLog("Chyba při hledání účastníka s loginem: "
                    + login + ". Požadovaný login neexistuje!");
            throw new SystemRegException("Chyba při hledání účastníka s loginem: "
                    + login + ". Požadovaný login neexistuje!");





        } catch (SQLException ex) {
            return null;
        }
    }

    public void completeRegistration(long personalID, long actionID) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where IDCard=" + personalID);

            long partID = -1;
            while (rset.next()) {
                long idPart = rset.getLong(1);
                if (!rset.getString(5).equals("null")) {

                    JDBCManager.writeLog("Registrace tohoto účastníka je již dokončena.");
                } else {

                    Statement actstmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    actionrset = actstmt.executeQuery("select * from participation where Action_id=" + actionID);
                    while (actionrset.next()) {
                        if (actionrset.getLong("User_id") == idPart) {
                            partID = actionrset.getLong("id");
                            String genLgn = Generator.randomString();
                            rset.updateString("login", genLgn);
                            String genPass = Generator.randomString();
                            rset.updateString("password", genPass);
                            rset.updateRow();
                        }
                    }






                    Timestamp t = new Timestamp(System.currentTimeMillis());
                    Statement partstmt = conn.createStatement();
                    int k = partstmt.executeUpdate("INSERT INTO presence(checkin, Participation_id) VALUES ('" + t + "', '" + partID + "');");
                    if (k == 0) {
                        JDBCManager.writeLog("Chyba při vkládání nového účastníka do tabulky "
                                + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                        throw new SystemRegException("Chyba při vkládání nového účastníka do tabulky "
                                + TABLE_NAME + ". Vložením nebyl ovlivněn žádný rádek!");
                    }



                    JDBCManager.writeLog("Byla dokončena registrace účastníka s ID: "
                            + personalID + ".");
                }


            }
        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        } finally {
            close();
        }
    }

    public void changeParams(String email, String firstName, String lastName, long personalID) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where id=" + personalID);
            while (rset.next()) {
                if (email != null && firstName != null && lastName != null) {

                    if (rset.getInt(1) == personalID) {
                        rset.updateString(3, firstName);
                        rset.updateString(4, lastName);
                        rset.updateString(7, email);
                        JDBCManager.writeLog("Údaje účastníka s ID: "
                                + personalID + ", byly změněny.");
                        rset.updateRow();

                    }
                } else {
                    JDBCManager.writeLog("Email a jméno sou povinnými parametry, nelze je nechat nevyplněné.");
                    throw new SystemRegException("Email a jméno sou povinnými parametry, nelze je nechat nevyplněné.");
                }
            }
            JDBCManager.writeLog("Chyba při hledání účastníka s id: "
                    + personalID + ". Požadovaný účastník neexistuje!");
            throw new SystemRegException("Chyba při hledání účastníka s loginem: "
                    + personalID + ". Požadovaný účastník neexistuje!");

        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        } finally {
            close();
        }
    }

    public void setLogin(long id, String login, String password) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where id=" + id);
            while (rset.next()) {
                if (rset.getLong(1) == id) {
                    if (login != null) {
                        rset.updateString(5, login);
                        rset.updateString(6, password);
                        rset.updateRow();
                        JDBCManager.writeLog("Byl změněn login účastníka s id: "
                                + id + ", na: " + login);
                    } else {
                        JDBCManager.writeLog("Uživatel s id: "
                                + id + " nemá dokončenou registraci, nejde mu tedy změnit login.");
                        throw new SystemRegException("Uživatel s id: "
                                + id + " nemá dokončenou registraci, nejde mu tedy změnit login.");
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

    public void deleteParticipant(long personalID) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where ID='" + personalID + "'");
            Statement additstm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            additset = additstm.executeQuery("select * from additional_info where User_id=" + personalID);
            Statement actionstm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            actionrset = actionstm.executeQuery("select * from participation where User_id=" + personalID);


            while (additset.next()) {
                long did;
                if ((did = additset.getLong("User_id")) == personalID) {
                    additset.deleteRow();
                    JDBCManager.writeLog("Z tabulky addittional_info byly odstraněny udaje o uzivateli s ID: " + did);
                    return;
                }
            }
            while (actionrset.next()) {
                long did;
                if ((did = actionrset.getLong("User_id")) == personalID) {
                    actionrset.deleteRow();
                    JDBCManager.writeLog("Z tabulky participation byla odstraněna akce s ID: " + did);
                    return;
                }
            }
            while (rset.next()) {
                long did;
                if ((did = rset.getLong(1)) == personalID) {
                    rset.deleteRow();
                    JDBCManager.writeLog("Z tabulky " + TABLE_NAME + " byla odstraněna akce s ID: " + did);
                    return;
                }
            }

            JDBCManager.writeLog("Chyba při odstraňování akce s ID: "
                    + personalID + ". Požadovaná akce neexistuje!");
            throw new SystemRegException("Chyba při odstraňování akce s ID: "
                    + personalID + ". Požadovaná akce neexistuje!");
        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba v dtb operaci při odstraňování akce!" + ex.getMessage());
            throw new SystemRegException("SQL chyba v dtb operaci při odstraňování akce" + ex.getMessage());
        } finally {
            close();
        }
    }

    public void deleteParticipant(String login) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where login='" + login + "'");
            while (rset.next()) {
                String dL;
                if ((dL = rset.getNString("login")).equals(login)) {
                    long pid = rset.getLong(1);
                    additset = stmt.executeQuery("select * from additional_info where User_id=" + pid);
                    actionrset = stmt.executeQuery("select * from participation where User_id=" + pid);
                    while (additset.next()) {
                        int did;
                        if ((did = additset.getInt("User_id")) == pid) {
                            additset.deleteRow();
                            JDBCManager.writeLog("Z tabulky addittional_info byly odstraněny informace o uživateli s ID: " + did);
                            return;
                        }
                    }
                    while (actionrset.next()) {
                        int did;
                        if ((did = actionrset.getInt("User_id")) == pid) {
                            actionrset.deleteRow();
                            JDBCManager.writeLog("Z tabulky participation byla odstraněna účast uživatele s ID: " + did);
                            return;
                        }
                    }

                    rset.deleteRow();
                    JDBCManager.writeLog("Z tabulky " + TABLE_NAME + " byl odstraněn účastník s loginem: " + dL);
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

    public Collection<Participant> getAllParticipants(long actionID) throws SystemRegException {
        tempPart = null;

        try {
            Collection<Participant> col = new ArrayList<Participant>();
            Collection<Long> aID = new ArrayList<Long>();
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                boolean cmplt = false;
                boolean isOnThis = false;
                long id = rset.getLong(1);
                long idCard = rset.getLong(2);
                String name = rset.getString(3);
                String surname = rset.getString(4);
                String pLogin = null;
                if (rset.getString("login") != null) {
                    pLogin = rset.getString(5);

                }
                String pPass = null;
                if (rset.getString("password") != null) {
                    pPass = rset.getString(6);
                    cmplt = true;
                }
                String email = rset.getString(7);
                Statement stmAddit = conn.createStatement();
                additset = stmAddit.executeQuery("select * from additional_info where User_id=" + id);
                Statement stmAction = conn.createStatement();
                actionrset = stmAction.executeQuery("select * from participation where User_id=" + id);

                Additional addit = null;
                while (additset.next()) {
                    if (additset.getInt("User_id") == id) {

                        String adName = additset.getString("key");
                        String adVal = additset.getString("value");
                        addit.addAdditonalParam(adName, adVal);
                    }
                }

                while (actionrset.next()) {
                    if (actionrset.getInt("User_id") == id) {
                        long actID = actionrset.getInt("Action_id");
                        if (actID == actionID) {
                            isOnThis = true;
                        }
                        aID.add(actID);
                    }
                }

                if (isOnThis) {
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);

                    if (cmplt) {
                        tempPart.setCompleteReg();
                    }

                    col.add(tempPart);
                }




            }

            return col;

        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
            return null;
        } finally {

            close();
        }
    }

    public Collection<Participant> getCompleteRegParticipants(long actionID) throws SystemRegException {
        tempPart = null;

        try {
            Collection<Participant> col = new ArrayList<Participant>();
            Collection<Long> aID = new ArrayList<Long>();
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                boolean cmplt = false;
                boolean isOnThis = false;
                long id = rset.getLong(1);
                long idCard = rset.getLong(2);
                String name = rset.getString(3);
                String surname = rset.getString(4);
                String pLogin = null;
                if (!rset.getString("login").equals("null")) {
                    pLogin = rset.getString(5);
                    cmplt = true;
                }
                String pPass = null;
                if (!rset.getString("password").equals("null")) {
                    pPass = rset.getString(6);
                    cmplt = true;
                }
                String email = rset.getString(7);
                
                Statement addtistmt = conn.createStatement();
                additset = addtistmt.executeQuery("select * from additional_info where User_id=" + id);
                Statement actionstmt = conn.createStatement();
                actionrset = actionstmt.executeQuery("select * from participation");

                Additional addit = new Additional();
                while (additset.next()) {
                    if (additset.getLong("User_id") == id) {

                        String adName = additset.getString("key");
                        String adVal = additset.getString("value");
                        addit.addAdditonalParam(adName, adVal);
                    }
                }

                while (actionrset.next()) {
                    if (actionrset.getInt("User_id") == id) {
                        long actID = actionrset.getInt("Action_id");
                        if (actID == actionID) {
                            isOnThis = true;
                        }
                        aID.add(actID);
                    }
                }
                
                if (isOnThis&&cmplt) {
                   
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);
                    tempPart.setCompleteReg();
                        col.add(tempPart);
                    


                }else{
                     
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);
                    
                }




            }

            return col;

        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
            return null;
        } finally {

            close();
        }

    }

    public Collection<Participant> getIncompleteRegParticipants(long actionID) throws SystemRegException {
        tempPart = null;

        try {
            Collection<Participant> col = new ArrayList<Participant>();
            Collection<Long> aID = new ArrayList<Long>();
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                boolean cmplt = false;
                boolean isOnThis = false;
                long id = rset.getLong(1);
                long idCard = rset.getLong(2);
                String name = rset.getString(3);
                String surname = rset.getString(4);
                String pLogin = null;
                if (!rset.getString("login").equals("null")) {
                    pLogin = rset.getString(5);
                    cmplt = true;
                }
                String pPass = null;
                if (!rset.getString("password").equals("null")) {
                    pPass = rset.getString(6);
                    cmplt = true;
                }
                String email = rset.getString(7);
                
                Statement addtistmt = conn.createStatement();
                additset = addtistmt.executeQuery("select * from additional_info where User_id=" + id);
                Statement actionstmt = conn.createStatement();
                actionrset = actionstmt.executeQuery("select * from participation");

                Additional addit = new Additional();
                while (additset.next()) {
                    if (additset.getLong("User_id") == id) {

                        String adName = additset.getString("key");
                        String adVal = additset.getString("value");
                        addit.addAdditonalParam(adName, adVal);
                    }
                }

                while (actionrset.next()) {
                    if (actionrset.getInt("User_id") == id) {
                        long actID = actionrset.getInt("Action_id");
                        if (actID == actionID) {
                            isOnThis = true;
                        }
                        aID.add(actID);
                    }
                }
                
                if (isOnThis&&!cmplt) {
                   
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);
                        col.add(tempPart);
                    


                }else{
                     
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);
                    tempPart.setCompleteReg();
                }




            }

            return col;

        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
            return null;
        } finally {

            close();
        }
    }

    public Collection<Participant> getPresent(long actionID) throws SystemRegException {
        tempPart = null;

        try {
            Collection<Participant> col = new ArrayList<Participant>();
            Collection<Long> aID = new ArrayList<Long>();
            long partID = -1;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                boolean cmplt = false;
                boolean isOnThis = false;
                boolean isPresent = false;
                long id = rset.getLong(1);
                long idCard = rset.getLong(2);
                String name = rset.getString(3);
                String surname = rset.getString(4);
                String pLogin = null;
                if (rset.getString("login") != null) {
                    pLogin = rset.getString(5);

                }
                String pPass = null;
                if (rset.getString("password") != null) {
                    pPass = rset.getString(6);
                    cmplt = true;
                }
                String email = rset.getString(7);

                Statement additstmt = conn.createStatement();
                additset = additstmt.executeQuery("select * from additional_info where User_id=" + id);
                Statement actionstmt = conn.createStatement();
                actionrset = actionstmt.executeQuery("select * from participation where User_id=" + id);

                Additional addit = new Additional();
                while (additset.next()) {
                    if (additset.getInt("User_id") == id) {

                        String adName = additset.getString("key");
                        String adVal = additset.getString("value");
                        addit.addAdditonalParam(adName, adVal);
                    }
                }

                if (actionrset.next()) {
                    if (actionrset.getInt("User_id") == id) {
                        long actID = actionrset.getInt("Action_id");
                        if (actID == actionID) {
                            isOnThis = true;
                            partID = actionrset.getLong(1);
                            Statement presstmt = conn.createStatement();
                            presrset = presstmt.executeQuery("select * from presence where Participation_id=" + partID);
                            while (presrset.next()) {
                                if (presrset.getLong("Participation_id") == partID) {
                                    if (presrset.getTimestamp("checkout") == null) {
                                        isPresent = true;
                                        continue;
                                    }
                                }
                            }
                        }
                        aID.add(actID);
                    }
                }



                if (isOnThis) {
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);

                    if (cmplt) {
                        tempPart.setCompleteReg();
                    }
                    if (isPresent) {
                        col.add(tempPart);
                    }

                }




            }

            return col;

        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
            return null;
        } finally {

            close();
        }
    }

    public Collection<Participant> getAbsent(long actionID) throws SystemRegException {
        tempPart = null;

        try {
            Collection<Participant> col = new ArrayList<Participant>();
            Collection<Long> aID = new ArrayList<Long>();
            long partID = -1;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from " + TABLE_NAME);
            while (rset.next()) {
                boolean cmplt = false;
                boolean isOnThis = false;
                boolean isPresent = false;
                long id = rset.getLong(1);
                long idCard = rset.getLong(2);
                String name = rset.getString(3);
                String surname = rset.getString(4);
                String pLogin = null;
                if (rset.getString("login") != null) {
                    pLogin = rset.getString(5);

                }
                String pPass = null;
                if (rset.getString("password") != null) {
                    pPass = rset.getString(6);
                    cmplt = true;
                }
                String email = rset.getString(7);

                Statement additstmt = conn.createStatement();
                additset = additstmt.executeQuery("select * from additional_info where User_id=" + id);
                Statement actionstmt = conn.createStatement();
                actionrset = actionstmt.executeQuery("select * from participation where User_id=" + id);

                Additional addit = new Additional();
                while (additset.next()) {
                    if (additset.getInt("User_id") == id) {

                        String adName = additset.getString("key");
                        String adVal = additset.getString("value");
                        addit.addAdditonalParam(adName, adVal);
                    }
                }

                if (actionrset.next()) {
                    if (actionrset.getInt("User_id") == id) {
                        long actID = actionrset.getInt("Action_id");
                        if (actID == actionID) {
                            isOnThis = true;
                            partID = actionrset.getLong(1);
                            Statement prestmt = conn.createStatement();
                            presrset = prestmt.executeQuery("select * from presence where Participation_id=" + partID);
                            while (presrset.next()) {
                                if (presrset.getLong("Participation_id") == partID) {
                                    if (presrset.getTimestamp("checkout") == null) {
                                        isPresent = true;
                                        continue;
                                    }
                                }
                            }
                        }
                        aID.add(actID);
                    }
                }



                if (isOnThis) {
                    tempPart = new Participant(name, surname, email, idCard, id, addit, aID);
                    tempPart.setLogin(pLogin);
                    tempPart.setPassword(pPass);

                    if (cmplt) {
                        tempPart.setCompleteReg();
                    }
                    if (!isPresent) {
                        col.add(tempPart);
                    }

                }




            }

            return col;

        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
            return null;
        } finally {

            close();
        }
    }

    public void changePresence(long id, long actionID) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from participation where User_id=" + id);

            while (rset.next()) {
                if (rset.getLong("Action_id") == actionID) {
                    long partID = rset.getLong(1);
                    Statement prestmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
                    presrset = prestmt.executeQuery("select * from presence where Participation_id=" + partID);
                    while (presrset.next()) {
                        if (presrset.getLong("Participation_id") == partID) {
                            if (presrset.getTimestamp("checkout") == null) {
                                Timestamp chckout = new Timestamp(System.currentTimeMillis());
                                presrset.updateTimestamp(3, chckout);
                                presrset.updateRow();

                            } else {
                                Timestamp chckin = new Timestamp(System.currentTimeMillis());
                                presrset.updateTimestamp(2, chckin);
                                presrset.updateDate(3, null);
                                presrset.updateRow();
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

    public void addAction(long id, long actionID) throws SystemRegException {
        try {
            boolean exist = false;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from participation where User_id=" + id);
            while (rset.next()) {
                if (rset.getLong("Action_id") == actionID) {
                    exist = true;
                }
            }
            if (!exist) {
                Statement stm = conn.createStatement();

                int i = stm.executeUpdate("insert into participation values "
                        + "(DEFAULT, '" + actionID + "', '" + id + "')");

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

    public void addActions(long id, Collection<Action> actions) throws SystemRegException {
        try {
            boolean exist = false;
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from participation where User_id=" + id);
            Iterator it = actions.iterator();
            while (it.hasNext()) {
                Action a = (Action) it.next();
                long actionID = a.getID();
                while (rset.next()) {
                    if (rset.getLong("Action_id") == actionID) {
                        exist = true;
                    }
                }
                if (!exist) {
                    Statement stm = conn.createStatement();
                    int i = stm.executeUpdate("insert into " + TABLE_NAME + " values ('" + actionID + "', '" + id + "')");
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
            }

        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba při vkládání nového uživatele do tabulky "
                    + TABLE_NAME + ". " + ex.getMessage());
        } finally {
            close();
        }

    }

    public void removeAction(long id, long actionID) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from participation where User_id='" + id + "'");


            while (rset.next()) {
                long did;
                if ((did = rset.getLong("Action_id")) == actionID) {
                    rset.deleteRow();
                    JDBCManager.writeLog("Z tabulky " + TABLE_NAME + " byla odstraněna akce s ID: " + did);
                    return;
                }
            }

            JDBCManager.writeLog("Chyba při odstraňování akce s ID: "
                    + actionID + ". Požadovaná akce neexistuje!");
            throw new SystemRegException("Chyba při odstraňování akce s ID: "
                    + actionID + ". Požadovaná akce neexistuje!");
        } catch (SQLException ex) {
            JDBCManager.writeLog("SQL chyba v dtb operaci při odstraňování akce!" + ex.getMessage());
            throw new SystemRegException("SQL chyba v dtb operaci při odstraňování akce" + ex.getMessage());
        } finally {
            close();
        }
    }

    private void close() {
        JDBCManager.closeConnection(conn);
        JDBCManager.closeStatement(stmt);
        JDBCManager.closeResultSet(rset);
        conn = null;
        stmt = null;
        rset = null;
    }
}
