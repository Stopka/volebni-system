/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import presentation.RefresablePanel;
import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import presentation.AbstractMainFramePanel;
import presentation.Globals;
import presentation.MainFrame;
import presentation.UserList;

/**
 *
 * @author Lahvi
 */
public class AdminPanel extends AbstractMainFramePanel {

    private MainFrame owner;
    private JSplitPane spliter;
    private JPanel viewPanel;
    private RefresablePanel content;
    private UserList userList;
    private boolean actions;

    public AdminPanel() {
        setRole("Admin");
        setLayout(new BorderLayout());
        userList = new UserList();
        JScrollPane userListPane = new JScrollPane(userList);
        content = new AdminParticipantsPanel();
        viewPanel = new JPanel(new BorderLayout());
        viewPanel.add(content, BorderLayout.CENTER);
        spliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userListPane, viewPanel);
        add(spliter, BorderLayout.CENTER);
        actions = false;
    }

    @Override
    public void refresh() {
        userList.refresh();
        content.refresh();
    }

    @Override
    public void changePanel() {
        spliter.remove(viewPanel);
        viewPanel = new JPanel(new BorderLayout());
        if (actions) {
            content = new AdminParticipantsPanel();
            viewPanel.add(content, BorderLayout.CENTER);
            actions = false;
        } else {
            content = new ActionTablePanel();
            viewPanel.add(content, BorderLayout.CENTER);
            actions = true;
        }
        spliter.setRightComponent(viewPanel);
        spliter.revalidate();
        revalidate();
    }
}
