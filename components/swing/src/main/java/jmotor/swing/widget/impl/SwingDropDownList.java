package jmotor.swing.widget.impl;

import jmotor.swing.widget.DropDownList;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Component:
 * Description:
 * Date: 12-1-6
 *
 * @author Andy.Ai
 */
public class SwingDropDownList extends JPanel implements DropDownList {
    public SwingDropDownList() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public Object getValue() {
        return null;
    }
}
