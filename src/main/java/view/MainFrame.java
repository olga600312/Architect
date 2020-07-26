package view;

import domain.Cause;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 12:28
 */
public class MainFrame {
    private JFrame frame;
    private CauseModel model;

    public void show(CauseModel model) {
        this.model = model;
        int gap = 3;
        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JComponent jc = (JComponent) super.prepareRenderer(renderer, row, column);

                jc.setBorder(BorderFactory.createLineBorder(Color.WHITE, gap));
                return jc;
            }
        };
        int rowHeight = 50;
        table.setRowHeight(rowHeight);
        table.setDefaultRenderer(Cause.Entry.class, new CauseRenderer());
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = columns.nextElement();
            tableColumn.setPreferredWidth(rowHeight);
           // tableColumn.setMaxWidth(rowHeight);
        }
        table.setGridColor(Color.WHITE);

        table.getTableHeader().setReorderingAllowed(false);//Was also able to do this within NetBeans GUI Builder by doing Table Contents from Jtable inspector item
       // table.getTableHeader().setResizingAllowed(false);


        //load main table to scrollpane
        JScrollPane sp = new JScrollPane(table);
        sp.setViewportView(table);

        //createRowHeader(model, table, rowHeight, sp);


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(sp);
        table.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        frame.setContentPane(pnl);
        frame.pack();
        frame.setSize(new Dimension(table.getColumnCount()*rowHeight*gap,table.getPreferredScrollableViewportSize().height));
        frame.setVisible(true);
    }

    private void createRowHeader(CauseModel model, JTable table, int rowHeight, JScrollPane sp) {
        JList rowHeader = new JList(getListModel());
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(table.getRowHeight()
                + table.getRowMargin());
        //  + table.getIntercellSpacing().height);

        /*//load row header to scrollpane's row header
        sp.setRowHeaderView(rowHeader);*/

//get model for JTable that will be used as the row header, fill in values
        DefaultTableModel rowHeaderTableModel = new DefaultTableModel(0, 1);//one column
        model.getData().forEach(e -> rowHeaderTableModel.addRow(new Object[]{e.getRowHeader().getName()}));


        //set model for row header, put in data. Alter renderer to make it like col header
        JTable dispTableRowHeader = new JTable(rowHeaderTableModel){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JComponent jc = (JComponent) super.prepareRenderer(renderer, row, column);
                jc.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                return jc;
            }
        };
        dispTableRowHeader.setRowHeight(rowHeight);
        dispTableRowHeader.getColumnModel().getColumn(0).setMaxWidth(rowHeight*2);
        dispTableRowHeader.getColumnModel().getColumn(0).setPreferredWidth(rowHeight);
        dispTableRowHeader.setDefaultRenderer(Object.class, new RowHeaderRenderer());//makes it gray but not like the header :/
        //dispTableRowHeader.setDefaultRenderer(Object.class, jScrollPane2.getColumnHeader().getDefaultRenderer());

        //load row header to scrollpane's row header
        sp.setRowHeaderView(dispTableRowHeader);

        //set the table corner and disallow reordering and resizing
        JTableHeader corner = dispTableRowHeader.getTableHeader();
        corner.setReorderingAllowed(false);
        corner.setResizingAllowed(false);
        sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, corner);//load to scrollpane
    }

    private ListModel getListModel(){
       return new AbstractListModel() {
            String headers[] = { "a", "b" };

            @Override
            public int getSize() {
                return headers.length;
            }

            @Override
            public Object getElementAt(int index) {
                return headers[index];
            }
        };
    }

    private class CauseRenderer extends DefaultTableCellRenderer {
        public CauseRenderer() {
            setOpaque(true);
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Cause.Entry e = (Cause.Entry) value;
            setText("" + e.getTotal());
            setBackground(e.getColor());
            return this;
        }
    }

    private class RowHeaderRenderer extends DefaultTableCellRenderer {
        public RowHeaderRenderer() {
            setOpaque(false);
            setHorizontalAlignment(JLabel.CENTER);
            setBorder(null);
        }

    }
}
