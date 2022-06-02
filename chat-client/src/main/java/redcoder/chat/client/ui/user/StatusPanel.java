package redcoder.chat.client.ui.user;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

class StatusPanel extends JPanel {
    private int userNum = 0;
    private final JLabel onlineUserNumLabel = new JLabel("在线人数：0");

    public StatusPanel( String nickname) {
        super(new MigLayout("flowy"));
        add(new JLabel("欢迎您：" + nickname));
        add(onlineUserNumLabel);
    }

    public void updateOnlineUserNum(int addNum) {
        userNum += addNum;
        onlineUserNumLabel.setText("在线人数：" + userNum);
    }
}
