/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys;

import cz.cvut.fel.mvod.persistence.DAOException;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.ActionDAO;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.ParticipantDAO;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.SystemRegException;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Action;
import cz.cvut.fel.mvod.persistence.regsys.commonInterface.entities.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ondra
 */
public class RegSysDAOImpl implements RegSysDAO {
    
    ParticipantDAO pDao;
    ActionDAO aDao;
    
    private boolean importEnabled = false;
    private Action action; 
    
    public RegSysDAOImpl() {
        pDao = ParticipantDAO.getInstance();
        aDao = ActionDAO.getInstance();
    }
    @Override
    public ArrayList<Participant> getParticipants() throws DAOException {
        ArrayList<Participant> al = new ArrayList();
        Collection c = null;
        try {
            c = pDao.getPresent(action.getID());
        } catch (SystemRegException ex) {
            System.out.println("Chyba!");
            throw new DAOException("Vyskytla se chyba při komunikaci s registračním serverem\nZkontrolujte připojení k serveru");
        }
        if(c != null) {
            al = new ArrayList(c);
        } else {
            System.out.println("c je null");
        }
        return al;
    }

    @Override
    public ArrayList<Action> getAkce() throws DAOException {
        ArrayList<Action> al = new ArrayList();
        try {
            al = new ArrayList(aDao.getActions());
            //System.out.println("LIST: l="+al.size());
            if(al.size() == 0) {
                throw new DAOException("Na registračním serveru nebyla nalezena žádná akce.");
            }
            return al;
        } catch (SystemRegException ex) {
            throw new DAOException("Vyskytla se chyba při komunikaci s registračním serverem\nZkontrolujte připojení k serveru");
        }
        
        //System.out.println("first: id="+al.get(0).getId()+" name="+al.get(0).getName());
        //return null;
        
    }

    /**
     * @return the isEnabled
     */
    public boolean isImportEnabled() {
        return importEnabled;
    }

    /**
     * @param isEnabled the isEnabled to set
     */
    public void setImportEnabled(boolean isEnabled) {
        this.importEnabled = isEnabled;
    }

    /**
     * @return the actionId
     */
    public Action getAction() {
        return action;
    }

    /**
     * @param actionId the actionId to set
     */
    public void setAction(Action action) {
        this.action = action;
        
    }
    
}
