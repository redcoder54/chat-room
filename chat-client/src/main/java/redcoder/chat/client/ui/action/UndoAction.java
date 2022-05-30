package redcoder.chat.client.ui.action;

import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.client.ui.Framework;
import redcoder.chat.client.ui.RcFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {

    public UndoAction() {
        super("Undo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RcFrame rcFrame = Framework.getActivatedRcFrame();
        if (rcFrame instanceof ChatFrame) {
            ((ChatFrame) rcFrame).getMessageInputPanel().undo(e);
        }
    }
}
