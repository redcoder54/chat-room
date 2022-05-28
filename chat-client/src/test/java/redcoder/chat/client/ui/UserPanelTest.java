package redcoder.chat.client.ui;

import redcoder.chat.client.model.headimage.HeadImageIconResource;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class UserPanelTest {

    private static final String[] IMAGES = {"Male.png", "Female.png"};
    private static final Random RANDOM = new Random();

    private static UserPanel createUserPanel() {
        UserPanel userPanel = new UserPanel(new User("Me", HeadImageIconResource.getHeadImage("Male.png")));
        for (int i = 0; i < 10; i++) {
            userPanel.addUser(createUser());
        }
        return userPanel;
    }

    private static User createUser() {
        String image = IMAGES[RANDOM.nextInt(2)];
        return new User("大" + RANDOM.nextInt(100), HeadImageIconResource.getHeadImage(image));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setPreferredSize(new Dimension(900, 600));

        frame.add(createUserPanel());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
