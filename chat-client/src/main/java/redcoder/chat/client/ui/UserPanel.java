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
    private ChatFrame chatFrame;

    public UserPanel(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
        label = new JLabel("在线人数：" + userNum);
        internalUserPanel = new InternalUserPanel();
        init();
    }

    private void init() {
        setLayout(new MigLayout("flowy, fillx"));
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        add(label);
        add(internalUserPanel, "growx");
        addUser(chatFrame.getUser());
    }


    public void addUser(User user) {
        internalUserPanel.addUser(user);
        userNum++;
        label.setText("在线人数：" + userNum);
        validate();
    }
}
