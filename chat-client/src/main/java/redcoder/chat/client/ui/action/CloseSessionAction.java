package redcoder.chat.client.ui.action;

import redcoder.chat.client.ui.Framework;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CloseSessionAction extends AbstractAction {

    public CloseSessionAction() {
        super("关闭会话");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Framework.closeRcFrame();
    }
}
