package redcoder.chat.client.ui;

import redcoder.chat.client.icon.IconResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HeadImgDialog extends JDialog {

    private Icon selected;

    private HeadImgDialog(JFrame frame) {
        super(frame, "选择头像", true);
        init();
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public static Icon showDialog(JFrame frame) {
        HeadImgDialog dialog = new HeadImgDialog(frame);
        return dialog.getSelected();
    }

    private Icon getSelected() {
        return selected;
    }

    private void init() {
        JPanel headImagePanel = createHeadImagePanel();
        JPanel buttonPanel = createButtonPanel();

        setLayout(new BorderLayout());
        add(headImagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeadImagePanel() {
        JPanel headImgPanel = new JPanel(new GridLayout(4, 5));
        headImgPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        HeadImgActionListener listener = new HeadImgActionListener();
        for (ImageIcon imageIcon : IconResource.getHeadImageIcons()) {
            JButton button = new JButton(imageIcon);
            button.addActionListener(listener);
            headImgPanel.add(button);
        }
        return headImgPanel;
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
            selected = button.getIcon();
        }
    }
}
