package redcoder.chat.client.ui;

import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.client.model.headimage.HeadImageIconResource;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class MessagePanelTest {

    private static final String[] IMAGES = {"Male.png", "Female.png"};
    private static final Random RANDOM = new Random();
    private static final User me = new User("me", "me", "Male.png");

    private static MessageDisplayPanel createMessagePanel() {
        MessageDisplayPanel messageDisplayPanel = new MessageDisplayPanel();
        for (int i = 1; i <= 10; i++) {
            messageDisplayPanel.addMessage(createMessage(i), i % 5 == 0);
        }

        return messageDisplayPanel;
    }

    private static Message createMessage(int i) {
        User user = createUser();

        Message message = new Message();
        message.setUser(user);
        if (i % 2 == 0) {
            message.setMsg("hellooooooo, i'm " + user.getNickname());
        }else {
            message.setMsg("hello, i'm " + user.getNickname());
        }

        return message;
    }

    private static User createUser() {
        String image = IMAGES[RANDOM.nextInt(2)];
        return new User(UUID.randomUUID().toString(), "小" + RANDOM.nextInt(100), HeadImageIconResource.getHeadImage(image));
    }

    public static void main(String[] args) {
        // Me.setMe(me);

        JFrame frame = new JFrame("Message Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setPreferredSize(new Dimension(900, 600));

        frame.add(createMessagePanel());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
