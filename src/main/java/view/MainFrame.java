package view;

import com.sun.istack.internal.NotNull;
import domain.Cause;
import domain.LocationType;
import global.App;
import global.Constants;
import global.Utilities;
import lombok.Data;
import lombok.Setter;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;

import static global.Constants.*;

/**
 * Create by Aviv POS
 * User: olgats
 * Date: 26/07/2020
 * Time: 12:28
 */
public class MainFrame {
    private JFrame frame;
    private CauseModel model;
    int gap = 1;
    @Setter
    private String imagePath;

    JTable table;
    private JPanel tblPanel;
    private FooterTable footer;

    public void show(CauseModel model) {
        this.model = model;

        table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JComponent jc = (JComponent) super.prepareRenderer(renderer, row, column);
                return prepareTableRenderer(jc, column);
            }


        };
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(BACKGROUND);
        ImagePanel imagePanel = new ImagePanel();
        pnlRight.add(imagePanel, BorderLayout.NORTH);
        URL resource = App.class.getClassLoader().getResource("tweetsAnalysisCover.png");

        try {
            LogoPanel logoPanel = new LogoPanel(ImageIO.read(resource));
            pnlRight.add(logoPanel, BorderLayout.SOUTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rowHeight = (d.width - imagePanel.getWidth()) / 65;
        table.setRowHeight(rowHeight);
        table.setFont(CAVIAR_DREAMS);
        table.setDefaultRenderer(Cause.Entry.class, new CauseRenderer());
        table.setDefaultRenderer(String.class, new RowHeaderRenderer());

        //table.setCellSelectionEnabled(true);

        VerticalTableHeaderCellRenderer headerRenderer = new VerticalTableHeaderCellRenderer() {


            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value.toString()+"  ", isSelected, hasFocus, row, column);
                lbl.setOpaque(true);
                lbl.setFont(CAVIAR_DREAMS);
                lbl.setBorder(EMPTY_BORDER);
                lbl.setBackground(table.getSelectedColumn()==column?table.getSelectionBackground():table.getBackground());
               //System.err.println("selected "+table.getCellSelectionEnabled());
                return lbl;
            }
        };


        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        int i = 0;
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = columns.nextElement();
            tableColumn.setPreferredWidth(rowHeight * (i == 0 ? 3 : 1));
            tableColumn.setMinWidth(rowHeight * (i == 0 ? 3 : 1));
            if (i > 0) {

                tableColumn.setMaxWidth(rowHeight);
            }
            tableColumn.setHeaderRenderer(headerRenderer);
            i++;
            // tableColumn.setMaxWidth(rowHeight);
        }
        table.setGridColor(new Color(0xffffff));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);//Was also able to do this within NetBeans GUI Builder by doing Table Contents from Jtable inspector item
        //table.getTableHeader().setResizingAllowed(false);
        tableHeader.setBackground(BACKGROUND);
        tableHeader.setForeground(Constants.FOREGROUND);


        //load main table to scrollpane
        JScrollPane sp = new JScrollPane(table);
        // sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        sp.getViewport().setBackground(BACKGROUND);
        sp.setBackground(BACKGROUND);
        sp.getVerticalScrollBar().setBackground(BACKGROUND);

        Border empty = new EmptyBorder(0, 0, 0, 0);
        sp.setBorder(empty);

       footer = new FooterTable(table);
        model.addTableModelListener(e -> {
            TableColumnModel cmFooter = footer.getColumnModel();
            TableColumnModel cm = table.getColumnModel();

            cmFooter.getColumn(0).setWidth(cm.getColumn(0).getWidth());
        });
        model.fireTableDataChanged();
        FooterRenderer footerRenderer = new FooterRenderer();
        footer.setForeground(BACKGROUND);
        footer.setDefaultRenderer(LocationType.class, footerRenderer);
        footer.setRowHeight(table.getRowHeight());
        footer.setShowGrid(false);
        footer.setIntercellSpacing(new Dimension(0, 0));

        tblPanel = new JPanel(new BorderLayout());
        tblPanel.add(BorderLayout.CENTER, sp);
        tblPanel.add(BorderLayout.SOUTH, footer);

        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(tblPanel);
        pnl.add(pnlRight, BorderLayout.EAST);

        if(imagePath!=null&&!imagePath.trim().isEmpty()) {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Point p = e.getPoint();
                    int row = table.rowAtPoint(p);
                    int col = table.columnAtPoint(p);
                    if (row >= 0 && col >= 0) {
                        Object obj = table.getValueAt(row, col);
                        if (obj instanceof Cause.Entry) {
                            Cause.Entry entry = (Cause.Entry) obj;

                            try {
                                File file = new File(imagePath, model.causeAtRow(row).getRowHeader().getName() + "_" + entry.getColumnHeader().getName() + ".jpeg");
                                if (file.isFile())
                                    imagePanel.setImage(ImageIO.read(file));
                                else {
                                    imagePanel.setImage(null);
                                }
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                                imagePanel.setImage(null);
                            }


                        } else
                            imagePanel.setImage(null);
                    } else {
                        imagePanel.setImage(null);
                    }
                    imagePanel.repaint();
                }
            });
        }
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(pnl);

       /* JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(sp);
        splitPane.setRightComponent(imagePanel);
        splitPane.setDividerLocation(0.8);
        splitPane.setResizeWeight(1);
        frame.setContentPane(splitPane);*/
        frame.pack();
        //frame.setSize(new Dimension(table.getColumnCount()*rowHeight*gap,table.getPreferredScrollableViewportSize().height));
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }
    @NotNull
    private Component prepareTableRenderer(JComponent jc, int column) {

        if (column == 0)
            jc.setBorder(EMPTY_BORDER);
        else
            jc.setBorder(BorderFactory.createLineBorder(BACKGROUND, gap));
        return jc;
    }

    private class FooterTable extends JTable {
        private JTable mainTable;

        public FooterTable(JTable mainTable) {
            super(new CauseFooterModel((CauseModel) mainTable.getModel()));
            this.mainTable = mainTable;
            refresh();
        }
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            JComponent jc = (JComponent) super.prepareRenderer(renderer, row, column);
            return prepareTableRenderer(jc, column);
        }

        private void refresh() {
            CauseModel causeModel=(CauseModel) mainTable.getModel();
            TableColumnModel cmFooter = getColumnModel();
            TableColumnModel cm = mainTable.getColumnModel();

            cmFooter.getColumn(0).setPreferredWidth(cm.getColumn(0).getWidth());
            cmFooter.getColumn(0).setWidth(cm.getColumn(0).getWidth());
            cmFooter.getColumn(0).setMinWidth(cm.getColumn(0).getWidth());
            int count = cmFooter.getColumnCount();
            for (int i = 1; i < count; i++) {
                LocationType type = (LocationType) getModel().getValueAt(0, i);
                Collection<Integer> columns=causeModel.getColumnsByType(type);
                int w=columns.stream().mapToInt(k->cm.getColumn(k).getWidth()).sum();
                cmFooter.getColumn(i).setWidth(w);
                cmFooter.getColumn(i).setPreferredWidth(w);
            }

        }
    }


    private static class CauseRenderer extends DefaultTableCellRenderer {
        public CauseRenderer() {
            setOpaque(true);
            setHorizontalAlignment(JLabel.CENTER);
            setFont(new Font("Caviar Dreams Bold", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Cause.Entry e = (Cause.Entry) value;
            setText("" + (isSelected && hasFocus ? e.getTotal() : ""));
            setBackground(e.getColor());
            return this;
        }
    }


    private static class FooterRenderer extends DefaultTableCellRenderer {
        public FooterRenderer() {
            setOpaque(true);
            setHorizontalAlignment(JLabel.LEFT);
            setFont(new Font("Caviar Dreams Bold", Font.BOLD, 14));

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            LocationType e = (LocationType) value;
            setText("  "+e.name());
            setBackground(Utilities.setSaturation(e.getInitColor(), 0.27f));
            return this;
        }
    }

    private static class RowHeaderRenderer extends DefaultTableCellRenderer {
        public RowHeaderRenderer() {

            setBackground(BACKGROUND);
            setForeground(FOREGROUND);
            setFont(CAVIAR_DREAMS);
            setBorder(EMPTY_BORDER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, "  " + value, isSelected, hasFocus, row, column);
        }
    }

    @Data
    private class ImagePanel extends JPanel {

        private Image image;

        public ImagePanel() {
            setBackground(BACKGROUND);
        }

        @Override
        public Dimension getPreferredSize() {

            return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width * 20 / 100, 400);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (image != null) {
                float scale = Math.min(((float) getWidth()) / ((float) image.getWidth(null)), ((float) getHeight()) / ((float) image.getHeight(null)));
                g.drawImage(image, 0, table.getTableHeader().getHeight(), (int) (image.getWidth(null) * scale), (int) (image.getHeight(null) * scale), null);
            }
        }
    }

    @Data
    private class LogoPanel extends JPanel {

        private Image image;

        public LogoPanel(Image image) {
            this.image = image;
            setBackground(BACKGROUND);
            //  setBorder(BorderFactory.createLineBorder(Color.red));
        }

        @Override
        public Dimension getPreferredSize() {
            int offset = 0;//Math.max(2, frame.getSize().height - table.getSize().height - table.getTableHeader().getHeight());
            return new Dimension(400, 200 + offset);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (image != null) {
                int offset = Math.max(2, frame.getSize().height - table.getSize().height - table.getTableHeader().getHeight());
                float scale = Math.min(((float) getWidth()) / ((float) image.getWidth(null)), (float) getHeight() / ((float) image.getHeight(null)));
                int width = (int) (image.getWidth(null) * scale);
                int height = (int) (image.getHeight(null) * scale);

                g.drawImage(image, (getWidth() - width) / 2, getHeight()-height-footer.getRowHeight(), width, height, null);
               /* g.setColor(Color.darkGray);
                g.drawRect((getWidth() - width) / 2, getHeight()-height,(getWidth() - width) / 2+width,height);*/
            }
        }
    }

}
