package view.spantable;

import javax.swing.table.TableModel;

/**
 * A SpanModel is an extension of {@link TableModel} that allows the model
 * to be queried about the visibility of logical table cells and their
 * row and column span.
 *
 * @author ytoh
 */
public interface SpanModel extends TableModel {

    /**
     * This method can be used to retrieve the visible cell at the position
     * of the logical cell located by row and column.
     *
     * <code>null</code> should never be returned if <code>row</code>
     * and <code>column</code> are in range.
     *
     * @param row
     * @param column
     * @return the cell that is visible at this position
     */
    Cell getVisibleCell(int row, int column);

    /**
     * Returns <code>true</code> if the cell at position row and column is visible
     * (is not hidden by any other cell).
     *
     * Returns <code>false</code> if another cell (spanned cell) is hidding this
     * logical cell.
     *
     * @param row
     * @param column
     * @return true if no other cell is hidding this cell, false otherwise
     */
    boolean isCellVisible(int row, int column);

    /**
     * Returns the number of rows that are taken up by cell at the supplied
     * position.
     *
     * If the cell is not row spanned it takes up 1 row.
     *
     * @param row
     * @param column
     * @return a number of rows this cell spans (number >= 1)
     */
    int getRowSpan(int row, int column);

    /**
     * Returns the number of columns that are taken up by cell at the supplied
     * position.
     *
     * If the cell is not column spanned it takes up 1 column.
     *
     * @param row
     * @param column
     * @return a number of columns this cell spans (number >= 1)
     */
    int getColumnSpan(int row, int column);

    /**
     * Removes row and column span from the cell at the specified position.
     *
     * @param row
     * @param column
     */
    void removeSpan(int row, int column);

    /**
     * An abstraction of the row-column pair.
     */
    public static interface Cell {

        int getRow();

        int getColumn();
    }
}
