package redcoder.chat.client.ui.user;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

class InternalUserPanel extends JScrollPane {

    private final JPanel contentPane;

    InternalUserPanel() {
        super();
        contentPane = new JPanel(new MigLayout("flowy, fillx"));
        setViewportView(contentPane);
    }

    public void addUser(User user) {
        JLabel avatarLabel = new JLabel(user.getAvatarIcon());

        JLabel nicknameLabel = new JLabel(user.getNickname());
        nicknameLabel.setFont(new Font(null, Font.PLAIN, 12));

        contentPane.add(new UidPanel(user.getUid(), avatarLabel, nicknameLabel), "growx");
        contentPane.validate();
    }

    public boolean removeUser(User user) {
        for (Component component : contentPane.getComponents()) {
            if (component instanceof UidPanel) {
                UidPanel panel = (UidPanel) component;
                if (Objects.equals(panel.uid, user.getUid())) {
                    contentPane.remove(component);
                    contentPane.validate();
                    return true;
                }
            }
        }
        return false;
    }

    private static class UidPanel extends JPanel {

        private final String uid;

        public UidPanel(String uid, JLabel avatarLabel, JLabel nicknameLabel) {
            super(new MigLayout("fillx"));
            this.uid = uid;

            add(avatarLabel, "split 2");
            add(nicknameLabel, "wrap");
            add(new JSeparator(), "growx");
        }
    }
}
