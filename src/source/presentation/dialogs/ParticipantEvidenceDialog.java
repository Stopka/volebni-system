/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import core.SystemRegException;
import core.data_tier.entities.Participant;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import presentation.Globals;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public class ParticipantEvidenceDialog extends AbstractDialog {

    private JButton actionBtn, backBtn;
    private boolean presence;
    private Participant p;
    private long actionID;

    public ParticipantEvidenceDialog(Participant par, long actionID) {
        super();
        this.p = par;
        this.actionID = actionID;
        init();
        this.setPreferredSize(new Dimension(500, 330));
        setCenterPos();
    }

    private void presenceAction() {
        String msg = null;
        Calendar c = Calendar.getInstance();
        String desc = ParticipantEvidenceDialog.this.presence ? "Byl zaznamenán odchod uživateli " : "Byl zaznamenán příchod uživateli ";
        try {
            Globals.getInstance().getParticipantOps().changePresence(p.getId(), actionID);
            msg = desc + p.getLogin() + ". Dne: " + c.get(Calendar.DATE)
                    + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR)
                    + " v " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
            JOptionPane.showMessageDialog(getOwner(), msg, "Zaznamenání proběhlo úspěšně", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (SystemRegException ex) {
            Globals.showErr(ParticipantEvidenceDialog.this, ex);
        }
    }

    private void regAction() {
        try {
            Globals.getInstance().getParticipantOps().completeReg(p.getId(), actionID);
            p = Globals.getInstance().getParticipantOps().getParticipant(p.getId());
            JOptionPane.showMessageDialog(getOwner(), "Registrace dokončena, "
                    + "vygenerovány byly tyto údaje:\nLogin: " + p.getLogin()
                    + "\nHeslo: " + p.getPassword(), "Zaznamenání proběhlo úspěšně", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (SystemRegException ex) {
            Globals.showErr(ParticipantEvidenceDialog.this, ex);
        }
    }

    private void setBtn() {
        if (!p.isCompleteReg()) {
            actionBtn.setAction(new AbstractAction("Dokončit registraci") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    regAction();
                }
            });

        } else {
            String title = null;
            if (p.getPresence(actionID).isPresent()) {
                presence = true;
                title = "Zaznamenat odchod";
                actionBtn.setForeground(Color.red);
            } else {
                presence = false;
                title = "Zaznamenat příchod";
                actionBtn.setForeground(Color.blue);
            }
            actionBtn.setAction(new AbstractAction(title) {

                @Override
                public void actionPerformed(ActionEvent e) {
                    presenceAction();
                }
            });
        }
    }

    @Override
    protected void init() {
        Container conPane = getContentPane();
        conPane.setLayout(new GridLayout(3, 1));
        JLabel parDesc = new JLabel("Účastník: " + p.toString(), SwingConstants.CENTER);
        actionBtn = new JButton();
        setBtn();
        actionBtn.setPreferredSize(new Dimension(JButton.WIDTH, JButton.HEIGHT + 30));
        backBtn = new JButton(new AbstractAction("Zpět") {

            @Override
            public void actionPerformed(ActionEvent e) {
                ParticipantEvidenceDialog.this.dispose();
            }
        });

        add(parDesc);
        add(actionBtn);
        add(backBtn);
        pack();
    }
}
