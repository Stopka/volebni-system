/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.table.AbstractTableModel;
import core.data_tier.entities.Additional;

/**
 *
 * @author Lahvi
 */
public class AddtionalModel extends AbstractTableModel{
    
    private List<String> keys, values;
    
    public AddtionalModel(Additional a){
        keys = new ArrayList<String>();
        values = new ArrayList<String>();
        if(a == null) return;
        Map<String, String> additionals = a.getAdditionalParams();
        if(additionals.size() <= 0) return;
        values.addAll(additionals.values());
        keys.addAll(additionals.keySet());
    }
    
    @Override
    public int getRowCount() {
        return keys.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return keys.get(rowIndex);
        } else {
            return values.get(rowIndex);
        }
    }
    
    @Override
    public String getColumnName(int column) {
        if(column == 0){
            return "Název";
        }
        return "Hodnota";
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String eValue = (String)aValue;
        if(columnIndex == 0){
           keys.remove(rowIndex);
           keys.add(rowIndex, eValue);
        } else {
            values.remove(rowIndex);
            values.add(rowIndex, eValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public void addInfo(String key, String value){
        keys.add(key);
        values.add(value);
        fireTableDataChanged();
    }
    
    public Map<String, String> getAdditionalInfo(){
        Map<String, String> adInfo = new TreeMap<String, String>();
        for (int i = 0; i < keys.size(); i++){
            adInfo.put(keys.get(i), values.get(i));
        }
        return adInfo;
    }
    
    public void removeRow(int row){
        String key = (String)getValueAt(row, 0);
        String value = (String)getValueAt(row, 1);
        keys.remove(key);
        values.remove(value);
        fireTableDataChanged();
    }
    
}
