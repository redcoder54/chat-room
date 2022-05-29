package redcoder.chat.client.model;

import redcoder.chat.client.model.headimage.HeadImageIcon;
import redcoder.chat.client.model.headimage.HeadImageIconResource;

public class User {

    private String uid;
    private String nickname;
    private String headImageName;
    private HeadImageIcon headImageIcon;

    public User(String uid, String nickname, String headImageName) {
        this(uid, nickname, headImageName, null);
    }

    public User(String uid, String nickname, HeadImageIcon headImageIcon) {
        this(uid, nickname, headImageIcon.getHeadImageName(), headImageIcon);
    }

    public User(String uid, String nickname, String headImageName, HeadImageIcon headImageIcon) {
        this.uid = uid;
        this.nickname = nickname;
        this.headImageName = headImageName;
        this.headImageIcon = headImageIcon == null ? HeadImageIconResource.getHeadImage(headImageName) : headImageIcon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
