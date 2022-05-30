package redcoder.chat.client.ui.action;

import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.client.ui.Framework;
import redcoder.chat.client.ui.RcFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {

    public RedoAction() {
        super("Redo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RcFrame rcFrame = Framework.getActivatedRcFrame();
        if (rcFrame instanceof ChatFrame) {
            ((ChatFrame) rcFrame).getMessageInputPanel().redo(e);
        }
    }
}
