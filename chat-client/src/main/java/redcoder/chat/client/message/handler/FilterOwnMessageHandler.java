package redcoder.chat.client.message.handler;

import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.common.model.RcMessage;
import redcoder.chat.common.model.RcUser;

import java.util.Objects;

public class FilterOwnMessageHandler implements MessageHandler {
    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        return !isMe(chatFrame, rcMessage.getUser());
    }

    private boolean isMe(ChatFrame chatFrame, RcUser rcUser) {
        if (rcUser == null) {
            return false;
        }
        User user = chatFrame.getLoggedUser();
        return Objects.equals(rcUser.getUid(), user.getUid());
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
