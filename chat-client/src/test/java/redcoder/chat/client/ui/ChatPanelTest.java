package redcoder.chat.client.ui;

import redcoder.chat.client.icon.IconResource;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.util.Random;

public class ChatPanelTest {

    private static final String[] IMAGES = {"Male.png", "Female.png"};
    private static final Random RANDOM = new Random();
    private static final User me = new User("me", IconResource.getHeadImgIcon("Male.png"));

    private static ChatPanel createChatPanel() {
        ChatPanel chatPanel = new ChatPanel(me);
        initUserPanel(chatPanel.getUserPanel());
        initMessagePanel(chatPanel.getMessagePanel().getDisplayPanel());
        return chatPanel;
    }

    private static void initUserPanel(UserPanel userPanel) {
        for (int i = 0; i < 10; i++) {
            userPanel.addUser(createUser());
        }
    }

    private static void initMessagePanel(MessageDisplayPanel messageDisplayPanel) {
        for (int i = 1; i <= 10; i++) {
            messageDisplayPanel.addMessage(createMessage(), i % 5 == 0);
        }
    }

    private static Message createMessage() {
        User user = createUser();

        Message message = new Message();
        message.setUser(user);
        message.setMsg("hello, i'm " + user.getNickname());

        return message;
    }

    private static User createUser() {
        String image = IMAGES[RANDOM.nextInt(2)];
        return new User("大" + RANDOM.nextInt(100), IconResource.getHeadImgIcon(image));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(createChatPanel());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
