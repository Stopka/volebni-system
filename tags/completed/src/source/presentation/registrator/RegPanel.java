/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.registrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import presentation.AbstractMainFramePanel;
import presentation.LoginScreen;
import presentation.MainFrame;
import presentation.ParticipantTablePanel;
import presentation.admin.AdminParticipantsPanel;
import presentation.controler.actions.LogoutAction;

/**
 *
 * @author Lahvi
 */
public class RegPanel extends AbstractMainFramePanel{
    private JButton logout;
    private ParticipantTablePanel panel;
    public RegPanel(){
        setLayout(new BorderLayout());
        setRole("Registrátor");
        logout = new JButton(new LogoutAction());
        logout.setBackground(Color.red);
       
        logout.setFont(new Font("Courier New",Font.BOLD, 35));
        logout.setPreferredSize(new Dimension(logout.getSize().width, logout.getSize().height + 80));
        
        panel = new RegParticipantTable();
        
        add(panel);
        add(logout, BorderLayout.PAGE_END);
    }

    @Override
    public void refresh() {
        panel.refresh();
    }

    @Override
    public void changePanel() {
        System.out.println("Není podoporováno");
    }
}
