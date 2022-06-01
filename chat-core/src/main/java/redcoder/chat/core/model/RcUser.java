package redcoder.chat.core.model;

import java.io.Serializable;
import java.util.Objects;

public class RcUser implements Serializable {

    private String uid;
    private String nickname;
    private String avatarName;

    public RcUser(String uid, String nickname, String avatarName) {
        this.uid = uid;
        this.nickname = nickname;
        this.avatarName = avatarName;
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

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RcUser)) {
            return false;
        }
        RcUser u = (RcUser) obj;
        return Objects.equals(uid, u.getUid());
    }

    @Override
    public String toString() {
        return "RcUser{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarName='" + avatarName + '\'' +
                '}';
    }
}
