package redcoder.chat.client.connection;

import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.MessageDisplayPanel;
import redcoder.chat.common.model.ChatMessage;

public class MessageSender {

    public static void send(Message message, MessageDisplayPanel messageDisplayPanel) {
        MessageReceiver messageReceiver = new MessageReceiver(messageDisplayPanel);
        new ChatConnection(convertTo(message), messageReceiver);
    }

    private static ChatMessage convertTo(Message message) {
        User user = message.getUser();
        return new ChatMessage(user.getNickname(), user.getHeadImageName(), message.getMsg());
    }
}
