/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.admin;

import core.data_tier.entities.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import presentation.AbstractMainFramePanel;
import presentation.Globals;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class AdminPanel extends AbstractMainFramePanel{
    private MainFrame owner;
    private JSplitPane spliter;
    private JPanel registrators;
    private JPanel viewPanel;
    
    public AdminPanel(){
       setRole("Admin");
       registrators = new JPanel();
       setLayout(new BorderLayout());
       Vector<User> users = new Vector<User>(Globals.getInstance().getUserOps().getUsers());
       JList persons = new JList(users);
       JTextArea details = new JTextArea("Popis userů");
       
       JSplitPane regSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, persons, details);
       regSplit.getLeftComponent().setPreferredSize(new Dimension(200, 500));
       
       viewPanel = new JPanel(new BorderLayout());
       viewPanel.add(new ParticipantTablePanel(getOwner()), BorderLayout.CENTER);
       spliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, regSplit, viewPanel);
       add(spliter, BorderLayout.CENTER);
    }
}
