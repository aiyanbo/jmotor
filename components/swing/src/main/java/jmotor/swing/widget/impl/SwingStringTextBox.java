package jmotor.swing.widget.impl;

import jmotor.swing.widget.StringTextBox;
import jmotor.util.DateUtils;

import javax.swing.JTextField;
import java.text.ParseException;
import java.util.Date;

/**
 * Component:
 * Description:
 * Date: 12-1-6
 *
 * @author Andy.Ai
 */
public class SwingStringTextBox extends JTextField implements StringTextBox {

    private Object value;

    @Override
    public void setValue(Object value) {
        this.value = value;
        String stringValue;
        if (value instanceof Date) {
            stringValue = DateUtils.formatDate((Date) value);
        } else {
            stringValue = value.toString();
        }
        setText(stringValue);
    }

    @Override
    public Object getValue() {
        Object _value;
        if (value instanceof Date) {
            try {
                _value = DateUtils.parseDate(getText());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            _value = value;
        }
        return _value;
    }
}
