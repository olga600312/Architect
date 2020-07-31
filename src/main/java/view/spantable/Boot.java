package view.spantable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 * {@link SpanTable} usage example.
 *
 * @author ytoh
 */
public class Boot {

    private static PageFormat mPageFormat;

    public static void main(String[] args) {
        // DATA
        String[][] data = new String[][]{
            {"a1", "a2", "a3", "a4"},
            {"b1", "b2", "b3", "b4"},
            {"c1", "c2", "c3", "c4"},
            {"d1", "d2", "d3", "d4"},
            {"e1", "e2", "e3", "e4"}
        };

        // FRAME
        JFrame f = new JFrame("test");
        f.setSize(400, 300);
        f.getContentPane().setLayout(new BorderLayout());

        PrinterJob pj = PrinterJob.getPrinterJob();
        mPageFormat = pj.defaultPage();

        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File", true);
        file.add(new FilePrintAction(f)).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        file.add(new FilePageSetupAction()).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK | Event.SHIFT_MASK));
        file.addSeparator();
        file.add(new FileQuitAction()).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
        mb.add(file);

        f.setJMenuBar(mb);

        // SPAN TABLE INITIALIZATION
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"1", "2", "3", "4"});
        DefaultSpanModel spanModel = new DefaultSpanModel(model);

        f.getContentPane().add(new JScrollPane(new SpanTable(spanModel)), BorderLayout.NORTH);

        spanModel.setColumnSpan(0, 0, 3);
        spanModel.setRowSpan(1, 1, 2);
        spanModel.setColumnSpan(1, 1, 2);
        spanModel.setRowSpan(2, 0, 3);

//        spanModel.removeSpan(2, 0);

        // OTHER STUFF
        f.pack();
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }

    public static class FilePrintAction extends AbstractAction {
        private JFrame frame;

        public FilePrintAction(JFrame frame) {
            super("Print");
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent ae) {
            PrinterJob pj = PrinterJob.getPrinterJob();
            ComponentPrintable cp = new ComponentPrintable(frame.getContentPane());
            pj.setPrintable(cp, mPageFormat);
            if (pj.printDialog()) {
                try {
                    pj.print();
                } catch (PrinterException e) {
                    System.out.println(e);
                }
            }
        }
    }

    public static class FilePageSetupAction extends AbstractAction {

        public FilePageSetupAction() {
            super("Page setup...");
        }

        public void actionPerformed(ActionEvent ae) {
            PrinterJob pj = PrinterJob.getPrinterJob();
            mPageFormat = pj.pageDialog(mPageFormat);
        }
    }

    public static class FileQuitAction extends AbstractAction {

        public FileQuitAction() {
            super("Quit");
        }

        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

    public static class ComponentPrintable implements Printable {

        private Component mComponent;

        public ComponentPrintable(Component c) {
            mComponent = c;
        }

        public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            boolean wasBuffered = disableDoubleBuffering(mComponent);
            mComponent.paint(g2);
            restoreDoubleBuffering(mComponent, wasBuffered);
            return PAGE_EXISTS;
        }

        private boolean disableDoubleBuffering(Component c) {
            if (c instanceof JComponent == false) {
                return false;
            }
            JComponent jc = (JComponent) c;
            boolean wasBuffered = jc.isDoubleBuffered();
            jc.setDoubleBuffered(false);
            return wasBuffered;
        }

        private void restoreDoubleBuffering(Component c, boolean wasBuffered) {
            if (c instanceof JComponent) {
                ((JComponent) c).setDoubleBuffered(wasBuffered);
            }
        }
    }
}
