package presentation;

import core.data_tier.entities.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import presentation.Globals;

/**
 * Třída reprezentující model tabulky ve třídě {@link presentation.admin.ParticipantTablePanel}.
 * Model je odvozen od abstraktní třídy {@link javax.swing.table.AbstractTableModel}.
 * Tabulka používající tento model bude mít 5 sloupců, se jménem, loginem, emailem,
 * číslem OP a přítomností daného účastníka.
 * @author Lahvi
 */
public class ParticipantTableModel extends AbstractTableModel{

    private List<Participant> pars;
    private long actionID;
    
    public ParticipantTableModel(long actionID){
        this.actionID = actionID;
        pars = new ArrayList<Participant>(Globals.getInstance().getParticipantOps().getAllParticipants(actionID));
    }
    
    /**
     * Vrací počet řádků v dané tabulce.
     * @return počet řádků tabulky.
     */
    @Override
    public int getRowCount() {
        return pars.size();
    }

    /**
     * Vrací počet sloupců tabulky, používající tento model. 
     * @return Počet sloupců = 5.
     */
    @Override
    public int getColumnCount() {
        return 7;
    }

    /**
     * Vrátí hodnotu na daném řádku {@code int rowIndex} a daném sloupci 
     * {@code int columnIndex}. Pokud bude sloupec 0 tak se vrátí String s loginem
     * účastníka na daném řádku, pokud 1 tak String se jménem, pokud 2 tak String
     * s emailem, pokud 3 tak int s číslem OP a pokud 4 tak boolean s přítomností 
     * daného účastníka.
     * @param rowIndex číslo daného řádku.
     * @param columnIndex číslo daného slouce.
     * @return Hodnota na daném řádku a slouci.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < 0 || rowIndex > pars.size()-1) return null;
        Participant p = pars.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                //pokud není dokončená registrace
                if(p.getLogin() == null)
                    return "Nedokončená registrace";
                else
                    return  p.getLogin();
            case 2:
                return p.getFirstName();
            case 3:
                return p.getLastName();
            case 4:
                return p.getEmail();
            case 5:
                return p.getCardID();
            case 6:
                return p.getPresence(actionID).isPresent();
        }
        return null;
    }

    /**
     * Vrací nadpis sloupce daném indexem {@code int column}. Pro 0 to bude 
     * {@code "Login"}, pro 1 {@code "Jméno"}, pro 2 {@code "Email"}, pro 3
     * {@code "Číslo OP"} a pro 4 {@code "Přítomen"}.
     * @param column číslo požadovaného slopce.
     * @return Nadpis daného slouce.
     */
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Login";
            case 2:
                return "Křestní";
            case 3:
                return "Příjmení";
            case 4:
                return "Email";
            case 5:
                return "Číslo OP";
            case 6:
                return "Přítomen";
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

    /**
     * Metoda vrátí aktuální list s účastníky.
     * @return List s účastníky, který je modelem používán.
     */
    public List<Participant> getParticipants() {
        return pars;
    }
    /**
     * Metoda nastaví nový list účastníků. Tento list bude inicalizován kolekcí
     * {@code Collectiin<Participant> pars}. Poté bude list seřazen podle jmen
     * účastníků a nakonec bude překreslena tabulka, používající tento model.
     * @param pars Kolekce s novým seznamem účastníků.
     * 
     * @see Participant
     * @see java.util.Collection
     * @see AbstractTableModel#fireTableDataChanged() 
     */
    public void setParticipants(Collection<Participant> pars) {
        this.pars = new ArrayList(pars);
        //seřazení podle jména
        Collections.<Participant>sort(this.pars, new Comparator<Participant>() {

            @Override
            public int compare(Participant o1, Participant o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        //aktualizování tabulky
        fireTableDataChanged();
    }

    /**
     * Metoda vrátí účastníka z listu modelu.
     * @param index Index v listu, na kterém se má požadovaný účastník nacházet.
     * @return Účastník z indexu {@code index}.
     */
    public Participant getParticipant(int index) {
        if(index < 0 || index > pars.size() - 1)
            return null;
        return pars.get(index);
    }
    /**
     * Metoda která znovu nainicializuje list účastníků v modelu.  
     */
    public void refresh(){
        actionID = Globals.getInstance().getSelectedActionID();
        Collection<Participant> parts = Globals.getInstance().getParticipantOps().getAllParticipants(actionID);
        setParticipants(parts);
    }
    
    
    
}
