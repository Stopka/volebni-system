/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import core.data_tier.entities.Action;
import core.data_tier.entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public class ActionTableModel extends AbstractTableModel{
    private List<Action> actions;
    
    public ActionTableModel(){
        User u = Globals.getInstance().getLogedUser();
        this.actions = new ArrayList<Action>(Globals.getInstance().getActionOps().getActions(u.getActions()));
    }
    
    public void refreshModel(){
        User u = Globals.getInstance().getLogedUser();
        this.actions = new ArrayList<Action>(Globals.getInstance().getActionOps().getActions(u.getActions()));
        fireTableDataChanged();
    }
    
    public Action getAction(int rowIndex){
        return actions.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return actions.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    public Collection<Action> getActions() {
        return actions;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Action sA = actions.get(rowIndex);
        switch(columnIndex){
            case 0:
                return sA.getID();
            case 1:
                return sA.getName();
            case 2:
                return sA.getPlace();
            case 3:
                return sA.getStartDateString();
            case 4:
                return sA.getEndDateString();
        }
        return null;
    }
    
    /**
     * 
     * @param column
     * @return 
     */
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Název";
            case 2:
                return "Místo konání";
            case 3:
                return "Datum zahájení";
            case 4:
                return "Datum ukončení";
        }
        return null;
    }
    
    /**
     * Vrací třídu daného sloupečku {@code int c}.
     * @param c číslo daného sloupce.
     * @return třída sloupce.
     */
    @Override
    public Class getColumnClass(int c){
        Object o = getValueAt(0, c);
        //pokud není v tabulce žádný záznam tak vracíme prázdný Object
        if(o == null)
            o = new Object();
        return o.getClass();
    }
}
