/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier;

import core.SystemRegException;
import core.data_tier.entities.Action;
import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 *
 * @author Lahvi
 */
public class ActionDAO {

    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    private Action tempAct;
    private final String TABLE_NAME = "actions";
    private static ActionDAO instance;

    public static ActionDAO getInstance(){
        if(instance == null)
            instance = new ActionDAO();
        return instance;
    }
    
    private ActionDAO() {
    }

    /**
     * 
     * @param name
     * @param place
     * @param startDate
     * @param endDate 
     */
    public void createAction(String name, String place, Date startDate, Date endDate, String desc) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            //tempAct = new Action(0, startDate, endDate, place, name);
            int i = stmt.executeUpdate("insert into " + TABLE_NAME + " values "
                    + "(DEFAULT, '" + name + "', '" + place + "', '" + desc + "', '" + startDate
                    + "', '" + endDate + "')");
            if (i == 0) {
                JDBCManager.writeLog("Nepodařilo se vložit novou akci do databáze!");
                throw new SystemRegException("Nepodařilo se vložit novou akci do databáze!");
            }
        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        }  finally {
            close();
        }
    }

    /**
     * 
     * @param ID
     * @return 
     */
    public Action getAction(int ID) throws SystemRegException {
        tempAct = null;
        try{
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from ACTIONS where id="+ID);
            if(rset.next()){
                if(rset.getInt(1) == ID){
                    int aid = rset.getInt(1);
                    String aname = rset.getString(2);
                    String aplace = rset.getString(3);
                    Date startDate = rset.getDate(4);
                    Date endDate = rset.getDate(5);
                    String adesc = rset.getString(6);
                    
                    Calendar startC = Calendar.getInstance();
                    startC.setTime(startDate);
                    Calendar endC = Calendar.getInstance();
                    endC.setTime(endDate);
                    tempAct = new Action(aid, startC, endC, aplace, aname, adesc);
                }
            }
            return tempAct;
        } catch (SQLException ex){
            return null;
        } 
    }

    /**
     * 
     * @param name
     * @return 
     */
    public Action getAction(String name) throws SystemRegException {
        tempAct = null;
        try{
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("select * from ACTIONS where name="+name);
            if(rset.next()){
                if(rset.getString(2).equals(name)){
                    int aid = rset.getInt(1);
                    String aname = rset.getString(2);
                    String aplace = rset.getString(3);
                    Date startDate = rset.getDate(4);
                    Date endDate = rset.getDate(5);
                    String adesc = rset.getString(6);
                    
                    Calendar startC = Calendar.getInstance();
                    startC.setTime(startDate);
                    Calendar endC = Calendar.getInstance();
                    endC.setTime(endDate);
                    tempAct = new Action(aid, startC, endC, aplace, aname, adesc);
                }
            }
            return tempAct;
        } catch (SQLException ex){
            return null;
        }      
    }

    public void setDates(int ID, Date startDate, Date endDate) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where id=" + ID);
            if (rset.next()) {
                if (rset.getInt(1) == ID) {
                    if(startDate != null){
                        rset.updateDate("startDate", startDate);
                        JDBCManager.writeLog("Bylo aktualizováno datum zahájení akce "
                                + ID + ", na: " + startDate);
                    }
                    if(endDate != null){
                        rset.updateDate("endDate", endDate);
                        JDBCManager.writeLog("Bylo aktualizováno datum ukončení akce "
                                + ID + ", na: " + endDate);
                    }
                }
            }
        } catch (SQLException ex) {
            JDBCManager.writeLog("Něco se pokazilo při vytváření spojení! " + ex.getMessage());
            throw new SystemRegException("Něco se pokazilo při vytváření spojení!");
        }  finally {
            close();
        }
    }

    public void setParams(int ID, String name, String place, String desc) throws SystemRegException {
        try {
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where id=" + ID);
            if (rset.next()) {

                if (rset.getInt(1) == ID) {
                    if (name != null) {
                        rset.updateString("name", name);
                        JDBCManager.writeLog("Bylo aktualizované jméno akce "
                                + ID + ", na: " + name);
                    }
                    if (place != null) {
                        rset.updateString("place", place);
                        JDBCManager.writeLog("Bylo aktualizované místo konání akce "
                                + ID + ", na: " + place);
                    }
                    if (desc != null) {
                        rset.updateString("desc", desc);
                        JDBCManager.writeLog("Byl aktualizovan popis akce " + ID
                                + ", na: " + desc);
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
    /**
     * 
     * @param ID
     * @throws SystemRegException 
     */
    public void deleteAction(int ID) throws SystemRegException {
        try{
            conn = JDBCManager.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rset = stmt.executeQuery("select * from " + TABLE_NAME + " where ID='" + ID + "'");
            if (rset.next()) {
                int did;
                if ((did = rset.getInt(1)) == ID) {
                    rset.deleteRow();
                    JDBCManager.writeLog("Z tabulky " + TABLE_NAME + " byla odstraněna akce s ID: " + did);
                    return;
                }
            }
            JDBCManager.writeLog("Chyba při odstraňování akce s ID: "
                    + ID + ". Požadovaná akce neexistuje!");
            throw new SystemRegException("Chyba při odstraňování akce s ID: "
                    + ID + ". Požadovaná akce neexistuje!");
        } catch (SQLException ex){
            JDBCManager.writeLog("SQL chyba v dtb operaci při odstraňování akce!" + ex.getMessage());
            throw new SystemRegException("SQL chyba v dtb operaci při odstraňování akce" + ex.getMessage());
        } finally {
            close();
        }
    }

    /**
     * Uzvaře veškerá otevřená spojení. Tzn ResultSet, Statement a Connection-
     */
    private void close() {
        JDBCManager.closeResultSet(rset);
        JDBCManager.closeStatement(stmt);
        JDBCManager.closeConnection(conn);
        conn = null;
        stmt = null;
        rset = null;
    }
    
}
