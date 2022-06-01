package redcoder.chat.client.message.handler;

import redcoder.chat.client.model.ImageMessage;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

public class ImageMessageHandler extends MessageHandlerSupport implements MessageHandler {

    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        if (rcMessage.getType() == RcMessage.IMAGE_MESSAGE) {
            User user = convertTo(rcMessage.getUser());
            chatFrame.getMessageDisplayPanel().addMessage(new ImageMessage(user, rcMessage.getImageData()), false);
            return false;
        }
        return true;
    }
}
