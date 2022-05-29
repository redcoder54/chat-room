package redcoder.chat.client.ui.action;

import redcoder.chat.client.ui.Framework;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewSessionAction extends AbstractAction {

    public NewSessionAction() {
        super("新建会话");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Framework.createLoginFrame();
    }
}
