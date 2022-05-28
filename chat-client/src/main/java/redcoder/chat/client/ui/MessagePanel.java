package redcoder.chat.client.ui;

import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JSplitPane {

    public static final int DEFAULT_WIDTH = 750;
    public static final int DEFAULT_HEIGHT = 600;

    private final MessageDisplayPanel displayPanel;
    private final MessageInputPanel inputPanel;

    public MessagePanel(User user) {
        super(JSplitPane.VERTICAL_SPLIT);
        this.displayPanel = new MessageDisplayPanel();
        this.inputPanel = new MessageInputPanel(user, this.displayPanel);

        setMinimumSize(new Dimension(DEFAULT_WIDTH - 150, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setResizeWeight(0.8);
        setTopComponent(displayPanel);
        setBottomComponent(inputPanel);
    }

    public MessageDisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public MessageInputPanel getInputPanel() {
        return inputPanel;
    }
}
