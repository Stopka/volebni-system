/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.registrator;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.*;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public class PresenceDialog extends JDialog {
    
    private JPanel mainPanel;
    private JLabel _login, _find, _status, status, details;
    private JButton actionBtn, findBtn, backBtn, resetBtn;
    private JTextField login;
    private Participant p;
    private boolean stav;
    
    public PresenceDialog(JFrame owner) {
        super(owner, "Evidence presnece", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container conPane = getContentPane();
        conPane.setLayout(new BorderLayout());
        mainPanel = new JPanel(new GridLayout(4, 2));
        
        _login = new JLabel("Login účastníka:");
        _find = new JLabel("Nalezen účastník:");
        _status = new JLabel("Status:");
        status = new JLabel("");
        details = new JLabel("");
        
        login = new JTextField();
        
        
        actionBtn = new JButton(new AbstractAction("Příchod / Odchod") {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                String desc = null;
                Calendar c = Calendar.getInstance();
                try {
                    if (stav) {
                        Globals.getInstance().getParticipantOps().addDeparture(p.getLogin());
                        desc = "Byl zaznamenán odchod uživateli " + p.getLogin() + ". Dne: " + c.get(Calendar.DATE)
                                + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR)
                                + " v " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    } else {
                        Globals.getInstance().getParticipantOps().addArrival(p.getLogin());
                        desc = "Byl zaznamenán příchod uživateli " + p.getLogin() + ". Dne: " + c.get(Calendar.DATE)
                                + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR)
                                + " v " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    }
                    JOptionPane.showMessageDialog(getOwner(), desc, "Zaznamenání proběhlo úspěšně", JOptionPane.INFORMATION_MESSAGE);
                } catch (SystemRegException ex) {
                    Globals.getInstance().showErr(PresenceDialog.this, ex);
                }
            }
        });
        
        
        
        actionBtn.setPreferredSize(new Dimension(500, 80));
        findBtn = new JButton(new AbstractAction("Vyhledat") {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
        
        backBtn = new JButton(new AbstractAction("Zpět") {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                PresenceDialog.this.dispose();
            }
        });
        
        mainPanel.add(_login);
        mainPanel.add(login);
        mainPanel.add(_find);
        mainPanel.add(details);
        mainPanel.add(_status);
        mainPanel.add(status);
        mainPanel.add(findBtn);
        mainPanel.add(backBtn);
        actionBtn.setBackground(Color.LIGHT_GRAY);
        actionBtn.setForeground(Color.BLACK);
        actionBtn.setEnabled(false);
        getRootPane().setDefaultButton(findBtn);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(actionBtn, BorderLayout.PAGE_END);
        pack();
        this.setPreferredSize(new Dimension(500, 330));
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Determine the new location of the window
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        // Move the window
        setLocation(x, y);
        
    }
    
    public void find() {
        try {
            details.setForeground(Color.black);
            String log = login.getText();
            p = Globals.getInstance().getParticipantOps().getParticipant(log);
            details.setText(p.toString());
            status.setText(p.getPresence().isPresent() ? "Přítomen" : "Nepřítomen");
            actionBtn.setEnabled(true);
            if (p.getPresence().isPresent()) {
                stav = true;
                actionBtn.setText("Zaznamenat Odchod");
                actionBtn.setBackground(Color.red);
            } else {
                stav = false;
                actionBtn.setText("Zaznamenat Příchod");
                actionBtn.setBackground(Color.blue);
                actionBtn.setForeground(Color.GRAY);
            }
            getRootPane().setDefaultButton(actionBtn);
        } catch (Exception ex) {
            details.setText("ÚČASTNÍK NENALEZEN!");
            details.setForeground(Color.red);
        }
    }
    
    public void reset() {
        login.setText("");
        status.setText("");
        details.setText("");
        actionBtn.setText("Příchod / Odchod");
        actionBtn.setBackground(Color.LIGHT_GRAY);
        actionBtn.setForeground(Color.BLACK);
        actionBtn.setEnabled(false);
        getRootPane().setDefaultButton(findBtn);
    }
}
