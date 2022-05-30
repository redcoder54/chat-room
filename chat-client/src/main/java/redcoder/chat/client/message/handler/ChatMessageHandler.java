package redcoder.chat.client.message.handler;

import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

public class ChatMessageHandler extends MessageHandlerSupport implements MessageHandler {

    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        if (rcMessage.getType() == RcMessage.CHAT_MESSAGE) {
            User user = convertTo(rcMessage.getUser());
            chatFrame.getMessageDisplayPanel().addMessage(new Message(user, rcMessage.getMsg()), false);
            return false;
        }
        return true;
    }
}
