package redcoder.chat.client.model;

import redcoder.chat.client.model.headimage.HeadImageIcon;
import redcoder.chat.client.model.headimage.HeadImageIconResource;

public class User {

    private String nickname;
    private String headImageName;
    private HeadImageIcon headImageIcon;

    public User(String nickname, String headImageName) {
        this(nickname, headImageName, null);
    }

    public User(String nickname, HeadImageIcon headImageIcon) {
        this(nickname, headImageIcon.getHeadImageName(), headImageIcon);
    }

    public User(String nickname, String headImageName, HeadImageIcon headImageIcon) {
        this.nickname = nickname;
        this.headImageName = headImageName;
        this.headImageIcon = headImageIcon == null ? HeadImageIconResource.getHeadImage(headImageName) : headImageIcon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImageName() {
        return headImageName;
    }

    public void setHeadImageName(String headImageName) {
        this.headImageName = headImageName;
    }

    public HeadImageIcon getHeadImageIcon() {
        return headImageIcon;
    }

    public void setHeadImageIcon(HeadImageIcon headImageIcon) {
        this.headImageIcon = headImageIcon;
    }
}
