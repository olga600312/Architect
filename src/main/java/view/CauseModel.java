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
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Cause.Entry.class;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowIndex >= 0 && rowIndex < data.size() ? data.get(rowIndex).entryAt(columnIndex) : null;
    }
}
