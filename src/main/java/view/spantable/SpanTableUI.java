package view.spantable;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;

/**
 * UI component for rendering a span table.
 *
 * @author ytoh
 */
public class SpanTableUI extends BasicTableUI {

    @Override
    public void paint(Graphics g, JComponent c) {
        if(table.getModel() instanceof SpanModel) {
            Rectangle rectangle = g.getClipBounds();
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    // only paint visible cells
                    if (((SpanModel) table.getModel()).isCellVisible(i, j)) {
                        Rectangle rect = table.getCellRect(i, j, true);
                        if (rect.intersects(rectangle)) {
                            paintCell(i, j, g, rect);
                        }
                    }
                }
            }
        // if this is not a span table
        } else {
            super.paint(g, c);
        }
    }

    /**
     * Paints visible cells using the editor or renderer component.
     * If the cell spans more then one row/column its area is stretched to hide
     * the underlying cells.
     *
     * @param row
     * @param column
     * @param g
     * @param area
     */
    private void paintCell(int row, int column, Graphics g, Rectangle area) {
        int verticalMargin = table.getRowMargin();
        int horizontalMargin = table.getColumnModel().getColumnMargin();

        area.setBounds(area.x + horizontalMargin / 2,
                area.y + verticalMargin / 2,
                area.width - horizontalMargin,
                area.height - verticalMargin);

        if (table.isEditing() && table.getEditingRow() == row && table.getEditingColumn() == column) {
            Component component = table.getEditorComponent();
            component.setBounds(area);
            component.validate();
        } else {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component component = table.prepareRenderer(renderer, row, column);
            if (component.getParent() == null) {
                rendererPane.add(component);
            }
            rendererPane.paintComponent(g, component, table, area.x, area.y, area.width, area.height, true);
        }
    }
}