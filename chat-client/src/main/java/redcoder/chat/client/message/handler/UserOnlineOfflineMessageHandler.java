package redcoder.chat.client.message.handler;

import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.common.model.RcMessage;

public class UserOnlineOfflineMessageHandler extends MessageHandlerSupport implements MessageHandler {

    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        byte type = rcMessage.getType();
        if (type == RcMessage.ONLINE_MESSAGE) {
            User user = convertTo(rcMessage.getUser());
            chatFrame.getUserPanel().addUser(user);
            return false;
        } else if (type == RcMessage.OFFLINE_MESSAGE) {
            User user = convertTo(rcMessage.getUser());
            chatFrame.getUserPanel().removeUser(user);
            return false;
        }
        return true;
    }

}
