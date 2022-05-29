package redcoder.chat.client.connection;

import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.common.model.RcMessage;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReceiver {

    private static final Logger LOGGER = Logger.getLogger(MessageReceiver.class.getName());
    private final ChatFrame chatFrame;

    public MessageReceiver(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    public void onReceive(RcMessage rcMessage) {
        if (isMe(rcMessage)) {
            return;
        }

        User user = new User(rcMessage.getUid(), rcMessage.getNickname(), rcMessage.getHeadImageName());
        switch (rcMessage.getType()) {
            case 1:
                chatFrame.getUserPanel().addUser(user);
                break;
            case 2:
                chatFrame.getUserPanel().removeUser(user);
                break;
            case 3:
                chatFrame.getDisplayPanel().addMessage(new Message(user, rcMessage.getMsg()), false);
                break;
            default:
                LOGGER.log(Level.WARNING, "未知的消息，type = {0}", rcMessage.getType());
                break;
        }
    }

    private boolean isMe(RcMessage rcMessage) {
        User user = chatFrame.getLoggedUser();
        return Objects.equals(rcMessage.getUid(), user.getUid());
    }
}
