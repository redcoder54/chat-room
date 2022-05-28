package redcoder.chat.client.model.headimage;

import javax.swing.*;
import java.awt.*;

public class HeadImageIcon implements Icon {

    private final String headImageName;
    private final ImageIcon imageIcon;

    public HeadImageIcon(String headImageName, ImageIcon imageIcon) {
        this.headImageName = headImageName;
        this.imageIcon = imageIcon;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        imageIcon.paintIcon(c, g, x, y);
    }

    @Override
    public int getIconWidth() {
        return imageIcon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return imageIcon.getIconHeight();
    }

    public String getHeadImageName() {
        return headImageName;
    }
}
