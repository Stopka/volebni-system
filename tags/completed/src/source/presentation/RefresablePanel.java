/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author Lahvi
 */
public abstract class RefresablePanel extends JPanel{
    
    public RefresablePanel(LayoutManager layout){
        super(layout);
    }
    public RefresablePanel(){
        super();
    }
    public abstract void refresh();
}
