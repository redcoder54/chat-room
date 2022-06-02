package redcoder.chat.client.message.handler;

import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.client.ui.user.UserPanel;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.model.RcUser;

public class UserListMessageHandler extends MessageHandlerSupport implements MessageHandler{

    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        if (rcMessage.getType() == RcMessage.ONLINE_USER_LIST_MESSAGE) {
            UserPanel userPanel = chatFrame.getUserPanel();
            for (RcUser onlineUser : rcMessage.getOnlineUsers()) {
                userPanel.addUser(convertTo(onlineUser));
            }
            return false;
        }
        return true;
    }
}
