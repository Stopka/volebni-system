/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import core.SystemRegException;
import core.data_tier.entities.Action;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import presentation.Globals;
import sun.awt.HorizBagLayout;

/**
 *
 * @author Lahvi
 */
public class ChooseActionDialog extends AbstractDialog implements ListSelectionListener {

    private Collection<Long> allActions;
    private Collection<Long> choosedActions;
    private JList<String> allList, chooseList;
    private ActionListModel allModel, chooseModel;
    private JButton okBtn, backBtn, addBtn, removeBtn;

    public ChooseActionDialog(Collection<Long> allActions, Collection<Long> choosedActions) {
        this.allActions = new ArrayList<Long>(allActions);
        this.choosedActions = choosedActions;
        init();
    }

    @Override
    protected void init() {
        setTitle("Vyberte akce do kterých bude účastník přiřazen.");
        setLayout(new BorderLayout());
        allModel = new ActionListModel(allActions);
        chooseModel = new ActionListModel(choosedActions);

        allList = new JList<String>(allModel);
        chooseList = new JList<String>(chooseModel);

        allList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chooseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allList.getSelectionModel().addListSelectionListener(this);
        chooseList.getSelectionModel().addListSelectionListener(this);
        addBtn = new JButton(new AbstractAction(">") {

            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
            }
        });
        addBtn.setEnabled(false);

        removeBtn = new JButton(new AbstractAction("<") {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeAction();
            }
        });
        removeBtn.setEnabled(false);
        backBtn = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        okBtn = new JButton(new AbstractAction("OK") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                if(chooseModel.getActions().isEmpty())
                    throw new SystemRegException("Účastník musí mít vybranou alespoň jednu akci!");
                choosedActions.removeAll(choosedActions);
                choosedActions.addAll(chooseModel.getActions());
                dispose();
                } catch (SystemRegException ex){
                    chooseModel = new ActionListModel(choosedActions);
                    chooseList.setModel(chooseModel);
                    Globals.showErr(owner, ex);
                }
            }
        });

        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rPanel = new JPanel(new BorderLayout());
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.PAGE_AXIS));
        midPanel.add(addBtn);
        midPanel.add(removeBtn);
        JLabel llab = new JLabel("Dostupné akce");
        JLabel plab = new JLabel("Vybrané akce");

        leftPanel.add(llab, BorderLayout.PAGE_START);
        leftPanel.add(allList, BorderLayout.CENTER);
        rPanel.add(plab, BorderLayout.PAGE_START);
        rPanel.add(chooseList, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.add(backBtn);
        btnPanel.add(okBtn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        mainPanel.add(leftPanel);
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(midPanel);
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(rPanel);
        mainPanel.add(Box.createHorizontalGlue());
        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
        setSize(440, 260);
        setCenterPos();
    }

    private void addAction() {
        int selectedIndex = allList.getSelectedIndex();
        long actAct = allModel.getAction(selectedIndex);
        allModel.removeAction(selectedIndex);
        chooseModel.addAction(actAct);
    }

    private void removeAction() {
        int selectedIndex = chooseList.getSelectedIndex();
        long actAct = chooseModel.getAction(selectedIndex);
        chooseModel.removeAction(selectedIndex);
        allModel.addAction(actAct);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (chooseList.getSelectedIndex() < 0) {
            removeBtn.setEnabled(false);
        } else {
            removeBtn.setEnabled(true);
        }
        if (allList.getSelectedIndex() < 0) {
            addBtn.setEnabled(false);
        } else {
            addBtn.setEnabled(true);
        }
    }

    class ActionListModel extends AbstractListModel<String> {

        private List<Long> actions;

        public ActionListModel(Collection<Long> actions) {
            this.actions = new ArrayList<Long>(actions);
        }

        @Override
        public int getSize() {
            return actions.size();
        }

        @Override
        public String getElementAt(int index) {
            try {
                long id = actions.get(index);
                Action a = Globals.getInstance().getActionOps().getAction(id);
                return a.getName();
            } catch (SystemRegException ex) {
                Globals.showErr(ChooseActionDialog.this, ex);
                return null;
            }
        }

        void addAction(long a) {
            actions.add(a);
            fireIntervalAdded(this, 0, actions.size() - 1);
        }

        long removeAction(int index) {
            long r = actions.remove(index);
            fireIntervalRemoved(this, index, index);
            return r;
        }

        Collection<Long> getActions() {
            return actions;
        }

        long getAction(int index) {
            return actions.get(index);
        }
        
        
    }
}
