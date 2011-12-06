/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businesstier.ParticipantFacade;

import core.data_tier.entities.Action;
import core.data_tier.entities.Participant;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;


/**
 * Panel s tabulkou a všemi základními operacemi nad tabulkou. Kdo bude tento
 * panel implementovat tak musí určit vlastní metody pro double click a pro 
 * right click.
 * @author Lahvi
 */
public abstract class ParticipantTablePanel extends RefresablePanel implements ListSelectionListener {

    protected JTable parTable;
    protected ParticipantTableModel model;
    private JButton allButton, nonPresentBtn, presentBtn, nonRegBtn;
    private JComboBox actionsBox;
    private JTextField filter;
    private TableRowSorter<ParticipantTableModel> sorter;
    private int filterComlumn;
    private JComboBox<String> filterBox;
    private long selectedID;

    public ParticipantTablePanel() {
        super(new BorderLayout());
        Collection<Long> ids = Globals.getInstance().getLogedUser().getActions();
        List<Action> availableActions = new ArrayList<Action>(Globals.getInstance().getActionOps().getActions(ids));
        
        selectedID = availableActions.get(0).getID();
        Globals.getInstance().setSelectedActionID(selectedID);
        model = new ParticipantTableModel(selectedID);
        parTable = new JTable(model);
        sorter = new TableRowSorter<ParticipantTableModel>(model);
        parTable.setRowSorter(sorter);
        parTable.getSelectionModel().addListSelectionListener(this);
        JPanel tablePan = new JPanel(new BorderLayout());
        tablePan.add(parTable.getTableHeader(), BorderLayout.PAGE_START);
        tablePan.add(parTable, BorderLayout.CENTER);

        allButton = new JButton(new AbstractAction("Zobrazit vše") {

            @Override
            public void actionPerformed(ActionEvent e) {
                filter.setText("");
                ParticipantFacade pOps = Globals.getInstance().getParticipantOps();
                model.setParticipants(pOps.getAllParticipants(selectedID));
            }
        });

        nonPresentBtn = new JButton(new AbstractAction("Zobrazit nepřítomné") {

            @Override
            public void actionPerformed(ActionEvent e) {
                filter.setText("");
                 ParticipantFacade pOps = Globals.getInstance().getParticipantOps();
                model.setParticipants(pOps.getAbsent(selectedID));
            }
        });

        presentBtn = new JButton(new AbstractAction("Zobrazit přítomné") {

            @Override
            public void actionPerformed(ActionEvent e) {
                filter.setText("");
                ParticipantFacade pOps = Globals.getInstance().getParticipantOps();
                model.setParticipants(pOps.getPresent(selectedID));
            }
        });

        nonRegBtn = new JButton(new AbstractAction("Zobrazit nezaregistrované") {

            @Override
            public void actionPerformed(ActionEvent e) {
                filter.setText("");
                ParticipantFacade pOps = Globals.getInstance().getParticipantOps();
                model.setParticipants(pOps.getIncompleteRegParticipants(selectedID));
            }
        });
        //nastavení akce pro double click
        parTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    doubleClickAction();
                }
            }
        });
        //nastavení akce pro stisk enteru
        javax.swing.Action action = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                doubleClickAction();
            }
        };
        parTable.getActionMap().put("EnterAction", action);
        parTable.getInputMap().put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                "EnterAction");

        //seznam podle čeho se bude filtrovat
        String[] filterModel = {"Login", "Příjmení", "Email", "ID"};
        filterBox = new JComboBox<String>(filterModel);
        filterBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = filterBox.getSelectedIndex();
                filterComlumn = index;
                switch(index){
                    case 0:
                        filterComlumn = 1;
                        break;
                    case 1:
                        filterComlumn = 3;
                        break;
                    case 2:
                        filterComlumn = 4;
                        break;
                    case 3:
                        filterComlumn= 5;
                        break;
                }
            }
        });
        filterBox.setSelectedIndex(1);
        filterBox.setPreferredSize(
                new Dimension(150, JComboBox.WIDTH));
        filter = new JTextField();
        
        //okamžitá reakce na jakoukoliv změnu obsahu filtru
        filter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }
        });
        //ComboBox pro výběr akce, pro kterou se budou účastníci zobrazovat.
        actionsBox = new JComboBox(new Vector<Action>(availableActions));
        actionsBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionsBox.getSelectedIndex() >= 0){
                    selectedID = ((Action)actionsBox.getSelectedItem()).getID();   
                    Globals.getInstance().setSelectedActionID(selectedID);
                }
            }
        });
        

        JLabel _filter = new JLabel("  Filter:");
        JPanel filterPan = new JPanel(new BorderLayout(20, 0));
        filterPan.add(_filter, BorderLayout.LINE_START);
        filterPan.add(filter, BorderLayout.CENTER);
        filterPan.add(filterBox, BorderLayout.LINE_END);
        tablePan.add(filterPan, BorderLayout.PAGE_END);

        JPanel btnPanel = new JPanel(new GridLayout());
        btnPanel.add(allButton);
        btnPanel.add(presentBtn);
        btnPanel.add(nonPresentBtn);
        btnPanel.add(nonRegBtn);

        add(actionsBox, BorderLayout.PAGE_START);
        add(tablePan, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
    }

    protected abstract void doubleClickAction();

    /**
     * 
     */
    private void newFilter() {
        RowFilter<ParticipantTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filter.getText(), filterComlumn);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    /**
     * Metoda z {@link RefresablePanel}. Refreshuje model tabulky.
     */
    @Override
    public void refresh() {
        model.refresh();
    }

    /**
     * Vrátí vybraného účastníka.
     * @return 
     */
    public Participant getSelectedParticipant() {
        int tabRow = parTable.getSelectedRow();
        if (tabRow != -1) {
            int modelIndex = parTable.convertRowIndexToModel(tabRow);
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
}
