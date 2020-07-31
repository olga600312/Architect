        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.spantable;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.apache.commons.lang.Validate;

/**
 * Convenience class for forwarding method calls to an underlying {@link TableModel}
 * instance.
 * 
 * @author ytoh
 */
public class ForwardingTableModel implements TableModel {
    private TableModel delegate;

    /**
     * Constructs a new <code>ForwardingTableModel</code> backed by the supplied
     * <code>model</code>.
     *
     * @param model to be forwarded to
     * @throws NullPointerException if the supplied model is <code>null</code>.
     */
    public ForwardingTableModel(TableModel model) {
        Validate.notNull(model, "model cannot be null");
        
        this.delegate = model;
    }

    public int getRowCount() { return delegate.getRowCount(); }

    public int getColumnCount() { return delegate.getColumnCount(); }

    public String getColumnName(int columnIndex) { return delegate.getColumnName(columnIndex); }

    public Class<?> getColumnClass(int columnIndex) { return delegate.getColumnClass(columnIndex); }

    public boolean isCellEditable(int rowIndex, int columnIndex) { return delegate.isCellEditable(rowIndex, columnIndex); }

    public Object getValueAt(int rowIndex, int columnIndex) { return delegate.getValueAt(rowIndex, columnIndex); }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { delegate.setValueAt(aValue, rowIndex, columnIndex); }

    public void addTableModelListener(TableModelListener l) { delegate.addTableModelListener(l); }

    public void removeTableModelListener(TableModelListener l) { delegate.removeTableModelListener(l); }
}
