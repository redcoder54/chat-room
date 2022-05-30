package redcoder.chat.client.ui;

import redcoder.chat.client.connection.ChatConnection;
import redcoder.chat.client.message.MessageReceiver;
import redcoder.chat.client.message.MessageSender;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.action.ActionName;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static redcoder.chat.client.ui.action.ActionName.CLOSE_SESSION;
import static redcoder.chat.client.ui.action.ActionName.NEW_SESSION;

public class ChatFrame extends RcFrame {

    private static final Logger LOGGER = Logger.getLogger(ChatFrame.class.getName());
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;
    private static final Font MENU_FONT = new Font(null, Font.BOLD, 14);
    private static final Font ITEM_FONT = new Font(null, Font.ITALIC, 12);

    private final User loggedUser;
    private final MessageSender sender;
    private final MessageReceiver receiver;
    private UserPanel userPanel;
    private MessageDisplayPanel displayPanel;
    private ChatConnection connection;

    public ChatFrame(User loggedUser) {
        super("Rc聊天室");
        this.loggedUser = loggedUser;
        this.sender = new MessageSender();
        this.receiver = new MessageReceiver(this);
    }

    public void createAndShowGUI() {
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        // 聊天面板
        ChatPanel chatPanel = new ChatPanel(this);
        userPanel = chatPanel.getUserPanel();
        displayPanel = chatPanel.getMessagePanel().getDisplayPanel();
        add(chatPanel);

        // 配置菜单
        Map<ActionName, KeyStroke> keyStrokes = Framework.getKeyStrokes();
        Map<ActionName, Action> actions = Framework.getActions();
        configureMenu(keyStrokes, actions);

        // 绑定快捷键
        Framework.addKeyBinding(getRootPane());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // 打开连接
        SwingUtilities.invokeLater(this::openConnection);
        // 虚拟机关闭时，关闭连接
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnection));
    }

    public void openConnection() {
        try {
            connection = new ChatConnection(sender, receiver, loggedUser);
            connection.open();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to open connection.", e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to close connection.", e);
            }
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

    private void configureMenu(Map<ActionName, KeyStroke> keyStrokes,
                               Map<ActionName, Action> actions) {
        JMenu sessionMenu = createSessionMenu(keyStrokes, actions);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(sessionMenu);
        setJMenuBar(menuBar);
    }

    private JMenu createSessionMenu(Map<ActionName, KeyStroke> keyStrokes,
                                    Map<ActionName, Action> actions) {
        JMenu menu = new JMenu("会话");
        menu.setFont(MENU_FONT);
        addMenuItem(menu, keyStrokes.get(NEW_SESSION), actions.get(NEW_SESSION));
        addMenuItem(menu, keyStrokes.get(CLOSE_SESSION), actions.get(CLOSE_SESSION));
        return menu;
    }

    private void addMenuItem(JMenu menu, KeyStroke keyStroke, Action action) {
        JMenuItem item = menu.add(action);
        item.setFont(ITEM_FONT);
        item.setAccelerator(keyStroke);
    }
}
