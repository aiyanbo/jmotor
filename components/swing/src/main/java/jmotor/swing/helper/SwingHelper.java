package jmotor.swing.helper;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * Component:Swing
 * Description:Swing helper
 * Date: 12-1-7
 *
 * @author Andy.Ai
 */
public class SwingHelper {
    private SwingHelper() {
    }

    public static void setScreenCenter(Container container) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenDimension = toolkit.getScreenSize();
        int width = container.getWidth();
        int height = container.getHeight();
        Point point = new Point();
        point.setLocation((screenDimension.getWidth() - width) / 2,
                (screenDimension.getHeight() - height) / 2);
        container.setLocation(point);
    }
}
