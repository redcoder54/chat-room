package redcoder.chat.client.connection;

import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.common.model.ChatMessage;

public class MessageReceiver {

    private final ChatFrame chatFrame;

    public MessageReceiver(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    public void onReceive(ChatMessage chatMessage) {
        chatFrame.getDisplayPanel().addMessage(convertTo(chatMessage), false);
    }

    private Message convertTo(ChatMessage chatMessage) {
        User user = new User(chatMessage.getNickname(), chatMessage.getHeadImageName());
        return new Message(user, chatMessage.getMsg());
    }
}
