package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageInputPanel extends JPanel implements ActionListener {

    private final ChatFrame chatFrame;
    private final MessageDisplayPanel displayPanel;
    private final JTextPane msgInputTextPane;

    public MessageInputPanel(ChatFrame chatFrame, MessageDisplayPanel displayPanel) {
        super(new MigLayout("fill, flowy"));
        this.chatFrame = chatFrame;
        this.displayPanel = displayPanel;
        this.msgInputTextPane = new JTextPane();
        init();
    }

    private void init() {
        setBackground(Color.WHITE);

        msgInputTextPane.setMaximumSize(new Dimension(displayPanel.getWidth(), 100));
        // msgInputTextPane.setContentType("text/html");
        // msgInputTextPane.setMargin(new Insets(2, 2, 2, 2));
        JScrollPane msgScrollPane = new JScrollPane(msgInputTextPane);
        msgScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(this);

        add(msgScrollPane,"grow, center");
        add(sendButton,"right, south, w 60!, gap 0 20 0 5");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = msgInputTextPane.getText();
        if (!text.isEmpty()) {
            User user = chatFrame.getLoggedUser();
            Message message = new Message(user, text.trim());
            displayPanel.addMessage(message, true);
            SwingUtilities.invokeLater(() -> chatFrame.getSender().send(message));
        }
    }
}
