package redcoder.chat.client.ui.user;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 600;

    private final ChatFrame chatFrame;
    private final StatusPanel statusPanel;
    private final InternalUserPanel internalUserPanel;

    public UserPanel(ChatFrame chatFrame) {
        super(new MigLayout("flowy, fillx"));
        this.chatFrame = chatFrame;
        statusPanel = new StatusPanel(chatFrame.getLoggedUser().getNickname());
        internalUserPanel = new InternalUserPanel();
        init();
    }

    private void init() {
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        add(statusPanel);
        add(internalUserPanel, "growx");
        addUser(chatFrame.getLoggedUser());
    }


    public void addUser(User user) {
        internalUserPanel.addUser(user);
        statusPanel.updateOnlineUserNum(1);
    }

    public void removeUser(User user) {
        if (internalUserPanel.removeUser(user)) {
            statusPanel.updateOnlineUserNum(-1);
        }
    }
}
