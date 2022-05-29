package redcoder.chat.client.message.handler;

import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandlers {

    private static final Logger LOGGER = Logger.getLogger(MessageHandlers.class.getName());
    private static final List<MessageHandler> HANDLERS = new ArrayList<>();

    static {
        addHandler(new FilterOwnMessageHandler());
        addHandler(new UserOnlineOfflineMessageHandler());
        addHandler(new ChatMessageHandler());
        addHandler(new UserListMessageHandler());
        addHandler(new UnknownTypedMessageHandler());
    }

    public static void addHandler(MessageHandler handler) {
        HANDLERS.add(handler);
        HANDLERS.sort(Comparator.comparing(Ordered::getOrder));
    }

    public static void handleMessage(ChatFrame chatFrame, RcMessage rcMessage) {
        try {
            for (MessageHandler handler : HANDLERS) {
                if (!handler.handle(chatFrame, rcMessage)) {
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "处理消息失败", e);
        }
    }
}
