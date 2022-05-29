package redcoder.chat.common.model;

import java.io.Serializable;
import java.util.Objects;

public class RcUser implements Serializable {

    private String uid;
    private String nickname;
    private String headImageName;

    public RcUser(String uid, String nickname, String headImageName) {
        this.uid = uid;
        this.nickname = nickname;
        this.headImageName = headImageName;
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
                ", headImageName='" + headImageName + '\'' +
                '}';
    }
}
