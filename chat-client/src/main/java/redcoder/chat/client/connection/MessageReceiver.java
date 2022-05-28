package redcoder.chat.client.connection;

import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.MessageDisplayPanel;
import redcoder.chat.common.model.ChatMessage;

public class MessageReceiver {

    private final MessageDisplayPanel messageDisplayPanel;

    public MessageReceiver(MessageDisplayPanel messageDisplayPanel) {
        this.messageDisplayPanel = messageDisplayPanel;
    }

    public void onReceive(ChatMessage chatMessage) {
        messageDisplayPanel.addMessage(convertTo(chatMessage), false);
    }

    private Message convertTo(ChatMessage chatMessage) {
        User user = new User(chatMessage.getNickname(), chatMessage.getHeadImageName());
        return new Message(user, chatMessage.getMsg());
    }
}
