/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import core.data_tier.entities.Action;
import core.data_tier.entities.Role;
import core.data_tier.entities.User;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import presentation.controler.actions.CreateUserAction;
import presentation.controler.actions.DeleteUserAction;
import presentation.controler.actions.EditUserAction;
import presentation.dialogs.UserDialog;

/**
 *
 * @author Lahvi
 */
public class UserList extends JList<User> implements ListSelectionListener{
    
    private User u, selectedUser;
    private Role role;
    private Collection<Long> userActions;
    private UserListModel model;
    
    public UserList(){
        super();
        u = Globals.getInstance().getLogedUser();
        role = u.getRole();
        userActions = u.getActions();
        model = new UserListModel(role);
        setModel(model);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    doubleClickAction();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int r = locationToIndex(e.getPoint());
                if (r >= 0 && r < getModel().getSize()) {
                    setSelectionInterval(r, r);
                } else {
                    clearSelection();
                }

                int index = getSelectedIndex();
                if (index < 0) {
                    return;
                }
                if (e.isPopupTrigger()) {
                    JPopupMenu popup = createPopup();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        
        });
        addListSelectionListener(this);
        setSelectedIndex(0);
        //selectedUser = getSelectedValue();
    }
    private void doubleClickAction(){
        if(selectedUser != null)
        new UserDialog(selectedUser).setVisible(true);
        Globals.getInstance().refreshData();
    }
    
    private JPopupMenu createPopup(){
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem popItem;
        popItem = new JMenuItem(CreateUserAction.getInstance());
        popupMenu.add(popItem);
        popItem = new JMenuItem(EditUserAction.getInstance());
        popupMenu.add(popItem);
        popItem = new JMenuItem(DeleteUserAction.getInstance());
        popupMenu.add(popItem);
        return popupMenu;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = getSelectedIndex();
        if(selectedIndex < 0 || selectedIndex > model.getSize()){
            Globals.getInstance().setSelectedUser(null);
        } else {
            selectedUser = getSelectedValue();
            Globals.getInstance().setSelectedUser(selectedUser);
        }
    }
    
    public void refresh(){
        model.refreshModel();
    }
    class UserListModel extends AbstractListModel<User>{

        private Role role;
        private List<User> modelValues;
        
        public UserListModel(Role role){
            this.role = role; 
            modelValues = new ArrayList<User>(Globals.getInstance().getUserOps().getUsers(role));
        }
        
        
        
        @Override
        public int getSize() {
            return modelValues.size();
        }

        @Override
        public User getElementAt(int index) {
            return modelValues.get(index);
        }
        
        public void refreshModel(){
            this.modelValues = new ArrayList<User>(Globals.getInstance().getUserOps().getUsers(role));
            
            fireContentsChanged(this, 0, modelValues.size());
        }
        
        public void refreshModel(Role role){
            this.role = role;
            refreshModel();
        }
        
        public void addUser(User u){
            modelValues.add(u);
            fireIntervalAdded(this, 0, modelValues.size() - 1);
        }
        
        public void removeUser(int index){
            modelValues.remove(index);
            fireIntervalRemoved(this, index, index);
        }
        public Collection<User> getUsers(){
            return modelValues;
        }
    }
}
