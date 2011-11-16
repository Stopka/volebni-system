/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import core.data_tier.entities.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public class ParTableModel extends AbstractTableModel{

    public ParTableModel(){
        pars = new ArrayList<Participant>(Globals.getInstance().getParticipantOps().getAllParticipants());
    }
    List<Participant> pars;
    @Override
    public int getRowCount() {
        return pars.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Participant p = pars.get(rowIndex);
        switch (columnIndex) {
            case 0:
                if(p.getLogin() == null)
                    return "Nedokončená registrace";
                else
                    return  p.getLogin();
            case 1:
                return p.getName();
            case 2:
                return p.getEmail();
            case 3:
                return p.getpersonalCardID();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Login";
            case 1:
                return "Jméno";
            case 2:
                return "E-mail";
            case 3:
                return "Číslo OP";
        }
        return null;
    }

    public List<Participant> getBooks() {
        return pars;
    }
    
    public void setParticipantss(Collection<Participant> pars) {
        this.pars = new ArrayList(pars);
        Collections.<Participant>sort(this.pars, new Comparator<Participant>() {

            @Override
            public int compare(Participant o1, Participant o2) {
                return o1.getLogin().compareTo(o2.getLogin());
            }
        });
        fireTableDataChanged();

    }

    public Participant getBook(int index) {
        return pars.get(index);
    }
    
}
