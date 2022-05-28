package redcoder.chat.client.ui;

import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;

public class ChatFrame extends JFrame {

    public static final int DEFAULT_WIDTH = 900;
    public static final int DEFAULT_HEIGHT = 600;
    private final ChatPanel chatPanel;

    public ChatFrame(User user) {
        super("Rc聊天室");
        this.chatPanel = new ChatPanel(user);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        add(chatPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }
}
