package jmotor.swing.widget.impl;

import jmotor.swing.widget.DataGridContainer;

import javax.swing.JTable;

/**
 * Component:
 * Description:
 * Date: 12-1-14
 *
 * @author Andy.Ai
 */
public class SwingDataGridContainer extends JTable implements DataGridContainer {
    @Override
    public int getSelectedIndex() {
        return -1;
    }

    @Override
    public void setSelectedIndex(int index) {
    }

    @Override
    public void setValue(Object value) {
    }

    @Override
    public Object getValue() {
        return null;
    }
}
