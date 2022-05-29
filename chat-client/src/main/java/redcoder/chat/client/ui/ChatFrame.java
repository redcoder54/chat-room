package redcoder.chat.client.ui;

import redcoder.chat.client.connection.ChatConnection;
import redcoder.chat.client.connection.MessageReceiver;
import redcoder.chat.client.connection.MessageSender;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatFrame extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(ChatFrame.class.getName());
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;
    private final MessageSender sender;
    private final MessageReceiver receiver;
    private UserPanel userPanel;
    private MessageDisplayPanel displayPanel;
    private ChatConnection connection;
    private User loggedUser;

    public ChatFrame(User loggedUser) {
        super("Rc聊天室");
        this.loggedUser = loggedUser;
        this.sender = new MessageSender();
        this.receiver = new MessageReceiver(this);
    }

    public void createAndShowGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (connection != null) {
                    connection.close();
                }
            }
        });

        ChatPanel chatPanel = new ChatPanel(this);
        userPanel = chatPanel.getUserPanel();
        displayPanel = chatPanel.getMessagePanel().getDisplayPanel();
        add(chatPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // 打开连接
        SwingUtilities.invokeLater(this::openConnection);
    }

    private void openConnection() {
        try {
            connection = new ChatConnection(sender, receiver, this);
            connection.open();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }

    public MessageSender getSender() {
        return sender;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public MessageDisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
