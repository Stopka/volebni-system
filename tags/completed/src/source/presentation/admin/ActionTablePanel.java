/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import core.data_tier.entities.Action;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import presentation.Globals;
import presentation.RefresablePanel;
import presentation.controler.actions.CreateActionAction;
import presentation.controler.actions.DeleteActionAction;
import presentation.controler.actions.EditActionAction;
import presentation.dialogs.CreateActionDialog;

/**
 *
 * @author Lahvi
 */
public class ActionTablePanel extends RefresablePanel implements ListSelectionListener {

    private ActionTableModel model;
    private JTable table;
    private TableRowSorter<ActionTableModel> sorter;
    private JTextField filterField;
    private JComboBox<String> filterBox;
    private JButton createBtn, editBtn, deleteBtn;
    private Action selectedAction;
    private int filterColumn;

    public ActionTablePanel() {
        model = new ActionTableModel();
        table = new JTable(model);
        sorter = new TableRowSorter<ActionTableModel>(model);
        table.setRowSorter(sorter);
        table.getSelectionModel().addListSelectionListener(this);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2) {
                    new CreateActionDialog(selectedAction).setVisible(true);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                int rowindex = table.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createPopupMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        table.getActionMap().put("EnterAction", CreateActionAction.getInstance());
        table.getInputMap().put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                "EnterAction");

        JScrollPane tabPane = new JScrollPane(table);
        //seznam podle čeho se bude filtrovat
        String[] filterModel = {"Název", "Místo"};
        filterBox = new JComboBox<String>(filterModel);
        filterBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = filterBox.getSelectedIndex();
                filterColumn = index;
                switch (index) {
                    case 0:
                        filterColumn = 1;
                        break;
                    case 1:
                        filterColumn = 2;
                        break;
                }
            }
        });
        filterBox.setSelectedIndex(0);
        filterField = new JTextField();
        filterField.getDocument().addDocumentListener(new DocumentListener() {

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
        JLabel _filter = new JLabel("  Filter:");
        JPanel filterPan = new JPanel(new BorderLayout(20, 0));
        filterPan.add(_filter, BorderLayout.LINE_START);
        filterPan.add(filterField, BorderLayout.CENTER);
        filterPan.add(filterBox, BorderLayout.LINE_END);

        createBtn = new JButton(CreateActionAction.getInstance());
        editBtn = new JButton(EditActionAction.getInstance());
        deleteBtn = new JButton(DeleteActionAction.getInstance());
        JPanel btnPanel = new JPanel(new GridLayout(3, 1));
        btnPanel.add(createBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        setLayout(new BorderLayout());
        add(tabPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.LINE_END);
        add(filterPan, BorderLayout.PAGE_END);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selRow = table.getSelectedRow();
        if (selRow > -1 && selRow < model.getRowCount()) {
            int modelRow = table.convertRowIndexToModel(selRow);
            if (modelRow > -1) {
                selectedAction = model.getAction(modelRow);
                Globals.getInstance().setSelectedAction(selectedAction);
                Globals.getInstance().fireStateChange();
                return; 
            }
        }
        selectedAction = null;
        Globals.getInstance().setSelectedAction(null);
        Globals.getInstance().fireStateChange();
    }

    private void newFilter() {
        RowFilter<ActionTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterField.getText(), filterColumn);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    private JPopupMenu createPopupMenu() {
        JMenuItem popitem;
        JPopupMenu popMenu = new JPopupMenu();
        popitem = new JMenuItem(CreateActionAction.getInstance());
        popMenu.add(popitem);
        popitem = new JMenuItem(DeleteActionAction.getInstance());
        popMenu.add(popitem);
        popitem = new JMenuItem(EditActionAction.getInstance());
        popMenu.add(popitem);
        return popMenu;
    }

    @Override
    public void refresh() {
        model.refreshModel();
    }
}
