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
import presentation.controler.actions.LogoutAction;

/**
 *
 * @author Lahvi
 */
public class RegPanel extends AbstractMainFramePanel{
    private JButton logout, presenceBtn, completeReg;
    
    public RegPanel(){
        setLayout(new BorderLayout());
        setRole("Registrátor");
        logout = new JButton(new LogoutAction());
        logout.setBackground(Color.red);
        //logout.setForeground(Color.yellow);
        logout.setFont(new Font("Courier New",Font.BOLD, 35));
        logout.setPreferredSize(new Dimension(900, 150));
        JPanel buttonPanel = new JPanel(new GridLayout());
        presenceBtn = new JButton(new AbstractAction("Evidence přítomnosti") {

            @Override
            public void actionPerformed(ActionEvent e) {
                new PresenceDialog(RegPanel.this.getOwner()).setVisible(true);
            }
        });
        presenceBtn.setPreferredSize(new Dimension(450, 700));
        
        completeReg = new JButton(new AbstractAction("Dokončit Registraci") {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CompleteRegDialog(getOwner()).setVisible(true);
            }
            
        });
        buttonPanel.add(completeReg);
        buttonPanel.add(presenceBtn);
        
        completeReg.setPreferredSize(new Dimension(450, 700));
        
        add(logout, BorderLayout.PAGE_END);
        /*add(completeReg, BorderLayout.LINE_START);
        add(presenceBtn, BorderLayout.LINE_END);*/
        add(buttonPanel, BorderLayout.CENTER);
        //buttonPanel.getRootPane().setDefaultButton(null);
    }
}
