/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ondra
 */
public class Database {

    private final String URL = "jdbc:mysql://localhost:3306/mydb";
    private final String USER = "root";
    private final String PASSWORD = "";
    
    public Database() {
        
    }
    
    public ResultSet executeQuery(String query) {
        
        return null;
    }
    
    public String getVersion() {
        ResultSet rs  = executeQuery("SELECT VERSION()");
        try {
            if (rs.next()) {
                    return rs.getString(1);
                }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<RegSysAction> getAkce() {
        ArrayList<RegSysAction> al = new ArrayList<RegSysAction>();


        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM action");
            while (rs.next()) {
                    al.add(new RegSysAction(rs.getInt("id"), rs.getString("name")));
                }

        } catch (SQLException ex) {
            // throw exception
            System.out.println("Nepodaril ose pripojit k DB!");

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                //
            }
        }
        return al;
    }

}
