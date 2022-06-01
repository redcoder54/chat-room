package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.ImageMessage;
import redcoder.chat.client.model.Message;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class MessageDisplayPanel extends JScrollPane {

    private static final Font FONT = new Font("null", Font.BOLD, 16);
    private final JPanel contentPane;

    public MessageDisplayPanel() {
        super();
        contentPane = new JPanel();
        contentPane.setLayout(new MigLayout("flowy, fillx"));

        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(contentPane);
        setAutoscrolls(true);
    }

    public void addMessage(Message message, boolean isMe) {
        JLabel headImage = new JLabel(message.getUser().getHeadImageIcon());

        JLabel nickname = new JLabel(message.getUser().getNickname());
        nickname.setFont(new Font(null, Font.PLAIN, 12));

        JTextPane textPane = new JTextPane();
        // textPane.setContentType("text/html");
        textPane.setMaximumSize(new Dimension(contentPane.getWidth() - 150, Short.MAX_VALUE));
        textPane.setEditable(false);
        textPane.setFont(FONT);
        setText(message, textPane.getStyledDocument());

        JPanel p = new JPanel(new MigLayout());
        p.add(headImage, "span 1 2, top");
        p.add(nickname, "span, wrap");
        p.add(textPane, "span");

        if (isMe) {
            contentPane.add(p, "right");
        } else {
            contentPane.add(p, "left");
        }
        contentPane.validate();
        SwingUtilities.invokeLater(() -> getViewport().scrollRectToVisible(new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight())));
    }

    private void setText(Message message, StyledDocument document) {
        try {
            if (message instanceof ImageMessage) {
                ImageMessage imageMessage = (ImageMessage) message;
                Style style = document.addStyle("image", null);
                StyleConstants.setIcon(style, new ImageIcon(imageMessage.getImageData()));
                document.insertString(0, "1", style);
            } else {
                document.insertString(0, message.getMsg(), null);
            }
        } catch (BadLocationException e) {
            // ignore
        }
    }
}
