package view;

import domain.Cause;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
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
    private Font caviarDreams = new Font("Caviar Dreams Bold", Font.BOLD, 10);
    private Color background = new Color(0x00fffffc);
    private Color foreground = Color.darkGray;
    private int insets = 4;
    private Border emptyBorder = BorderFactory.createEmptyBorder(insets, insets, insets, insets);

    public void show(CauseModel model) {
        this.model = model;
        int gap = 1;
        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                JComponent jc = (JComponent) super.prepareRenderer(renderer, row, column);

                jc.setBorder(BorderFactory.createLineBorder(Color.WHITE, gap));
                return jc;
            }
        };
        int resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        int rowHeight = d.width / 50;
        table.setRowHeight(rowHeight);
        table.setFont(caviarDreams);
        table.setDefaultRenderer(Cause.Entry.class, new CauseRenderer());
        table.setDefaultRenderer(String.class, new RowHeaderRenderer());

        VerticalTableHeaderCellRenderer headerRenderer = new VerticalTableHeaderCellRenderer() {


            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(caviarDreams);
                lbl.setBorder(emptyBorder);
                return lbl;
            }
        };


        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        int i = 0;
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = columns.nextElement();
            tableColumn.setPreferredWidth(rowHeight * (i == 0 ? 3 : 1));
            tableColumn.setHeaderRenderer(headerRenderer);
            i++;
            // tableColumn.setMaxWidth(rowHeight);
        }
        table.setGridColor(new Color(0xffffff));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);//Was also able to do this within NetBeans GUI Builder by doing Table Contents from Jtable inspector item
        // table.getTableHeader().setResizingAllowed(false);
        tableHeader.setBackground(background);
        tableHeader.setForeground(foreground);


        //load main table to scrollpane
        JScrollPane sp = new JScrollPane(table);
        sp.setViewportView(table);

        //createRowHeader(model, table, rowHeight, sp);
        ImagePanel imagePanel = new ImagePanel();
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
                            File file = new File("C:\\Users\\Olga-PC\\IdeaProjects\\Architect\\images", model.causeAtRow(row).getRowHeader().getName() + "_" + entry.getColumnHeader().getName() + ".jpeg");
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

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(sp);
        pnl.add(imagePanel, BorderLayout.EAST);
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


    private ListModel getListModel() {
        return new AbstractListModel() {
            String headers[] = {"a", "b"};

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
            setFont(new Font("Caviar Dreams Bold", Font.BOLD, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Cause.Entry e = (Cause.Entry) value;
            setText("" + (isSelected && hasFocus ? e.getTotal() : ""));
            setBackground(e.getColor());
            return this;
        }
    }

    private class RowHeaderRenderer extends DefaultTableCellRenderer {
        public RowHeaderRenderer() {

            setBackground(background);
            setForeground(foreground);
            setFont(caviarDreams);
            setBorder(emptyBorder);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JComponent lbl = (JComponent) super.getTableCellRendererComponent(table, "  " + value, isSelected, hasFocus, row, column);
            lbl.setBorder(emptyBorder);
            lbl.setBackground(background);

            lbl.setForeground(foreground);
            return lbl;
        }
    }

    @Data
    private class ImagePanel extends JPanel {
        private Image image;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (image != null) {
                float scale = Math.min(((float) getWidth()) / ((float) image.getWidth(null)), ((float) getHeight()) / ((float) image.getHeight(null)));
                g.drawImage(image, 0, 0, (int) (image.getWidth(null) * scale), (int) (image.getHeight(null) * scale), null);
            }
        }
    }

}
