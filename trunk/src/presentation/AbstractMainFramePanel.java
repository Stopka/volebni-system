/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Lahvi
 */
public class AbstractMainFramePanel extends JPanel{
    private MainFrame owner;
    private String role;
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String getRole(){
        return role;
    }
    public void setOwner(MainFrame owner){
        this.owner = owner;
    }
    
    public MainFrame getOwner(){
        return this.owner;
    }
}
