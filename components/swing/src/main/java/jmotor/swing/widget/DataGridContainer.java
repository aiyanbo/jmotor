package jmotor.swing.widget;

import jmotor.swing.DataWidget;

/**
 * Component:
 * Description:
 * Date: 12-1-14
 *
 * @author Andy.Ai
 */
public interface DataGridContainer extends DataWidget {
    int getSelectedIndex();

    void setSelectedIndex(int index);
}
