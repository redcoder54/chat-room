package redcoder.chat.client.ui.message;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.TextMessage;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.client.ui.Framework;
import redcoder.chat.client.ui.action.ActionName;
import redcoder.chat.client.dnd.MessageTransferHandler;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.logging.Logger;

import static redcoder.chat.client.ui.action.ActionName.REDO;
import static redcoder.chat.client.ui.action.ActionName.UNDO;

public class MessageInputPanel extends JPanel implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(MessageInputPanel.class.getName());
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

    public void undo(ActionEvent e) {
        textInputPane.undo(e);
    }

    public void redo(ActionEvent e) {
        textInputPane.redo(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textInputPane.getText();
        if (!text.isEmpty()) {
            User user = chatFrame.getLoggedUser();
            Message message = new TextMessage(user, text.trim());
            displayPanel.addMessage(message, true);
            SwingUtilities.invokeLater(() -> chatFrame.getSender().send(message));
        }
    }

    private class ScrollableTextInputPane extends JScrollPane {
        private final JTextPane textPane;
        private final UndoManager undoManager;

        public ScrollableTextInputPane() {
            this.undoManager = new UndoManager();
            textPane = new JTextPane();
            setBorder(BorderFactory.createEmptyBorder());
            setViewportView(textPane);
            init();
        }

        private void init() {
            textPane.setMaximumSize(new Dimension(displayPanel.getWidth(), 100));
            // textPane.setContentType("text/html");
            // textPane.setMargin(new Insets(2, 2, 2, 2));
            textPane.setDragEnabled(true);
            textPane.setTransferHandler(new MessageTransferHandler());

            Document document = textPane.getDocument();
            document.addUndoableEditListener(e -> {
                undoManager.addEdit(e.getEdit());
            });

            // add key-binding
            addKeyBinding();
        }

        private void addKeyBinding() {
            Map<ActionName, Action> actions = Framework.getActions();
            Map<ActionName, KeyStroke> keyStrokes = Framework.getKeyStrokes();

            ActionMap actionMap = textPane.getActionMap();
            actionMap.put(UNDO, actions.get(UNDO));
            actionMap.put(REDO, actions.get(REDO));

            InputMap inputMap = textPane.getInputMap();
            inputMap.put(keyStrokes.get(UNDO), actions.get(UNDO));
            inputMap.put(keyStrokes.get(REDO), actions.get(REDO));
        }

        public String getText() {
            return textPane.getText();
        }

        public void undo(ActionEvent e) {
            try {
                undoManager.undo();
            } catch (CannotUndoException ex) {
                // ignore
            }
        }

        public void redo(ActionEvent e) {
            try {
                undoManager.redo();
            } catch (CannotRedoException ex) {
                // ignore
            }
        }
    }
}
