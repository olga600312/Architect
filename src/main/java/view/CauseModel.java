package view;

import domain.Cause;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 10:27
 */

public class CauseModel extends AbstractTableModel {
    private ArrayList<Cause> data;
    private ArrayList<String> columns;

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columns.size()+1;
    }

    @Override
    public String getColumnName(int column) {
        return column==0?"":columns.get(column-1);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex==0?String.class:Cause.Entry.class;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object o=null;
        if(columnIndex==0){
            o=data.get(rowIndex).getHorizontalLocation().getName();
        }else {
            o = rowIndex >= 0 && rowIndex < data.size() ? data.get(rowIndex).entryAt(columnIndex-1) : null;
        }
        return o;
    }

    public ArrayList<Cause> getData() {
        return data;
    }

    public void setData(ArrayList<String> columns,ArrayList<Cause> data) {
        this.columns = columns;
        this.data = data;
        fireTableStructureChanged();
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

}
