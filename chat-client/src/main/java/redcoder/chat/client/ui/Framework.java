package redcoder.chat.client.ui;

import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.action.ActionName;
import redcoder.chat.client.ui.action.CloseSessionAction;
import redcoder.chat.client.ui.action.NewSessionAction;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static redcoder.chat.client.ui.action.ActionName.CLOSE_SESSION;
import static redcoder.chat.client.ui.action.ActionName.NEW_SESSION;

public class Framework extends WindowAdapter {

    public static final Framework INSTANCE = new Framework();

    private static final Map<ActionName, KeyStroke> keyStrokes = new HashMap<>();
    private static final Map<ActionName, Action> actions = new HashMap<>();
    private static int numFrame = 0;
    private static RcFrame activatedFrame;

    static {
        keyStrokes.put(ActionName.NEW_SESSION, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        keyStrokes.put(ActionName.CLOSE_SESSION, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));

        actions.put(ActionName.NEW_SESSION, new NewSessionAction());
        actions.put(ActionName.CLOSE_SESSION, new CloseSessionAction());
    }

    public static Map<ActionName, KeyStroke> getKeyStrokes() {
        return keyStrokes;
    }

    public static Map<ActionName, Action> getActions() {
        return actions;
    }

    public static void createLoginFrame() {
        LoginFrame frame = new LoginFrame();
        frame.createAndShowGUI();
        numFrame++;
    }

    public static void createChatFrame(User user) {
        ChatFrame frame = new ChatFrame(user);
        frame.createAndShowGUI();
        numFrame++;
    }

    public static void closeRcFrame() {
        if (activatedFrame instanceof ChatFrame) {
            ((ChatFrame) activatedFrame).closeConnection();
        }
        activatedFrame.dispose();
        activatedFrame.setVisible(false);
        numFrame--;
    }

    public static void addKeyBinding(JComponent component) {
        // register action
        ActionMap actionMap = component.getActionMap();
        for (Map.Entry<ActionName, Action> entry : actions.entrySet()) {
            actionMap.put(entry.getKey(), entry.getValue());
        }

        // add key binding
        InputMap inputMap = component.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(keyStrokes.get(NEW_SESSION), NEW_SESSION);
        inputMap.put(keyStrokes.get(CLOSE_SESSION), CLOSE_SESSION);
    }

    @Override
    public void windowActivated(WindowEvent e) {
        Object source = e.getSource();
        activatedFrame = (RcFrame) source;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        closeRcFrame();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (numFrame == 0) {
            System.exit(0);
        }
    }
}
