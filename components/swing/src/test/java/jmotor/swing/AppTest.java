package jmotor.swing;

import jmotor.swing.helper.SwingHelper;
import jmotor.swing.widget.StringTextBox;
import jmotor.swing.widget.impl.SwingStringTextBox;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;


public class AppTest extends JFrame {
    public static void main(String[] args) {
        AppTest app = new AppTest();
        SwingHelper.setScreenCenter(app);
        app.setVisible(true);

    }

    public AppTest() {
        initComponent();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }

    private void initComponent() {
        setSize(new Dimension(1000, 700));
        setTitle("Main Page");
        setLayout(new BorderLayout(2, 2));
        JPanel pnlMain = new JPanel();
        pnlMain.setBorder(new TitledBorder("Content"));
        pnlMain.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JTextField textBox = new JTextField();
        textBox.setText("Please enter your string");
        StringTextBox dateTextBox = new SwingStringTextBox();
        dateTextBox.setValue(new Date());
        JTable tblRow = new JTable();
        TableModel tableModel = new DefaultTableModel();
        tblRow.setModel(tableModel);
        JTableHeader tableHeader = new JTableHeader();
        TableColumnModel columnModel = new DefaultTableColumnModel();
        TableColumn textColumn = new TableColumn();
        textColumn.setHeaderValue("Text");
        columnModel.addColumn(textColumn);
        tableHeader.setColumnModel(columnModel);
        tblRow.setTableHeader(tableHeader);
        TableColumn column1 = new TableColumn();
        column1.setHeaderValue("column1");
        tblRow.addColumn(column1);
        pnlMain.add(textBox);
        pnlMain.add((Component) dateTextBox);
        pnlMain.add(tblRow);
        add(pnlMain);
    }
}
