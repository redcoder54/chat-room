package redcoder.chat.client.message;

import redcoder.chat.client.message.handler.MessageHandlers;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

public class MessageReceiver {

    private final ChatFrame chatFrame;

    public MessageReceiver(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    public void onReceive(RcMessage rcMessage) {
        MessageHandlers.handleMessage(chatFrame, rcMessage);
    }

}
