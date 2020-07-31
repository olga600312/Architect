package view;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;

public class CauseFooterColumnModel  extends DefaultTableColumnModel  implements TableColumnModelListener {
    public CauseFooterColumnModel(TableColumnModel model) {
        super();
        model.addColumnModelListener(this);
    }

    @Override
    public void columnAdded(TableColumnModelEvent e) {

    }

    @Override
    public void columnRemoved(TableColumnModelEvent e) {

    }

    @Override
    public void columnMoved(TableColumnModelEvent e) {

    }

    @Override
    public void columnMarginChanged(ChangeEvent e) {

    }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {

    }

    private void recalculate(){
       //tableColumns
    }
}
