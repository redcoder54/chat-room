package redcoder.chat.common.model;

import java.io.Serializable;

public class RcMessage implements Serializable {

    public static final byte ONLINE_MESSAGE = (byte) 1;
    public static final byte OFFLINE_MESSAGE = (byte) 2;
    public static final byte CHAT_MESSAGE = (byte) 3;

    /**
     * 消息类型，1-上线通知、2-下线通知、3-聊天消息
     */
    private byte type;
    private String uid;
    private String nickname;
    private String headImageName;
    private String msg;

    public RcMessage(String uid, String nickname, String headImageName, String msg) {
        this(CHAT_MESSAGE, uid, nickname, headImageName, msg);
    }

    public RcMessage(byte type, String uid, String nickname, String headImageName, String msg) {
        this.uid = uid;
        this.type = type;
        this.nickname = nickname;
        this.headImageName = headImageName;
        this.msg = msg;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RcMessage{" +
                "type=" + type +
                ", uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImageName='" + headImageName + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
