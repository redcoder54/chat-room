package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;

class InternalUserPanel extends JScrollPane {

    private final JPanel contentPane;

    InternalUserPanel() {
        super();
        contentPane = new JPanel(new MigLayout("flowy, fillx"));
        setViewportView(contentPane);
    }

    void addUser(User user) {
        JLabel headImage = new JLabel(user.getHeadImageIcon());

        JLabel nickname = new JLabel(user.getNickname());
        nickname.setFont(new Font(null, Font.PLAIN, 12));

        JPanel panel = new JPanel(new MigLayout());
        panel.add(headImage);
        panel.add(nickname);

        contentPane.add(panel);
        contentPane.add(new JSeparator(), "growx");
    }
}
