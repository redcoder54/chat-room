package redcoder.chat.client.model;

import javax.swing.*;
import java.awt.*;

public class AvatarIcon implements Icon {

    private final String avatarName;
    private final ImageIcon imageIcon;

    public AvatarIcon(String avatarName, ImageIcon imageIcon) {
        this.avatarName = avatarName;
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

    public String getAvatarName() {
        return avatarName;
    }
}
