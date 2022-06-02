package redcoder.chat.client.ui;

import redcoder.chat.client.model.User;
import redcoder.chat.client.resource.IconResource;
import redcoder.chat.client.ui.user.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class UserPanelTest {

    private static final String[] IMAGES = {"Male.png", "Female.png"};
    private static final Random RANDOM = new Random();
    private static final User me = new User("me", "me", "Male.png");

    private static UserPanel createUserPanel() {
        UserPanel userPanel = new UserPanel(new ChatFrame(me));
        for (int i = 0; i < 10; i++) {
            userPanel.addUser(createUser(i));
        }
        return userPanel;
    }

    private static User createUser(int i) {
        String image = IMAGES[RANDOM.nextInt(2)];
        return new User("u" + i, "u" + i, IconResource.getAvatarIcon(image));
    }

    public static void main(String[] args) {
        // Me.setMe(me);

        JFrame frame = new JFrame("User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setPreferredSize(new Dimension(900, 600));

        UserPanel userPanel = createUserPanel();

        JButton button = new JButton("del-u0");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPanel.removeUser(new User("u0", "", ""));
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(userPanel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
