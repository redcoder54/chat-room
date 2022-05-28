package redcoder.chat.client.ui;

import redcoder.chat.client.connection.MessageSender;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MessageInputPanel extends JPanel {

    private final JTextArea msgInputTextArea;

    public MessageInputPanel(User user, MessageDisplayPanel displayPanel) {
        super();
        msgInputTextArea = new JTextArea(3, 30);
        msgInputTextArea.setLineWrap(true);
        msgInputTextArea.setWrapStyleWord(true);

        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = msgInputTextArea.getText();
                if (!text.isEmpty()) {
                    Message message = new Message(user, text);
                    displayPanel.addMessage(message, true);
                    SwingUtilities.invokeLater(() -> MessageSender.send(message, displayPanel));
                }
            }
        });

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnPanel.add(sendButton, BorderLayout.LINE_END);

        setLayout(new BorderLayout());
        add(msgInputTextArea, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
