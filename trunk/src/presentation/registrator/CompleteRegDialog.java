/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.registrator;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import presentation.Globals;

/**
 *
 * @author Lahvi
 */
public class CompleteRegDialog extends JDialog {

    private JPanel mainPanel;
    private JLabel _personalID, _foundedPar, _status, status, details;
    private JButton actionBtn, findBtn, backBtn;
    private JTextField personalID;
    private Participant p;
    private Globals main;

    public CompleteRegDialog(JFrame owner) {
        super(owner, "Dokončení registace účastníka", true);
        main = Globals.getInstance();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container conPane = getContentPane();
        conPane.setLayout(new BorderLayout());
        mainPanel = new JPanel(new GridLayout(4, 2));

        _personalID = new JLabel("Číslo občanky:");
        _foundedPar = new JLabel("Nalezen účastník:");
        _status = new JLabel("Status:");
        status = new JLabel("");
        details = new JLabel("");

        personalID = new JTextField();

        actionBtn = new JButton();
        actionBtn.setAction(new AbstractAction("Dokončit registraci") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    reset();
                    main.getParticipantOps().completeReg(p.getpersonalCardID());
                    p = main.getParticipantOps().getParticipant(p.getpersonalCardID());
                    JOptionPane.showMessageDialog(getOwner(), "Registrace dokončena, "
                            + "vygenerovány byly tyto údaje:\nLogin: " + p.getLogin() 
                            +"\nHeslo: " + p.getPassword(), "Zaznamenání proběhlo úspěšně", JOptionPane.INFORMATION_MESSAGE);
                } catch (SystemRegException ex) {
                    
                }

            }
        });
        actionBtn.setEnabled(false);

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
                CompleteRegDialog.this.dispose();
            }
        });

        mainPanel.add(_personalID);
        mainPanel.add(personalID);
        mainPanel.add(_foundedPar);
        mainPanel.add(details);
        mainPanel.add(_status);
        mainPanel.add(status);
        mainPanel.add(findBtn);
        mainPanel.add(backBtn);
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
            int idnum = Integer.parseInt(personalID.getText());
            p = main.getParticipantOps().getParticipant(idnum);
            if(p.isCompleteReg()){
                details.setText("ÚČASTNÍK JIŽ MÁ REGISTRACI DOKONČENOU! Login: " + p.getLogin());
                details.setForeground(Color.red);
            } else {
                details.setText(p.toString());
                actionBtn.setEnabled(true);
            }
            
            getRootPane().setDefaultButton(actionBtn);
        } catch (NumberFormatException ex) {
            main.showErr(CompleteRegDialog.this, ex);
        } catch (SystemRegException ex) {
            details.setText("ÚČASTNÍK NENALEZEN!");
            details.setForeground(Color.red);
        }
    }

    public void reset() {
        personalID.setText("");
        status.setText("");
        details.setText("");
        actionBtn.setEnabled(false);
        getRootPane().setDefaultButton(findBtn);
    }
}
