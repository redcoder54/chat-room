package redcoder.chat.client.ui;

import redcoder.chat.client.model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageInputPanel extends JPanel implements ActionListener {

    private final ChatFrame chatFrame;
    private final MessageDisplayPanel displayPanel;
    private final JTextArea msgInputTextArea;

    public MessageInputPanel(ChatFrame chatFrame, MessageDisplayPanel displayPanel) {
        super();
        this.chatFrame = chatFrame;
        this.displayPanel = displayPanel;
        this.msgInputTextArea = new JTextArea(3, 30);
        init();
    }

    private void init() {
        msgInputTextArea.setLineWrap(true);
        msgInputTextArea.setWrapStyleWord(true);

        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(this);

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnPanel.add(sendButton, BorderLayout.LINE_END);

        setLayout(new BorderLayout());
        add(msgInputTextArea, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = msgInputTextArea.getText();
        if (!text.isEmpty()) {
            Message message = new Message(chatFrame.getUser(), text);
            displayPanel.addMessage(message, true);
            SwingUtilities.invokeLater(() -> chatFrame.getSender().send(message));
        }
    }
}
