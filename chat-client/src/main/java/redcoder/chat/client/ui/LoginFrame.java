package redcoder.chat.client.ui;

import redcoder.chat.client.model.headimage.HeadImageIcon;
import redcoder.chat.client.model.headimage.HeadImageIconResource;
import redcoder.chat.client.model.User;
import redcoder.chat.client.utils.RandomUtils;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        super("登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(230, 150));

        Container contentPane = getContentPane();
        GroupLayout layout = new GroupLayout(contentPane);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        init(layout);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(GroupLayout layout) {
        JLabel nicknameLabel = new JLabel("昵称：");
        JTextField nicknameTF = new JTextField(RandomUtils.randomName(), 10);
        JLabel sexLabel = new JLabel("头像：");
        JButton headImgButton = new JButton(HeadImageIconResource.getHeadImage("Female.png"));
        JButton enterBtn = new JButton("进入");

        headImgButton.addActionListener(e -> {
            Icon icon = HeadImageSelectionDialog.showDialog(this);
            if (icon != null) {
                headImgButton.setIcon(icon);
            }
        });
        enterBtn.addActionListener(e -> {
            dispose();
            setVisible(false);
            SwingUtilities.invokeLater(() -> {
                User user = new User(nicknameTF.getText(), (HeadImageIcon) headImgButton.getIcon());
                ChatFrame frame = new ChatFrame(user);
                frame.createAndShowGUI();
            });
        });

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nicknameLabel)
                        .addComponent(sexLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nicknameTF)
                        .addComponent(headImgButton)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(enterBtn))));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nicknameLabel)
                        .addComponent(nicknameTF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(sexLabel)
                        .addComponent(headImgButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(enterBtn)));

        layout.linkSize(SwingConstants.HORIZONTAL, nicknameTF, enterBtn);
    }
}
