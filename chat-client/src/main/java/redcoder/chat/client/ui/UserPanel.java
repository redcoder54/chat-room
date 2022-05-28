package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 600;

    private int userNum = 0;
    private final JLabel label;
    private final InternalUserPanel internalUserPanel;

    public UserPanel(User user) {
        super(new MigLayout("flowy, fillx"));
        label = new JLabel("在线人数：" + userNum);
        internalUserPanel = new InternalUserPanel();

        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        add(label);
        add(internalUserPanel,"growx");
        addUser(user);
    }

    public void addUser(User user) {
        internalUserPanel.addUser(user);
        userNum++;
        label.setText("在线人数：" + userNum);
        validate();
    }
}
