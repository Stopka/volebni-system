/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.data_tier;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;

/**
 *
 * @author Lahvi
 */
public class ParticipantDAO {
    private static ParticipantDAO instance;
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    
    public static ParticipantDAO getInstance(){
        if(instance == null){
            instance = new ParticipantDAO();
        }
        return instance;
    }
    
    private ParticipantDAO(){}
    
    public Participant getParticipant(int personalID) throws SystemRegException{
        return null;
    }
    
    public Participant getParticipant(String login) throws SystemRegException{
        return null;
    }
    
    public void completeRegistration(int personalID) throws SystemRegException{
        
    }
    
    public void changeParams(String email, String address){
        
    }
    
    public void changeLogin(String login, String password){
        
    }
    
    public void deleteParticipant(int personalID) throws SystemRegException{
        
    }
    
    public void deleteParticipnat(String login) throws SystemRegException{
        
    }
    
    public Collection<Participant> getAllParticipants(){
        return null;
    }
    
    private void close(){
        JDBCManager.closeConnection(conn);
        JDBCManager.closeStatement(stmt);
        JDBCManager.closeResultSet(rset);
        conn = null; stmt = null; rset = null;
    }
}
