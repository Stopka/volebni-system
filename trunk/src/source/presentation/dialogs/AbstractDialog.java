/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import java.awt.Dimension;

import java.awt.Toolkit;
import javax.swing.JDialog;
import presentation.MainFrame;

/**
 *
 * @author Lahvi
 */
public abstract class AbstractDialog extends JDialog{
    
    protected MainFrame owner;
    
    public AbstractDialog(){
        super(MainFrame.getMainFrame(), true);
        owner = MainFrame.getMainFrame();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    protected void setCenterPos(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        setLocation(x, y);
    }
    
    protected abstract void init();
}
