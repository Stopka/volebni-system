/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import core.data_tier.entities.Participant;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Lahvi
 */
public abstract class AbstractParticipantTable extends JTable implements ListSelectionListener{
    private ParticipantTableModel model;
    
    public AbstractParticipantTable(){
        model = new ParticipantTableModel(WIDTH);
        super.setModel(model);
    }
    
    /**
     * Vrátí vybraného účastníka.
     * @return 
     */
    public Participant getSelectedParticipant() {
        int tabRow = getSelectedRow();
        if (tabRow != -1) {
            int modelIndex = convertRowIndexToModel(tabRow);
            if (modelIndex > -1) {
                return model.getParticipant(modelIndex);
            }
        }
        return null;
    }
    
    /**
     * Akce pro změnu vybraného indexu
     * @param e 
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        Participant p = getSelectedParticipant();
        Globals.getInstance().setSelectedParticipant(p);
    }
    
    protected abstract void doubleClickAction();
    
    public void setModel(ParticipantTableModel model){
        this.model = model;
        setModel(model);
    }
}
