package redcoder.chat.client.ui.login;

import redcoder.chat.client.model.AvatarIcon;
import redcoder.chat.client.resource.IconResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AvatarSelectionDialog extends JDialog {

    private AvatarIcon selected;

    private AvatarSelectionDialog(JFrame frame) {
        super(frame, "选择头像", true);
        init();
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public static AvatarIcon showDialog(JFrame frame) {
        AvatarSelectionDialog dialog = new AvatarSelectionDialog(frame);
        return dialog.getSelected();
    }

    private AvatarIcon getSelected() {
        return selected;
    }

    private void init() {
        JPanel avatarPanel = createAvatarPanel();
        JPanel buttonPanel = createButtonPanel();

        setLayout(new BorderLayout());
        add(avatarPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createAvatarPanel() {
        JPanel avatarPanel = new JPanel(new GridLayout(4, 5));
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        HeadImgActionListener listener = new HeadImgActionListener();
        for (AvatarIcon imageIcon : IconResource.getHeadImageIcons()) {
            JButton button = new JButton(imageIcon);
            button.addActionListener(listener);
            avatarPanel.add(button);
        }
        return avatarPanel;
    }

    private JPanel createButtonPanel() {
        JButton confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> {
            dispose();
            setVisible(false);
        });
        JButton cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> {
            selected = null;
            dispose();
            setVisible(false);
        });

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 2));
        btnPanel.add(confirmBtn);
        btnPanel.add(cancelBtn);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(btnPanel, BorderLayout.LINE_END);
        return panel;
    }

    private class HeadImgActionListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            selected = (AvatarIcon) button.getIcon();
        }
    }
}
