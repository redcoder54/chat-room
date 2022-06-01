package redcoder.chat.client.message.handler;

import redcoder.chat.client.model.TextMessage;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

public class TextMessageHandler extends MessageHandlerSupport implements MessageHandler {

    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        if (rcMessage.getType() == RcMessage.TEXT_MESSAGE) {
            User user = convertTo(rcMessage.getUser());
            chatFrame.getMessageDisplayPanel().addMessage(new TextMessage(user, rcMessage.getMsg()), false);
            return false;
        }
        return true;
    }
}
