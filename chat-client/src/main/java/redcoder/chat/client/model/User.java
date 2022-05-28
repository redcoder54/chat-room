package redcoder.chat.client.model;

import javax.swing.*;

public class User {

    private String nickname;
    private Icon headImage;

    public User(String nickname, Icon headImage) {
        this.nickname = nickname;
        this.headImage = headImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Icon getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Icon headImage) {
        this.headImage = headImage;
    }
}
