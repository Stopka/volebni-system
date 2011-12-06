/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys;

import cz.cvut.fel.mvod.persistence.DAOException;
import java.util.ArrayList;

/**
 *
 * @author Ondra
 */
public interface RegSysDAO {
        /**
	 * Vrati vsechny ucastniky akce.
	 * @param RegSysAkce akce 
	 * @return ArrayList<RegSysUcastnik>
	 * @throws DAOException pokud operace selže
	 */
	ArrayList<RegSysParticipant> getParticipant(int id_participant, int id_akce) throws DAOException;
        /**
	 * Vrati vsechny akce
	 * @return ArrayList<RegSysAkce>
	 * @throws DAOException pokud operace selže
	 */
	ArrayList<RegSysAction> getAkce() throws DAOException;
        
        /* testovaci metoda */
        String getDBVersion();
}
