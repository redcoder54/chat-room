package redcoder.chat.client.model;

import redcoder.chat.client.resource.IconResource;

public class User {

    private String uid;
    private String nickname;
    private String avatarName;
    private AvatarIcon avatarIcon;

    public User(String uid, String nickname, String avatarName) {
        this(uid, nickname, avatarName, null);
    }

    public User(String uid, String nickname, AvatarIcon avatarIcon) {
        this(uid, nickname, avatarIcon.getAvatarName(), avatarIcon);
    }

    public User(String uid, String nickname, String avatarName, AvatarIcon avatarIcon) {
        this.uid = uid;
        this.nickname = nickname;
        this.avatarName = avatarName;
        this.avatarIcon = avatarIcon == null ? IconResource.getAvatarIcon(avatarName) : avatarIcon;
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

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public AvatarIcon getAvatarIcon() {
        return avatarIcon;
    }

    public void setAvatarIcon(AvatarIcon avatarIcon) {
        this.avatarIcon = avatarIcon;
    }
}
