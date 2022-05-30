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
    private final ScrollableTextInputPane textInputPane;

    public MessageInputPanel(ChatFrame chatFrame, MessageDisplayPanel displayPanel) {
        super(new MigLayout("fill, flowy"));
        this.chatFrame = chatFrame;
        this.displayPanel = displayPanel;
        this.textInputPane = new ScrollableTextInputPane();
        init();
    }

    private void init() {
        setBackground(Color.WHITE);

        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(this);

        add(textInputPane, "grow, center");
        add(sendButton, "right, south, w 60!, gap 0 20 0 5");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textInputPane.getText();
        if (!text.isEmpty()) {
            User user = chatFrame.getLoggedUser();
            Message message = new Message(user, text.trim());
            displayPanel.addMessage(message, true);
            SwingUtilities.invokeLater(() -> chatFrame.getSender().send(message));
        }
    }

    private class ScrollableTextInputPane extends JScrollPane {
        private final JTextPane textPane;

        public ScrollableTextInputPane() {
            textPane = new JTextPane();
            textPane.setMaximumSize(new Dimension(displayPanel.getWidth(), 100));
            // textPane.setContentType("text/html");
            // textPane.setMargin(new Insets(2, 2, 2, 2));

            setBorder(BorderFactory.createEmptyBorder());
            setViewportView(textPane);
        }

        public String getText() {
            return textPane.getText();
        }
    }
}
