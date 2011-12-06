/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys;

import cz.cvut.fel.mvod.persistence.DAOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ondra
 */
public class RegSysDAOImpl implements RegSysDAO {
    
    Database db;
    
    public RegSysDAOImpl() {
        db = new Database();
    }
    @Override
    public ArrayList<RegSysParticipant> getParticipant(int id_participant, int id_akce) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<RegSysAction> getAkce() throws DAOException {
        ArrayList<RegSysAction> al = db.getAkce();
        System.out.println("LIST: l="+al.size());
        System.out.println("first: id="+al.get(0).getId()+" name="+al.get(0).getName());
        return al;
        
    }

    @Override
    public String getDBVersion() {
        return db.getVersion();
    }
    
}
