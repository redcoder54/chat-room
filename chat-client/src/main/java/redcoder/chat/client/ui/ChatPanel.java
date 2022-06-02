package redcoder.chat.client.ui;

import redcoder.chat.client.ui.message.MessagePanel;
import redcoder.chat.client.ui.user.UserPanel;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JSplitPane {

    public static final int DEFAULT_WIDTH = 900;
    public static final int DEFAULT_HEIGHT = 600;

    private final UserPanel userPanel;
    private final MessagePanel messagePanel;

    public ChatPanel(ChatFrame chatFrame) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.userPanel = new UserPanel(chatFrame);
        this.messagePanel = new MessagePanel(chatFrame);

        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setResizeWeight(0.2);
        setLeftComponent(userPanel);
        setRightComponent(messagePanel);
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public MessagePanel getMessagePanel() {
        return messagePanel;
    }
}
