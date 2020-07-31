package view.spantable;

import java.util.HashMap;
import java.util.Map;
import javax.swing.table.TableModel;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.Validate;

/**
 * A simple implementation of the {@link SpanModel} interface that wraps around
 * a {@link TableModel}.
 *
 * @author ytoh
 */
public class DefaultSpanModel extends ForwardingTableModel implements SpanModel {

    // decorated model
    private Map<Integer, Map<Integer, Integer>> rowSpans;
    private Map<Integer, Map<Integer, Integer>> columnSpans;
    private Map<Integer, Map<Integer, Cell>>    hiddenCells;

    /**
     * Constructs a new <code>DefaultSpanModel</code> backed by the supplied
     * <code>model</code>.
     *
     * @param model to be extended
     */
    public DefaultSpanModel(TableModel model) {
        super(model);

        Factory hashMapFactory = new Factory() {
            public Map<Integer, Integer> create() {
                return new HashMap<Integer, Integer>();
            }
        };

        rowSpans = MapUtils.lazyMap(new HashMap<Integer, Map<Integer, Integer>>(), hashMapFactory);
        columnSpans = MapUtils.lazyMap(new HashMap<Integer, Map<Integer, Integer>>(), hashMapFactory);
        hiddenCells = MapUtils.lazyMap(new HashMap<Integer, Map<Integer, Cell>>(), new Factory() {
            public Object create() {
                return new HashMap<Integer, Cell>();
            }
        });
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cell c = getVisibleCell(rowIndex, columnIndex);
        return super.getValueAt(c.getRow(), c.getColumn());
    }

    /**
     * Sets the column span of the cell at the supplied position to <code>columnSpan</code>.
     * This causes this cell to hide certain logical cells.
     *
     * @param row
     * @param column
     * @param columnSpan the number of columns this cell should span (atleast 2)
     *
     * @throws IllegalArgumentException if <code>row</code> or <code>column</code>
     * are not in the model or <code>columnSpan</code> is less then 2
     */
    public void setColumnSpan(int row, int column, int columnSpan) {
        Validate.isTrue(row >= 0 && row < getRowCount(), "row out of bounds (0-" + getRowCount() + ")");
        Validate.isTrue(column >= 0 && column < getColumnCount(), "column out of bounds (0-" + getColumnCount() + ")");
        Validate.isTrue(columnSpan > 1, "column span must be atleast 2 (was " + columnSpan + ")");

        columnSpans.get(column).put(row, columnSpan);
        // hide all cells
        for (int i = 0, n = getRowSpan(row, column); i < n; i++) {
            for (int j = 1; j < columnSpan; j++) {
                hideCell(row + i, column + j, row, column);
            }
        }
    }

    /**
     * Sets the row span of the cell at the supplied position to <code>rowSpan</code>.
     * This causes this cell to hide certain logical cells.
     *
     * @param row
     * @param column
     * @param rowSpan the number of row this cell should span (atleast 2)
     *
     * @throws IllegalArgumentException if <code>row</code> or <code>column</code>
     * are not in the model or <code>rowSpan</code> is less then 2
     */
    public void setRowSpan(int row, int column, int rowSpan) {
        Validate.isTrue(row >= 0 && row < getRowCount(), "row out of bounds (0-" + getRowCount() + ")");
        Validate.isTrue(column >= 0 && column < getColumnCount(), "column out of bounds (0-" + getColumnCount() + ")");
        Validate.isTrue(rowSpan > 1, "row span must be atleast 2 (was " + rowSpan + ")");

        rowSpans.get(row).put(column, rowSpan);
        // hide all cells
        for (int i = 1; i < rowSpan; i++) {
            for (int j = 0, n = getColumnSpan(row, column); j < n; j++) {
                hideCell(row + i, column + j, row, column);
            }
        }
    }

    /**
     * Remembers all the hidden cell and the cell that hides them.
     *
     * @param row
     * @param column
     * @param hidingCellRow
     * @param hidingCellColumn
     */
    private void hideCell(int row, int column, int hidingCellRow, int hidingCellColumn) {
        hiddenCells.get(row).put(column, new CellImpl(hidingCellRow, hidingCellColumn));
    }

    public int getColumnSpan(int row, int column) {
        Integer span = columnSpans.get(column).get(row);
        return span != null ? span : 1;
    }

    public int getRowSpan(int row, int column) {
        Integer span = rowSpans.get(row).get(column);
        return span != null ? span : 1;
    }

    public boolean isCellVisible(int row, int column) {
        return hiddenCells.get(row).get(column) == null;
    }

    public Cell getVisibleCell(int row, int column) {
        if(isCellVisible(row, column)) {
            // either return the logical cell
            return new CellImpl(row, column);
        }

        // or return the cell that hides the logical cell
        return hiddenCells.get(row).get(column);
    }

    public void removeSpan(int row, int column) {
        // unhide cells
        for (int i = 0, rowSpan = getRowSpan(row, column); i < rowSpan; i++) {
            for (int j = 0, columnSpan = getColumnSpan(row, column); j < columnSpan; j++) {
                hiddenCells.get(row + i).remove(column + j);
            }
        }

        // remove spans
        rowSpans.get(row).remove(column);
        columnSpans.get(column).remove(row);
    }


    /**
     * Basic immutable implementation of the {@link Cell} interface.
     */
    private static final class CellImpl implements Cell {
        private final int row;
        private final int column;

        private CellImpl(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
