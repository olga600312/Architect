package view;

import domain.Cause;
import domain.LocationType;
import lombok.AllArgsConstructor;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CauseFooterModel extends AbstractTableModel {


    private CauseModel model;
    private ArrayList<LocationType> types = new ArrayList<>();
    private Map<Integer, Span> spans = new TreeMap<>();

    public CauseFooterModel(CauseModel model) {
        this.model = model;
        ArrayList<Cause.Entry> data = model.causeAtRow(0).getData();
        for (int i = 0; i < data.size(); i++) {
            LocationType type = data.get(i).getColumnHeader().getType();
           //
            if (!types.contains(type)) {
                types.add(type);
            }

        }
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return types.size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return columnIndex == 0 ? "" : types.get(columnIndex - 1);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? String.class : LocationType.class;
    }

@AllArgsConstructor
    private class Span {
        int from;
        int to;
    }
}
