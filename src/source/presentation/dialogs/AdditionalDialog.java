/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import core.data_tier.entities.Additional;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Dialog pro vytváření a upravování volitelných parametrů.
 * @author Lahvi
 */
public class AdditionalDialog extends AbstractDialog implements ListSelectionListener{

    private JTable table;
    private AddtionalModel model;
    private JButton addBtn, okBtn, backBtn;
    private JTextField keyField, valField;
    private Additional additionals;
    private boolean edit;

    public AdditionalDialog(Additional additionals) {
        super();
        setTitle("Přidání nepoviných informací");
        model = new AddtionalModel(additionals);
        this.additionals = additionals;
        init();
        edit = false;
    }

    @Override
    protected void init() {
        setLayout(new BorderLayout());
        keyField = new JTextField();
        valField = new JTextField();
        
        table = new JTable(model);
        JScrollPane tabPane = new JScrollPane(table);
        table.addMouseListener(new MouseAdapter() {

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
        table.getSelectionModel().addListSelectionListener(this);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        addBtn = new JButton(new AbstractAction("Přidat informaci") {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(edit){
                    
                }
                adInfoAction();
            }
        });
        backBtn = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okBtn = new JButton(new AbstractAction("OK") {

            @Override
            public void actionPerformed(ActionEvent e) {
                okAction();
                dispose();
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.add(backBtn);
        btnPanel.add(okBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel fieldPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        fieldPanel.add(keyField);
        fieldPanel.add(valField);

        
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.add(tabPane, BorderLayout.CENTER);
        tabPanel.add(fieldPanel, BorderLayout.PAGE_END);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabPanel, BorderLayout.CENTER);
        mainPanel.add(addBtn, BorderLayout.PAGE_END);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
        setSize(500, 250);
        setCenterPos();
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(edit){
            reset();
            edit = false;
        }
    }
    
    private void adInfoAction() {
        String key = keyField.getText();
        String value = valField.getText();
        if (key != null && !key.equals("") && value != null && !value.equals("")) {
            if(edit){
                int row = table.getSelectedRow();
                model.setValueAt(key, row, 0);
                model.setValueAt(value, row, 1);
                edit = false;
                addBtn.setText("Přidat informaci");
            }else{
                model.addInfo(key, value);
            }
        }
        reset();
    }

    private void okAction() {
        additionals.removeAll();
        additionals.setAdditionalParams(model.getAdditionalInfo());
    }

    private void reset() {
        keyField.setText("");
        valField.setText("");
    }
    
    private JPopupMenu createPopupMenu() {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem(new AbstractAction("Odstranit informaci") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                model.removeRow(row);
                if(edit){
                    edit = false;
                    addBtn.setText("Přidat informaci"); 
                }
            }
        });
        popup.add(menuItem);
        menuItem = new JMenuItem(new AbstractAction("Editovat informaci") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                edit = true;
                keyField.setText((String)model.getValueAt(row, 0));
                valField.setText((String)model.getValueAt(row, 1));
                addBtn.setText("Uložit změněnou informaci");
            }
        });
        popup.add(menuItem);
        return popup;   
    }

}

