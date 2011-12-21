/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import core.SystemRegException;
import core.data_tier.entities.Action;
import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public class CreateActionDialog extends AbstractDialog {

    private Action editAction;
    private JButton okBtn, backBtn, descBnt;
    private JTextArea descArea;
    private JTextField nameField, placeField;
    private Calendar startDate, endDate;
    private String name, desc, place;
    private JDateChooser startDateChooser, endDateChooser;
    private JPopupMenu descPopup;

    public CreateActionDialog() {
        super();
        init();
    }

    public CreateActionDialog(Action editAction) {
        this();
        this.editAction = editAction;
        setEditValue();
    }

    @Override
    protected void init() {
        setTitle("Vytvoření nového účastníka");
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();
        nameField = new JTextField();
        placeField = new JTextField();
        descArea = new JTextArea();
        descPopup = new JPopupMenu();
        descPopup.add(descArea);

        okBtn = new JButton(new AbstractAction("Vytvořit") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setValues();
                    if(editAction == null)
                        createAction();
                    else
                        editAction();
                    dispose();
                } catch (SystemRegException ex) {
                    Globals.showErr(CreateActionDialog.this, ex);
                }
            }
        });

        backBtn = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        descBnt = new JButton(new AbstractAction("Přidat popis") {

            @Override
            public void actionPerformed(ActionEvent e) {
                descArea.setPreferredSize(new Dimension(descBnt.getWidth(), 100));
                int x = descBnt.getX();
                int y = descBnt.getY() - descBnt.getHeight();
                descPopup.show(descBnt, x, y);
            }
        });



        JLabel _startDate = new JLabel("Datum zahájení");
        JLabel _endDate = new JLabel("Datum ukončení");
        JLabel _desc = new JLabel("Popis akce");
        JLabel _place = new JLabel("Místo konání");
        JLabel _name = new JLabel("Název akce");
        JPanel lblPanel = new JPanel(new GridLayout(5, 1));
        lblPanel.add(_name);
        lblPanel.add(_place);
        lblPanel.add(_desc);
        lblPanel.add(_startDate);
        lblPanel.add(_endDate);
        JPanel valuesPanel = new JPanel(new GridLayout(5, 1));
        valuesPanel.add(nameField);
        valuesPanel.add(placeField);
        valuesPanel.add(descBnt);
        valuesPanel.add(startDateChooser);
        valuesPanel.add(endDateChooser);
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.add(backBtn);
        btnPanel.add(okBtn);
        setLayout(new BorderLayout(20, 0));
        add(lblPanel, BorderLayout.LINE_START);
        add(valuesPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
        setSize(500, 230);
        setCenterPos();
    }

    private void setEditValue() {
        setTitle("Upravit akci");
        okBtn.setText("Uložit změny");
        descBnt.setText("Upravit popis");
        name = editAction.getName(); nameField.setText(name);
        place = editAction.getPlace(); placeField.setText(place);
        desc = editAction.getPlace(); 
        if(desc != null)
            descArea.append(desc);
        endDate = editAction.getEndDate(); endDateChooser.setCalendar(endDate);
        startDate = editAction.getStartDate(); startDateChooser.setCalendar(startDate);
    }

    private String getDateString(Calendar date) {
        int d = date.get(Calendar.DATE);
        int m = date.get(Calendar.MONTH);
        int r = date.get(Calendar.YEAR);
        Formatter f = new Formatter();
        f.format("%02d.%02d.%d", d, m, r);
        return f.toString();
    }

    private void setValues() throws SystemRegException {
        name = nameField.getText();
        place = placeField.getText();
        desc = descArea.getText();
        startDate = startDateChooser.getCalendar();
        endDate = endDateChooser.getCalendar();
        if(name.isEmpty() || place.isEmpty() || startDate == null || endDate == null){
            throw new SystemRegException("Název, místo konání a obě data "
                    + "jsou povinné parametry!");
        }
        int m = startDate.get(Calendar.MONTH) + 1;
        startDate.set(Calendar.MONTH, m);
        m = endDate.get(Calendar.MONTH) + 1;
        endDate.set(Calendar.MONTH, m);
    }

    private void createAction() throws SystemRegException {
        Action a = Globals.getInstance().getActionOps().createAction(name, place, desc, startDate, endDate);
        User u = Globals.getInstance().getLogedUser();
        Globals.getInstance().getUserOps().addAction(u.getLogin(), a.getID());
        Globals.getInstance().setLogedUser(u);
    }

    private void editAction() throws SystemRegException {
        Globals.getInstance().getActionOps().changeDates(editAction.getID(), startDate, endDate);
        Globals.getInstance().getActionOps().changeParams(editAction.getID(), name, place, desc);
    }
}
