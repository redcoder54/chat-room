package redcoder.chat.client.message.handler;

import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UnknownTypedMessageHandler extends MessageHandlerSupport implements MessageHandler {

    private static final Logger LOGGER = Logger.getLogger(UnknownTypedMessageHandler.class.getName());

    @Override
    public boolean handle(ChatFrame chatFrame, RcMessage rcMessage) {
        LOGGER.log(Level.SEVERE, "未知类型的消息，无法处理，消息类型：{0}", rcMessage.getType());
        return true;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
