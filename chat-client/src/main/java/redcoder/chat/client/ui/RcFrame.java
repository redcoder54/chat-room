package redcoder.chat.client.ui;

import javax.swing.*;

public abstract class RcFrame extends JFrame {

    public RcFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(Framework.INSTANCE);
    }

    public abstract void createAndShowGUI();
}
