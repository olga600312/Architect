package view.spantable;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JTable;
import javax.swing.plaf.TableUI;
import javax.swing.table.TableModel;

/**
 * An extension of {@link JTable} that is able to render spanned cells using
 * a custom UI - {@link SpanTableUI}.
 *
 * @author ytoh
 */
public class SpanTable extends JTable {
    private boolean isSpanModel;

    public SpanTable(TableModel model) {
        super(model);
        // the table UI has to be set to <code>SpanTableUI</code>
        super.setUI(new SpanTableUI());
    }

    @Override
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        if (isSpanModel) {
            // if the model is a span model, we have to check if the cell is spanned or not
            // and expand the area of the cell to reflect it
            Rectangle cellRect = super.getCellRect(row, column, includeSpacing);

            for (int i = 1, n = ((SpanModel) getModel()).getRowSpan(row, column); i < n; i++) {
                // expand the area of the visible cell
                cellRect.height += getRowHeight(row + i);
            }

            for (int i = 1, n = ((SpanModel) getModel()).getColumnSpan(row, column); i < n; i++) {
                // expand the area of the visible cell
                cellRect.width += getColumnModel().getColumn(column + i).getWidth();
            }

            return cellRect;
        }

        return super.getCellRect(row, column, includeSpacing);
    }

    @Override
    public void setUI(TableUI ui) { }

    @Override
    public void setModel(TableModel dataModel) {
        isSpanModel = dataModel instanceof SpanModel;
        super.setModel(dataModel);
    }

    @Override
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
        // only needed for correct repaint behavior
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        repaint();
    }

    @Override
    public int columnAtPoint(Point point) {
        if(isSpanModel) {
           int row = super.rowAtPoint(point);
           int column = super.columnAtPoint(point);

           // return the column of the hiding cell
           return ((SpanModel) getModel()).getVisibleCell(row, column).getColumn();
        }

        return super.columnAtPoint(point);
    }

    @Override
    public int rowAtPoint(Point point) {
        if(isSpanModel) {
           int row = super.rowAtPoint(point);
           int column = super.columnAtPoint(point);

           // return the row of the hiding cell
           return ((SpanModel) getModel()).getVisibleCell(row, column).getRow();
        }

        return super.rowAtPoint(point);
    }
}
