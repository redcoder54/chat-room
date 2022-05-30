package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UserPanel extends JPanel {

    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 600;

    private final ChatFrame frame;
    private final StatusPanel statusPanel;
    private final InternalUserPanel internalUserPanel;

    public UserPanel(ChatFrame frame) {
        super(new MigLayout("flowy, fillx"));
        this.frame = frame;
        statusPanel = new StatusPanel();
        internalUserPanel = new InternalUserPanel();
        init();
    }

    private void init() {
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        add(statusPanel);
        add(internalUserPanel, "growx");
        addUser(frame.getLoggedUser());
    }


    public void addUser(User user) {
        internalUserPanel.addUser(user);
        statusPanel.updateOnlineUserNum(1);
    }

    public void removeUser(User user) {
        if (internalUserPanel.removeUser(user)) {
            statusPanel.updateOnlineUserNum(-1);
        }
    }

    private class StatusPanel extends JPanel {
        private int userNum = 0;
        private final JLabel onlineUserNumLabel = new JLabel("在线人数：0");

        public StatusPanel() {
            super(new MigLayout("flowy"));
            add(new JLabel("欢迎您：" + frame.getLoggedUser().getNickname()));
            add(onlineUserNumLabel);
        }

        public void updateOnlineUserNum(int addNum) {
            userNum += addNum;
            onlineUserNumLabel.setText("在线人数：" + userNum);
        }
    }

    private static class InternalUserPanel extends JScrollPane {

        private final JPanel contentPane;

        InternalUserPanel() {
            super();
            contentPane = new JPanel(new MigLayout("flowy, fillx"));
            setViewportView(contentPane);
        }

        public void addUser(User user) {
            JLabel headImage = new JLabel(user.getHeadImageIcon());

            JLabel nickname = new JLabel(user.getNickname());
            nickname.setFont(new Font(null, Font.PLAIN, 12));

            contentPane.add(new UidPanel(user.getUid(), headImage, nickname), "growx");
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
