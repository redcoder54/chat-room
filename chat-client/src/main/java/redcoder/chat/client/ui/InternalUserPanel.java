package redcoder.chat.client.ui;

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

    void addUser(User user) {
        JLabel headImage = new JLabel(user.getHeadImageIcon());

        JLabel nickname = new JLabel(user.getNickname());
        nickname.setFont(new Font(null, Font.PLAIN, 12));

        contentPane.add(new UidPanel(user.getUid(), headImage, nickname),"growx");
        contentPane.validate();
    }

    boolean removeUser(User user) {
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

        public UidPanel(String uid, JLabel headImage, JLabel nickname) {
            super(new MigLayout("fillx"));
            this.uid = uid;

            add(headImage, "split 2");
            add(nickname, "wrap");
            add(new JSeparator(), "growx");
        }
    }
}
